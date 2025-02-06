package org.example;
import java.util.ArrayList;
import java.util.List;

public class GitHubDataParser {

    public static List<String> parseEvents(String jsonResponse) {
        List<String> events = new ArrayList<>();

        // deleting [] from events
        jsonResponse = jsonResponse.trim();
        if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
            jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
        }

        // splitting the json response into individual events and extracting type, repo name, and commit message if applicable.
        String[] rawEvents = jsonResponse.split("\\},\\{");

        for (String rawEvent : rawEvents) {
            String type = extractValue(rawEvent, "\"type\":\"", "\"");
            String repo = extractValue(rawEvent, "\"repo\":{\"id\":", ",\"name\":\"");
            String repoName = extractValue(rawEvent, "\"name\":\"", "\"");

            if (type != null && repoName != null) {
                String formattedEvent = formatEvent(type, repoName, rawEvent);
                events.add(formattedEvent);
            }
        }
        return events;
    }

    private static String extractValue(String text, String key, String endChar) {
        int start = text.indexOf(key);
        if (start == -1) return null;
        start += key.length();
        int end = text.indexOf(endChar, start);
        return end != -1 ? text.substring(start, end) : null;
    }

    private static String formatEvent(String type, String repo, String rawEvent) {
        return switch (type) {
            case "PushEvent" -> {
                String commitMessage = extractValue(rawEvent, "\"message\":\"", "\"");
                yield "Pushed to " + repo + ": " + (commitMessage != null ? commitMessage : "No message");
            }
            case "WatchEvent" -> "Starred " + repo;
            case "CreateEvent" -> "Created repository " + repo;
            case "ForkEvent" -> "Forked " + repo;
            case "IssuesEvent" -> "Opened an issue in " + repo;
            default -> "Performed " + type + " in " + repo;
        };
    }
}
