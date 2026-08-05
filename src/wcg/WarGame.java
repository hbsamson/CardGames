package wcg;

import core.Card;
import core.Deck;
import core.Game;
import core.TerminalColors;
import io.ConsoleInput;
import io.DeckFileReader;
import shuffle.PerfectShuffle;
import shuffle.Shuffle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WarGame implements Game {
    public static String NO_WINNER = "none";
    public record Winner(String name, List<Card> deck) {}
    private final ConsoleInput input;
    private boolean initialized = false;

    // Game state shared by initialize(), play(), and displayResult()
    private List<Card> orderedDeck;
    private List<Card> inputDeck;
    private List<Card> shuffledDeck;
    private List<WarPlayer> players;
    public static List<Card> excessCards;
    private Winner winner;

    private int n;
    private int s;
    private int roundNumber;

    public WarGame(ConsoleInput input) {
        this.input = input;
        this.winner = new Winner(NO_WINNER, List.of());
        this.roundNumber = 0;
    }

    @Override
    public void initialize() throws Exception {
        // Read, shuffle and deal cards
        System.out.println("\n=== Hello, welcome to Hannah's War Card Game ===");

        // reads input.txt for in order listing of cards
        String ordered_path = "src/decks/input.txt";
        String ordered_data = DeckFileReader.readFileAsString(ordered_path);
        String cleaned_ordered_data = ordered_data.replace("Initial card sequence: ", "");
        String[] string_deck = cleaned_ordered_data.split(","); // convert to list of strings

        // empty card ordered card deck
        orderedDeck = new ArrayList<>();
        // converts string to Card class
        for (String card : string_deck) {
            orderedDeck.add(Card.fromString(card));
        }

        // uncomment for quick testing
        // n = 2;
        // s = 2;
        // String input_path = "src/decks/input.txt";
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

            String inputData = DeckFileReader.readFileAsString(inputPath);
            String cleanedInputData = inputData.replace("Initial card sequence: ", "");
            StringTokenizer tokenizer = new StringTokenizer(cleanedInputData, ",");

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

        // print path txt file deck
        initialized = true;
        System.out.println("\nDeck from " + inputPath + ": ");
//        System.out.println(inputDeck);
        inputDeck.printed();

        // player count 2 <= n <= 8
        n = input.askPlayerCount();
        
        // shuffle inputDeck
        s = input.askShuffleCount();
        Shuffle shuffler = new PerfectShuffle();
        shuffledDeck = shuffler.shuffle(inputDeck.asList());
        for (int i=0; i < s-1; i++) {
            shuffledDeck = shuffler.shuffle(shuffledDeck);
        }

        System.out.println("\nShuffled Deck:");
        printDeck(shuffledDeck);

        // initialize players
        players = new ArrayList<>();
        for (int i=0; i < n; i++) {
            players.add(new WarPlayer(i + 1));
        }

        // distribution of deck cards to players 0, 1, 2..., n, 0, 1, 2..., n
        int c = shuffledDeck.size()/n;
        excessCards = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < shuffledDeck.size(); i++) {
            if (players.get(index).getCardCount() < c) {
                players.get(index).receiveCard(shuffledDeck.get(i));
            } else {
                excessCards.add(shuffledDeck.get(i));
            }
            index = (index + 1) % n;
        }

        System.out.println();
        System.out.println("===================================================");
        for (WarPlayer player : players) {
            System.out.println(player.getName() + "'s Hand: " + player.getHandAsString());
        }
        System.out.println("===================================================");

    }

    @Override
    public void play() {
        if (!initialized) {
            return;
        }

        // Continue War rounds
        while (!isGameOver()) {
            playNextRound();
        }
    }
    @Override
    public boolean isGameOver() {
        // One player owns all cards.
        return !NO_WINNER.equals(winner.name());
    }

    @Override
    public void displayResult() {
        // Display War winner
        if (!initialized) {
            System.out.println("\n=== Game terminated due to invalid deck input ===");
            return;
        }

        System.out.println();
        System.out.println(TerminalColors.green(
                "===== " + winner.name().toUpperCase() + " IS THE WINNER!!! ====="
        ));
        printDeck(winner.deck());
        writeWinnerDeckToFile(winner.deck());
    }

    private void printDeck(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            System.out.println("[]");
            return;
        }

        Deck deckToPrint = new Deck(cards);
        deckToPrint.printed();
    }

    private void writeWinnerDeckToFile(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return;
        }

        StringBuilder content = new StringBuilder();
        for (Card card : cards) {
            if (content.length() > 0) {
                content.append(",");
            }
            content.append(card.getSuit().getCode()).append("-").append(card.getRank().getSymbol());
        }

        try {
            Path outputPath = Path.of("src", "decks", "output.txt");
            Files.createDirectories(outputPath.getParent());
            Files.writeString(
                    outputPath,
                    content.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            System.out.println("Unable to write output file: " + e.getMessage());
        }
    }

    public static List<Card> getExcessCards() {
        return excessCards;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<WarPlayer> getPlayers() {
        return List.copyOf(players);
    }

    public Winner getWinner() {
        return winner;
    }

    public void playNextRound() {
        if (!initialized) { // deck not initialized
            return;
        }

        updateWinner();

        if (isGameOver()) {
            return;
        }

        roundNumber += 1;
        WarRound.gameRound(
                roundNumber,
                players,
                orderedDeck,
                shuffledDeck
        );

        updateWinner();
    }

    private void printInvalidCardMessage(String cardString) {
        System.out.println("\nInvalid card: " + cardString);
        System.out.println("Valid card format: <Suit>-<Rank>");
        System.out.println("Examples: D-A (Ace of Diamonds), S-10 (10 of Spades), H-K (King of Hearts)");
        System.out.println("Valid suits: C (Clubs), D (Diamonds), H (Hearts), S (Spades)");
        System.out.println("Valid ranks: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J (Jack), Q (Queen), K (King)");
    }

    private void updateWinner() {
        WarPlayer remainingPlayer = null;
        int activePlayers = 0;

        for (WarPlayer player : players) {
            if (player.hasCards()) {
                activePlayers++;
                remainingPlayer = player;
            }
        }

        if (activePlayers == 1) {
            List<Card> outDeck = new ArrayList<>(
                    remainingPlayer.getHand().asList()
            );
            outDeck.addAll(excessCards);

            this.winner = new Winner(
                    remainingPlayer.getName(),
                    List.copyOf(outDeck)
            );
        }
    }
}
