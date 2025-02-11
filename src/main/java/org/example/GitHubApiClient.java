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
        return sendRequest(request);
    }

    public String sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return switch (response.statusCode()) {
            case 200 -> response.body();
            case 404 -> throw new IllegalStateException("User not found on GitHub");
            case 403 -> throw new IllegalStateException("API rate limit exceeded");
            case 500 -> throw new IOException("Internal server error on GitHub");
            case 422 -> throw new IllegalStateException("Invalid request");
            case 401 -> throw new IllegalStateException("Unauthorized access to GitHub API");
            default -> throw new IOException("HTTP error response: " + response.statusCode());
        };
    }
}
