package solitaire;

import core.*;
import io.ConsoleInput;
import io.DeckFileReader;

import java.io.File;
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
    private int moveNumber;
    private String lastMoveDescription;

    // persists for the entire pass through the talon, does not reset at the start of every iteration
    private boolean progressMadeThisCycle;

    private boolean lost;

    public SolitaireGame(ConsoleInput input) {
        this.input = input;
        this.lastMove = null;
        this.nonProgressTableauMoves = 0;
        this.moveNumber = 0;
        this.lastMoveDescription = null;
        this.progressMadeThisCycle = false;
        this.lost = false;
    }

    @Override
    public void initialize() throws Exception {
        // Load and shuffle using your existing implementation.
        System.out.println("\n=== Hello, welcome to Hannah's Solitaire Game ===");

        table = new SolitaireTable();

        if (!dealInitialTableau()) {
            lost = true;
            return;
        }
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
            System.out.println();
            System.out.println(TerminalColors.green(
                    "===== Congrats! Game Complete! ========="
            ));
        } else {
            System.out.println();
            System.out.println(TerminalColors.red(
                    "========= Oh noooo... Game Lost ========="
            ));
        }
    }

    private boolean dealInitialTableau() throws Exception {
        System.out.println("Enter the path to a card deck file.");
        System.out.println("If you omit the .txt extension, it will be added automatically.");
        System.out.println("Example: src/decks/input");
        System.out.println("\nAvailable deck files:");

        File deckFolder = new File("src/decks");
        File[] files = deckFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    System.out.println("- " + file.getName());
                }
            }
        }

        String inputPath;
        Deck inputDeck;
        String cardRegex = "^[DHSC]-(A|[2-9]|10|J|Q|K)$";

        while (true) {
            inputPath = input.askFile().trim();

            if (!inputPath.toLowerCase().endsWith(".txt")) {
                inputPath += ".txt";
            }

            File file = new File(inputPath);
            if (!file.exists()) {
                System.out.println("File does not exist. Please try again.");
                continue;
            }

            if (!file.isFile()) {
                System.out.println("Path is not a file. Please try again.");
                continue;
            }

            String data = DeckFileReader.readFileAsString(inputPath);
            String inputData = data.replace("Initial card sequence: ", "");
            StringTokenizer tokenizer = new StringTokenizer(inputData, ",");

            inputDeck = new Deck();
            boolean validDeck = true;
            while (tokenizer.hasMoreTokens()) {
                String cardString = tokenizer.nextToken().trim();

                if (!cardString.matches(cardRegex)) {
                    printInvalidCardMessage(cardString);
                    validDeck = false;
                    break;
                }

                inputDeck.addToBottom(Card.fromString(cardString));
            }

            if (!validDeck) {
                System.out.println("Please choose a valid deck file.");
                continue;
            }

            if (inputDeck.size() != 52) {
                System.out.println("Deck must contain exactly 52 cards. Please try again.");
                continue;
            }

            break;
        }

        System.out.println("\nDeck from " + inputPath + ": ");
        inputDeck.printed();

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

        return true;
    }

    private void printInvalidCardMessage(String cardString) {
        System.out.println("\nInvalid card: " + cardString);
        System.out.println("Valid card format: <Suit>-<Rank>");
        System.out.println("Examples: D-A (Ace of Diamonds), S-10 (10 of Spades), H-K (King of Hearts)");
        System.out.println("Valid suits: C (Clubs), D (Diamonds), H (Hearts), S (Spades)");
        System.out.println("Valid ranks: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J (Jack), Q (Queen), K (King)");
    }

    private boolean tryTableauToFoundation() {
        for (int stackIndex = 0; stackIndex < TABLEAU_COUNT; stackIndex++) {
            TableauPile stack = table.getTableau().get(stackIndex);
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
                    recordMove(
                            "Move " + cardCode(movedCard)
                                    + " from Stack " + (stackIndex + 1)
                                    + " to " + foundationName(zone)
                    );
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
                    recordMove(
                            "Move " + cardCode(movedCard)
                                    + " from Stack " + (sourceIndex + 1)
                                    + " to Stack " + (destinationIndex + 1)
                    );
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
                    recordMove(
                            "Move " + cardCode(candidate)
                                    + " from Stack " + (sourceIndex + 1)
                                    + " to Stack " + (destinationIndex + 1)
                    );
                    return true;
                }
            }
        }

        return false;
    }

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
                recordMove(
                        "Move " + cardCode(movedCard)
                                + " from Talon Waste to " + foundationName(zone)
                );
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
                recordMove(
                        "Move " + cardCode(movedCard)
                                + " from Talon Waste to Stack " + (destinationIndex + 1)
                );
                return true;
            }
        }

        return false;
    }

    private void drawFromTalon() {
        int drawCount = Math.min(DRAW_COUNT, table.getTalon().size());
        table.drawFromTalon(DRAW_COUNT);
        recordMove(
                "Draw " + drawCount + (drawCount == 1 ? " card" : " cards")
                        + " from Talon to Talon Waste"
        );
    }

    private void recycleWasteIntoTalon() {
        table.recycleWaste();
        recordMove("Recycle Talon Waste into Talon");
    }

    private void recordMove(String description) {
        moveNumber++;
        lastMoveDescription = "Move " + moveNumber + ": " + description;
    }

    private String cardCode(SolitaireCard solitaireCard) {
        Card card = solitaireCard.getCard();
        String code = card.getSuit().getCode() + "-" + card.getRank().getSymbol();
        return TerminalColors.colorize(card.getSuit(), code);
    }

    private String foundationName(FoundationPile foundation) {
        return foundation.getSuit().getCode() + " Foundation";
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
        System.out.println();
        if (lastMoveDescription != null) {
            System.out.println(lastMoveDescription);
        }
        System.out.println("==================================================");
    }
}
