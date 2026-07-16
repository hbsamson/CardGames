import core.Game;
//import solitaire.SolitaireGame;
import wcg.WarGame;
import io.ConsoleInput;
import io.ConsolePrinter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        try (ConsoleInput input = new ConsoleInput()) {
            ConsolePrinter printer = new ConsolePrinter();

            System.out.println("=== Hannah's Card Games ===");
            System.out.println("[1] War");
            System.out.println("[2] Solitaire");
            System.out.print("Choose a game: ");

            int choice = input.askMenuChoice();

            Game game = switch (choice) {
                case 1 -> new WarGame(input, printer);
//                case 2 -> System.out.println("Solitaire");
//                case 2 -> new SolitaireGame(scanner);
                default -> throw new IllegalArgumentException(
                        "Invalid game selection."
                );
            };

            game.initialize();
            game.play();
            game.displayResult();

        }
    }
}