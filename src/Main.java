import core.Game;
import solitaire.SolitaireGame;
import wcg.WarGame;
import io.ConsoleInput;

public class Main {
    public static void main(String[] args) throws Exception {
        try (ConsoleInput input = new ConsoleInput()) {
            System.out.println("╔══════════════════════════════╗");
            System.out.println("║     HANNAH'S CARD GAMES      ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  [1] War                     ║");
            System.out.println("║  [2] Solitaire               ║");
            System.out.println("╚══════════════════════════════╝");

            int choice = input.askMenuChoice();

            Game game = switch (choice) {
                case 1 -> new WarGame(input);
                case 2 -> new SolitaireGame(input);
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