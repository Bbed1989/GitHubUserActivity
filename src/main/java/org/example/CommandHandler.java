package org.example;

import java.util.Scanner;

public class CommandHandler {
    private final Scanner SCANNER = new Scanner(System.in);

    public void handleCommand(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter username");
            String input = SCANNER.nextLine().trim();
            args = input.split("\\s+", 2);
        }

        String command = args[0]; // first word is a command
    }

}
