package org.example;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

public class CommandHandler {
    private final Scanner SCANNER = new Scanner(System.in);

    public void handleCommand(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Please enter username");
            String input = SCANNER.nextLine().trim();
            args = input.split("\\s+", 2);
        }

        String username = args[0]; // first word is a command
        String response = getResponse(username);
        parseEvents(response);
    }

    private String getResponse(String username) throws IOException, InterruptedException {
        GitHubApiClient apiClient = new GitHubApiClient(HttpClient.newHttpClient());
        return apiClient.fetchUserActivity(username);
    }

    private void parseEvents(String response) {
        List<String> events = GitHubDataParser.parseEvents(response);
        for (String event : events) {
            System.out.println(event);
        }
    }

}

