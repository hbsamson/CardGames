package io;

import java.util.Scanner;
import java.util.InputMismatchException;

public class ConsoleInput implements AutoCloseable {
    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String askFile() {
        System.out.print("\nFile path: src/decks/");
        return "src/decks/" + scanner.next();
    }

    public int askPlayerCount() {
        int n = 0;

        do {
            try {
                System.out.print("Enter number of players (2-8): ");
                n = scanner.nextInt();
                if (n < 2 || n > 8) {
                    System.out.println("\tInvalid input. Please enter number within 2 to 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\tInvalid input. Please enter a number.");
                scanner.nextLine(); // discard invalid input
            }
        } while (n < 2 || n > 8);
        
        return n;
    }

    public int askShuffleCount() {
        int s = -1;
        do {
            try {
                System.out.print("Enter shuffle count (more than 0): ");
                s = scanner.nextInt();
                if (s <= 0) {
                    System.out.println("\tInvalid input. Please enter a number more than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\tInvalid input. Please enter a number.");
                scanner.nextLine(); // discard invalid input
            }
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
        while (true) {
            try {
                System.out.print("Choose a game: ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\tInvalid input. Please enter a number.");
                scanner.nextLine(); // discard invalid input
            }
        }
    }

    public boolean askPlayAgain() {
        while (true) {
            System.out.print("\t\nWould you like to play again? (y/n): ");
            String response = scanner.next().trim();

            if (response.equalsIgnoreCase("y")
                    || response.equalsIgnoreCase("yes")) {
                return true;
            }

            if (response.equalsIgnoreCase("n")
                    || response.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println("\tInvalid input. Please enter y or n.");
        }
    }

    public void close() {
        scanner.close();
    }
}
