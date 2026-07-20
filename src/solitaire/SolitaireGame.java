package solitaire;

import core.*;
import io.ConsoleInput;
import io.DeckFileReader;

import java.util.List;
import java.util.StringTokenizer;

import static solitaire.SolitaireMoveType.TABLEAU_TO_TABLEAU;
import static solitaire.SolitaireTable.TABLEAU_COUNT;

public class SolitaireGame implements Game {
    private static final int DRAW_COUNT = 3;
    private static final int MAX_NON_PROGRESS_TABLEAU_MOVES = 20;
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

    private boolean lost;

    public SolitaireGame(ConsoleInput input) {
        this.input = input;
        this.lastMove = null;
        this.nonProgressTableauMoves = 0;
        this.progressMadeThisCycle = false;
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
                lost = false;
                break;
            }

            boolean moveMadeThisIteration = false;

            // Permanent progress
            if (tryTableauToFoundation()) {
                progressMadeThisCycle = true;
                nonProgressTableauMoves = 0;
                displayTable();
                continue;
            }

            // Tableau rearrangement
            if (nonProgressTableauMoves < MAX_NON_PROGRESS_TABLEAU_MOVES && tryTableauToTableau()) {
                nonProgressTableauMoves++;
                displayTable();
                continue;
            }

            // Permanent progress
            if (tryWasteToFoundation()) {
                progressMadeThisCycle = true;
                nonProgressTableauMoves = 0;
                displayTable();
                continue;
            }

            if (tryWasteToTableau()) {
                nonProgressTableauMoves = 0;
                displayTable();
                continue;
            }

            if (!table.getTalon().isEmpty()) {
                drawFromTalon();
                displayTable();
                continue;
            }

            if (progressMadeThisCycle && !table.getWaste().isEmpty()) {
                recycleWasteIntoTalon();
                progressMadeThisCycle = false;
                nonProgressTableauMoves = 0;
                displayTable();
                continue;
            }

            lost = true;

