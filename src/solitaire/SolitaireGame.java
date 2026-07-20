package solitaire;

import core.Card;
import core.Deck;
import core.Game;
import io.ConsoleInput;
import io.DeckFileReader;

import java.util.StringTokenizer;

import static solitaire.SolitaireTable.TABLEAU_COUNT;

public class SolitaireGame implements Game {
    private static final int DRAW_COUNT = 3;
    private static final int MAX_NON_PROGRESS_TABLEAU_MOVES = 25;
    private final ConsoleInput input;

    private SolitaireTable table;
    private Deck deck;

    private SolitaireMove lastMove;
    private int nonProgressTableauMoves;

    /*
     * Persists for the entire pass through the talon.
     * Do not reset at the start of every iteration.
     */
    private boolean progressMadeThisCycle;

    private boolean won;
    private boolean lost;

    public SolitaireGame(ConsoleInput input) {
        this.input = input;
        this.lastMove = null;
        this.nonProgressTableauMoves = 0;
        this.progressMadeThisCycle = false;
        this.won = false;
        this.lost = false;
    }

    @Override
    public void initialize() throws Exception {
        // Load and shuffle using your existing implementation.
        System.out.println("\n=== Hello, welcome to Hannah's Solitaire Game ===");

        table = new SolitaireTable();

        dealInitialTableau();
        displayTable();
    }

    @Override
    public void play() {
        while (!isGameOver()) {
            if (table.areFoundationsComplete()) {
                won = true;
                break;
            }

            boolean moveMadeThisIteration = false;

            /*
             * 1. Search tableau-to-foundation moves.
             */
//            if (nonProgressTableauMoves
//                    < MAX_NON_PROGRESS_TABLEAU_MOVES) {
//
//                SolitaireMoveResult result =
//                        tryTableauToFoundation();
//
//                if (result.moved()) {
//                    processMoveResult(result);
//                    moveMadeThisIteration = true;
//                }
//
//                if (moveMadeThisIteration) {
//                    continue;
//                }
//
//                /*
//                 * 2. Search tableau-to-tableau moves.
//                 */
//                result = tryTableauToTableau();
//
//                if (result.moved()) {
//                    processMoveResult(result);
//                    continue;
//                }
//            }
//
//            /*
//             * 3. Search waste moves.
//             */
//            SolitaireMoveResult wasteResult =
//                    tryWasteToFoundation();
//
//            if (!wasteResult.moved()) {
//                wasteResult = tryWasteToTableau();
//            }
//
//            if (wasteResult.moved()) {
//                processMoveResult(wasteResult);
//                continue;
//            }
//
//            /*
//             * 4. Draw up to three cards.
//             */
//            if (!table.getTalon().isEmpty()) {
//                drawFromTalon();
//                displayBoard();
//                continue;
//            }
//
//            /*
//             * 5. Talon is empty and no move is available.
//             */
//            if (progressMadeThisCycle) {
//                recycleWasteIntoTalon();
//
//                progressMadeThisCycle = false;
//                nonProgressTableauMoves = 0;
//
//                displayBoard();
//                continue;
//            }

            lost = true;
        }

        displayResult();
    }

    @Override
    public boolean isGameOver() {
        return table.areFoundationsComplete() || lost;
    }

    @Override
    public void displayResult() {
        displayTable();

        if (won) {
            System.out.println("\nGame Complete!");
        } else {
            System.out.println("\nGame Lost!");
        }
    }

//    private void processMoveResult(SolitaireMoveResult result) {
//        if (result.progressMade()) {
//            progressMadeThisCycle = true;
//            nonProgressTableauMoves = 0;
//        }
//
//        if (result.move().type()
//                == SolitaireMoveType.TABLEAU_TO_TABLEAU) {
//
//            if (!result.progressMade()) {
//                nonProgressTableauMoves++;
//            }
//        } else {
//            nonProgressTableauMoves = 0;
//        }
//
//        lastMove = result.move();
//        displayBoard();
//    }

    private void dealInitialTableau() throws Exception {
        // Implement according to your existing Deck methods
        // ask for path to input deck
        String path = "src\\decks\\input.txt";
//        String path = input.askFile();
        String data = DeckFileReader.readFileAsString(path);
        String input_data = data.replace("Initial card sequence: ", "");
        StringTokenizer tokenizer = new StringTokenizer(input_data, ",");

        // placing cards (as tokens) into deck
        Deck inputDeck = new Deck();
        while (tokenizer.hasMoreTokens()) {
            String cardString = tokenizer.nextToken().trim();
            inputDeck.addToBottom(Card.fromString(cardString));
        }

        // print path txt file deck
        System.out.println("Deck from " + path);
        System.out.print(inputDeck.asList());

        // add 1 per column, next row starts with next col
        int index = 0;
        for (int row = 0; row < TABLEAU_COUNT; row++) {
            for (int col = row; col < TABLEAU_COUNT; col++) {
                table.getTableau().get(col).addCard(inputDeck.drawSolitaireTopCard());
                index++;
            }
        }

        // flip all bottom cards to faceup
        for (TableauPile pile : table.getTableau()) {
            pile.revealBottomCard();
        }

        // add excess to talon
        while (!inputDeck.isEmpty()) {
            table.getTalon().addCard(inputDeck.drawSolitaireTopCard());
        }
    }

    private void moveRemainingCardsToTalon() {
        // Implement according to your existing Deck methods.

    }

//    private SolitaireMoveResult tryTableauToFoundation() {
//        return SolitaireMoveResult.noMove();
//    }
//
//    private SolitaireMoveResult tryTableauToTableau() {
//        return SolitaireMoveResult.noMove();
//    }
//
//    private SolitaireMoveResult tryWasteToFoundation() {
//        return SolitaireMoveResult.noMove();
//    }
//
//    private SolitaireMoveResult tryWasteToTableau() {
//        return SolitaireMoveResult.noMove();
//    }
//
//    private void drawFromTalon() {
//        table.drawFromTalon(DRAW_COUNT);
//    }
//
//    private void recycleWasteIntoTalon() {
//        table.recycleWaste();
//    }

    private void displayTable() {
        System.out.println("\n==================== Solitaire ====================");
        System.out.println("Foundation Zone");
        table.printFoundations();
        System.out.println("\nManoeuvre Tableau");
        table.printTableau();
        System.out.println("Talon");
        table.printTalon();
        System.out.println("Talon Waste");
        table.printWaste();
        System.out.println("==================================================");
    }
}