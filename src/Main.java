import core.Game;
import solitaire.SolitaireGame;
import wcg.WarGame;
import io.ConsoleInput;

public class Main {
    public static void main(String[] args) throws Exception {
        int choice;

        try (ConsoleInput input = new ConsoleInput()) {
            do {
                System.out.println("╔══════════════════════════════╗");
                System.out.println("║     HANNAH'S CARD GAMES      ║");
                System.out.println("╠══════════════════════════════╣");
                System.out.println("║  [1] War                     ║");
                System.out.println("║  [2] Solitaire               ║");
                System.out.println("║  [0] Exit                    ║");
                System.out.println("╚══════════════════════════════╝");

                do {
                    choice = input.askMenuChoice();
                    if (choice != 1 && choice != 2 && choice != 0 ) {
                        System.out.println("\tInvalid choice. Please enter 1 or 2 to select a game, or 0 to exit.");
                    }
                } while (choice != 1 && choice != 2 && choice != 0 );

                if (choice == 0) {
                    System.out.println("\nExiting... Thanks for playing! <3");
                    System.exit(0);
                }

                Game game = switch (choice) {
                    case 1 -> new WarGame(input);
                    case 2 -> new SolitaireGame(input);
                    default -> throw new IllegalArgumentException("Invalid selection.");
                };

                game.initialize();
                game.play();
                game.displayResult();
            } while (input.askPlayAgain());
        }
    }
}
