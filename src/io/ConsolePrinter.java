package io;

import core.Card;

import java.util.List;

public class ConsolePrinter {

    public void printDeck(String title, List<Card> deck) {
        System.out.println("\n" + title);
        System.out.println(deck);
    }

    public void printWinner(String winner) {
        System.out.println("\n===== " + winner + " IS THE WINNER =====");
    }

    public void printPlayerDeck(int player, List<Card> deck) {
        System.out.println(
                "Player " + player + "'s Deck: " + deck
        );
    }
}
