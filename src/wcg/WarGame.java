package wcg;

import core.Card;
import core.Game;
import io.ConsoleInput;
import io.DeckFileReader;
import shuffle.PerfectShuffle;
import shuffle.Shuffle;

import java.util.ArrayList;
import java.util.List;

public class WarGame implements Game {
    public static String NO_WINNER = "none";

    private final ConsoleInput input;

    // Game state shared by initialize(), play(), and displayResult()
    private List<Card> orderedDeck;
    private List<Card> shuffledDeck;
    private List<WarPlayer> players;
    private List<Card> excessCards;

    public int n;
    private int s;
    private int roundNumber;
    private String winner;

    public WarGame(ConsoleInput input) {
        this.input = input;
        this.winner = NO_WINNER;
        this.roundNumber = 0;
    }

    @Override
    public void initialize() throws Exception {
        // Read, shuffle and deal cards
        System.out.println("\n=== Hello, welcome to Hannah's War Card Game ===");

        // read cards from file
        // String path = input.askFile();
        String path = "C:\\Users\\hannah.samson\\IdeaProjects\\CardGames\\src\\decks\\input.txt";
        String data = DeckFileReader.readFileAsString(path);
        String input_data = data.replace("Initial card sequence: ", "");
        String[] stringDeck = input_data.split(","); // convert to list of strings

        // empty card deck
        orderedDeck = new ArrayList<>();

        // converts string to Card class
        for (String card : stringDeck) {
            orderedDeck.add(Card.fromString(card));
        }

        // print path txt file deck
        System.out.println("Deck from " + path + orderedDeck.toString());

//        for (int i=0; i < deck.size(); i++) {
//            System.out.println(deck.get(i));
//        }

        // player count 2 <= n <= 8
        n = input.askPlayerCount();

        // shuffle
        s = input.askShuffleCount();
        Shuffle shuffler = new PerfectShuffle();
        for (int i=0; i < s; i++) {
            shuffledDeck = shuffler.shuffle(orderedDeck);
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
        return !NO_WINNER.equals(winner);
    }

    @Override
    public void displayResult() {
        // Display War winner
        System.out.println("\n===== " + winner.toUpperCase() + " IS THE WINNER!!! =====");
    }
}
