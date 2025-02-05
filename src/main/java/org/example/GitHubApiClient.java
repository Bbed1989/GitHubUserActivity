package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubApiClient {
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
    private final HttpClient httpClient;


    public GitHubApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String fetchUserActivity(String username) throws IOException, InterruptedException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("GitHub username cannot be empty");
        }
        System.out.println("Fetching GitHub user activity for " + username);
        HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(GITHUB_API_URL + username + "/events"))
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
               .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Error: Received HTTP " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: Unable to fetch data from GitHub API");
            return null;
        }
    }
}
