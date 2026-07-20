package io;

import java.util.Scanner;

public class ConsoleInput implements AutoCloseable {
    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String askFile() {
        System.out.print("File path: src/decks/");
        return "src/decks/" + scanner.next();
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
        int s;

        do {
            System.out.print("Enter shuffle count (>0): ");
            s = scanner.nextInt();
        } while (s <= 0);

        return s;
    }

    public int askDrawCount() {
        int n;

        do {
            System.out.print("Enter draw count (1 or 3): ");
            n = scanner.nextInt();
        } while (n != 1 || n != 3);

        return n;
    }

    public int askMenuChoice() {
        System.out.print("Choose a game: ");
        return scanner.nextInt();
    }

    public void close() {
        scanner.close();
    }
}
