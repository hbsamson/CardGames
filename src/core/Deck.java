package core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

public class Deck {
    private final Deque<Card> cards;

    public Deck() {
        cards = new ArrayDeque<>();
    }

    public Deck(Collection<Card> cards) {
        this.cards = new ArrayDeque<>(cards);
    }

    public Card drawTopCard() {
        return cards.pollFirst();
        // pollFirst() method retrieves and removes the first element of a collection, or returns null if the collection is empty
    }

    public Card viewTopCard() {
        return cards.peekFirst();
    }

    public void addToBottom(Card card) {
        cards.offerLast(card);
    }

    public void addAllToBottom(Collection<Card> cardsToAdd) {
        cards.addAll(cardsToAdd);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<Card> asList() {
        return new ArrayList<>(cards);
    }
}