            // 1. Search tableau-to-foundation moves.
//            if (nonProgressTableauMoves < MAX_NON_PROGRESS_TABLEAU_MOVES) {
//                SolitaireMoveResult result = tryTableauToFoundation();

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

//            lost = true;
        }
    }

    @Override
    public boolean isGameOver() {
        return table.areFoundationsComplete() || lost;
    }

    @Override
    public void displayResult() {
        displayTable();

        if (!lost) {
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
//        String path = "src\\decks\\input.txt";
        String path = input.askFile();
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

        // flip all bottom cards to face up
        for (TableauPile stack : table.getTableau()) {
            stack.revealBottomCard();
        }

        // add excess to talon
        while (!inputDeck.isEmpty()) {
            table.getTalon().addCard(inputDeck.drawSolitaireTopCard());
        }
    }

    private boolean tryTableauToFoundation() {
        for (TableauPile stack : table.getTableau()) {
            SolitaireCard candidate = stack.getBottomCard();
            if (candidate == null || !candidate.isFaceUp()) {
                continue;
            }
            for (FoundationPile zone : table.getFoundations()) {
                if (zone.canAdd(candidate)) {
                    SolitaireCard movedCard = stack.removeBottomCard();
                    zone.addCard(movedCard);
                    stack.revealBottomCard();
                    lastMove = null;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean tryTableauToTableau() {
        List<TableauPile> tableau = table.getTableau();

        // try bottom cards first
        for (int sourceIndex = 0; sourceIndex < TABLEAU_COUNT; sourceIndex++) {
            TableauPile source = tableau.get(sourceIndex);
            SolitaireCard candidate = source.getBottomCard();
            if (candidate == null || !candidate.isFaceUp()) {
                continue;
            }

            int sourceCardIndex = source.size() - 1;
            int sourceSize = source.size();

            for (int destinationIndex = 0; destinationIndex < TABLEAU_COUNT; destinationIndex++) {
                // Do not move onto the same pile
                if (sourceIndex == destinationIndex) {
                    continue;
                }

                TableauPile destination = tableau.get(destinationIndex);
                SolitaireMove proposedMove = new SolitaireMove(SolitaireMoveType.TABLEAU_TO_TABLEAU, sourceIndex, destinationIndex, sourceCardIndex);

                // Prevent immediately reversing the previous move
                if (proposedMove.reverses(lastMove)) {
                    continue;
                }

                if (destination.canAdd(candidate, sourceSize)) {
                    SolitaireCard movedCard = source.removeBottomCard();
                    destination.addCard(movedCard);
                    source.revealBottomCard();
                    // Save this successful move
                    lastMove = proposedMove;
                    return true;
                }
            }
        }

        // comparing top open card stack
        for (int sourceIndex = 0; sourceIndex < TABLEAU_COUNT; sourceIndex++) {
            TableauPile source = tableau.get(sourceIndex);
            SolitaireCard candidate = source.getFirstFaceUpCard();
            int candidateIndex = source.getFirstFaceUpIndex();
            if (candidate == null || !candidate.isFaceUp()) {
                continue;
            }

            int sourceCardIndex = source.size() - 1;
            int sourceSize = source.size();

            for (int destinationIndex = 0; destinationIndex < TABLEAU_COUNT; destinationIndex++) {
                // Do not move onto the same pile
                if (sourceIndex == destinationIndex) {
                    continue;
                }

                TableauPile destination = tableau.get(destinationIndex);
                SolitaireMove proposedMove = new SolitaireMove(SolitaireMoveType.TABLEAU_TO_TABLEAU, sourceIndex, destinationIndex, sourceCardIndex);

                // Prevent immediately reversing the previous move
                if (proposedMove.reverses(lastMove)) {
                    continue;
                }

                if (destination.canAdd(candidate, sourceSize)) {
                    List<SolitaireCard> movedCards = source.removeCardsFrom(candidateIndex);
                    destination.addCards(movedCards);
                    source.revealBottomCard();
                    // Save this successful move
                    lastMove = proposedMove;
                    return true;
                }
            }
        }

        return false;
    }
//    private boolean tryTableauToTableau() {
//        List<TableauPile> stack = table.getTableau();
//        SolitaireMove(TABLEAU_TO_TABLEAU,
//        int sourcePile,
//        int destinationPile,
//        int sourceCardIndex
//        for (int sourceIndex = 0; sourceIndex < TABLEAU_COUNT; sourceIndex++) {
//            TableauPile source = stack.get(sourceIndex);
//            SolitaireCard candidate = source.getBottomCard();
//            if (candidate == null || !candidate.isFaceUp()) {
//                continue;
//            }
//            for (int destinationIndex = 0; destinationIndex < TABLEAU_COUNT; destinationIndex++) {
//                if (sourceIndex == destinationIndex) { // skip same stack
//                    continue;
//                }
//
//                TableauPile destination = stack.get(destinationIndex);
//                int sourceSize = source.size(); // for stopping king moving to another empty
//                if (destination.canAdd(candidate, sourceSize)) { // check if can add card
//                    SolitaireCard movedCard = source.removeBottomCard();
//                    destination.addCard(movedCard);
//                    source.revealBottomCard(); // flip new bottom
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    private boolean tryWasteToFoundation() {
        WastePile talon_open = table.getWaste();
        SolitaireCard candidate = talon_open.peekTopCard();
        if (candidate == null || !candidate.isFaceUp()) {
            return false;
        }

        for (FoundationPile zone : table.getFoundations()) {
            if (zone.canAdd(candidate)) {
                SolitaireCard movedCard = talon_open.removeTopCard();
                zone.addCard(movedCard);
                lastMove = null;
                return true;
            }
        }

        return false;
    }

    private boolean tryWasteToTableau() {
        WastePile talon_open = table.getWaste();
        SolitaireCard candidate = talon_open.peekTopCard();
        if (candidate == null || !candidate.isFaceUp()) {
            return false;
        }

        List<TableauPile> stack = table.getTableau();
        for (int destinationIndex = 0; destinationIndex < TABLEAU_COUNT; destinationIndex++) {
            TableauPile destination = stack.get(destinationIndex);
            if (destination.canAdd(candidate, 0)) { // check if can add card
                SolitaireCard movedCard = talon_open.removeTopCard();
                destination.addCard(movedCard);
                lastMove = null;
                return true;
            }
        }

        return false;
    }

    private void drawFromTalon() {
        table.drawFromTalon(DRAW_COUNT);
    }

    private void recycleWasteIntoTalon() {
        table.recycleWaste();
    }

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