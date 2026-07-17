package wcg;

import core.Card;
import core.Game;
import io.ConsoleInput;
import io.DeckFileReader;
import shuffle.PerfectShuffle;
import shuffle.Shuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WarGame implements Game {
    public static String NO_WINNER = "none";
    public record Winner(String name, List<Card> deck) {}
    private final ConsoleInput input;

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

        // reads inOrder.txt for in order listing of cards
        String ordered_path = "src\\decks\\inOrder.txt";
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
        String path = input.askFile();
        String data = DeckFileReader.readFileAsString(ordered_path);
        String input_data = data.replace("Initial card sequence: ", "");
        StringTokenizer tokenizer = new StringTokenizer(input_data, ",");

        // placing cards (as tokens) into deck
        inputDeck = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String cardString = tokenizer.nextToken().trim();
            inputDeck.add(Card.fromString(cardString));
        }

        // print path txt file deck
        System.out.println("Deck from " + path + inputDeck.toString());

        // player count 2 <= n <= 8
        n = input.askPlayerCount();

        // shuffle inputDeck
        s = input.askShuffleCount();
        Shuffle shuffler = new PerfectShuffle();
        for (int i=0; i < s; i++) {
            shuffledDeck = shuffler.shuffle(inputDeck);
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
            roundNumber += 1;
            winner = WarRound.gameRound(roundNumber, players, orderedDeck, shuffledDeck);
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
        System.out.println("\n===== " + winner.name().toUpperCase() + " IS THE WINNER!!! =====");
        System.out.println(winner.deck());
    }

    public static List<Card> getExcessCards() {
        return excessCards;
    }
}
