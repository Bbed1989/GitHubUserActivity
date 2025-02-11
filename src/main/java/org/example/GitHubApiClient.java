package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class GitHubApiClient {
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
    private static final Logger LOGGER = Logger.getLogger(GitHubApiClient.class.getName());
    private final HttpClient httpClient;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";


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

    private String sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        switch (response.statusCode()) {
            case 200:
                System.out.println(GREEN + "✔ Request successful!" + RESET);
                return response.body();
            case 404:
                throw logAndThrow(RED + "❌ User not found on GitHub" + RESET, new IllegalStateException("User not found"));
            case 403, 401:
                throw logAndThrow(YELLOW + "⚠ Access denied. Check credentials or API rate limit." + RESET, new IllegalStateException("Rate limit exceeded"));
            case 500:
                throw logAndThrow(RED + "❌ GitHub internal error. Try again later." + RESET, new IOException("Server error"));
            default:
                throw logAndThrow(RED + "❌ Unexpected response: " + response.statusCode() + RESET, new IOException("HTTP error " + response.statusCode()));
        }
    }

    private <T extends Exception> T logAndThrow(String message, T exception) throws T {
        LOGGER.warning(message);
        throw exception;
    }
}
