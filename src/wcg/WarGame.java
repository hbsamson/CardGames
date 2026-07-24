package wcg;

import core.Card;
import core.Game;
import io.ConsoleInput;
import io.DeckFileReader;
import shuffle.PerfectShuffle;
import shuffle.Shuffle;

import java.io.File;
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
        String ordered_path = "src\\decks\\input.txt";
        String ordered_data = DeckFileReader.readFileAsString(ordered_path);
        String cleaned_ordered_data = ordered_data.replace("Initial card sequence: ", "");
        String[] string_deck = cleaned_ordered_data.split(","); // convert to list of strings

        // empty card ordered card deck
        orderedDeck = new ArrayList<>();
        // converts string to Card class
        for (String card : string_deck) {
            orderedDeck.add(Card.fromString(card));
        }

        // ask for path to input deck
//        String input_path = input.askFile();
        String input_path = "src/decks/input.txt";
        // file check
        File file = new File(input_path);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        if (!file.isFile()) {
            System.out.println("Path is not a file.");
            return;
        }

        String cardRegex = "^[DHSC]-(A|[2-9]|10|J|Q|K)$";
        String input_data = DeckFileReader.readFileAsString(input_path);
        String cleaned_input_data = input_data.replace("Initial card sequence: ", "");
        StringTokenizer tokenizer = new StringTokenizer(cleaned_input_data, ",");

        // placing cards (as tokens) into deck
        inputDeck = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String cardString = tokenizer.nextToken().trim();
            if (!cardString.matches(cardRegex)) { // checking if token matches card regex
                System.out.println("\nInvalid card: " + cardString);
                System.out.println("Valid card format: <Suit>-<Rank>");
                System.out.println("Examples: D-A (Ace of Diamonds), S-10 (10 of Spades), H-K (King of Hearts)");
                System.out.println("Valid suits: C (Clubs), D (Diamonds), H (Hearts), S (Spades)");
                System.out.println("Valid ranks: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J (Jack), Q (Queen), K (King)");
                return;
            }
            inputDeck.add(Card.fromString(cardString));
        }

        if (inputDeck.size() != 52) {
            System.out.println("Deck must contain exactly 52 cards.");
            return;
        }

        // print path txt file deck
        initialized = true;
        System.out.println("Deck from " + input_path + ": ");
        System.out.println(inputDeck.toString());

        // player count 2 <= n <= 8
//        n = input.askPlayerCount();
        n = 2;
//        s = 2;
        // shuffle inputDeck
        s = input.askShuffleCount();
        Shuffle shuffler = new PerfectShuffle();
        shuffledDeck = shuffler.shuffle(inputDeck);
        for (int i=0; i < s-1; i++) {
            shuffledDeck = shuffler.shuffle(shuffledDeck);
        }

        System.out.println("\nShuffled Deck:");
        System.out.println(shuffledDeck.toString());

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
        for (WarPlayer player : players) {
            System.out.println(player.getName() + "'s Hand: " + player.getHandAsString());
        }

    }

    @Override
    public void play() {
        // Continue War rounds
        while (!isGameOver()) {
            playNextRound();
        }

        displayResult();
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

        System.out.println("\n===== " + winner.name().toUpperCase() + " IS THE WINNER!!! =====");
        System.out.println(winner.deck());
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
        updateWinner();
        if (!initialized) { // deck not initialized
            return;
        }

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
            this.winner = new Winner(
                    remainingPlayer.getName(),
                    List.copyOf(remainingPlayer.getHand().asList())
            );
        }
    }
}
