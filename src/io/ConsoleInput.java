package io;

import java.util.Scanner;

public class ConsoleInput implements AutoCloseable {
    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String askFile() {
        System.out.print("File path: ");
        return scanner.next();
    }

    public int askPlayerCount() {
        int n;

        do {
            System.out.print("Enter number of players (2-8): ");
            n = scanner.nextInt();
        } while (n < 2 || n > 8);

        return n;
    }

    public int askShuffleCount() {
        System.out.print("Enter shuffle count: ");
        return scanner.nextInt();
    }

    public int askMenuChoice() {
        System.out.print("Choose a game: ");
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}
