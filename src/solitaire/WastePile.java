package solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WastePile {
    private final List<SolitaireCard> cards;

    public WastePile() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a revealed card to the top of the waste pile.
     */
    public void addCard(SolitaireCard card) {
        if (card == null) {
            return;
        }

        card.turnFaceUp();
        cards.add(card);
    }

    /**
     * Adds multiple drawn cards to the waste pile.
     * The last card added becomes the exposed top card.
     */
    public void addCards(List<SolitaireCard> cardsToAdd) {
        for (SolitaireCard card : cardsToAdd) {
            addCard(card);
        }
    }

    /**
     * Returns the exposed top card without removing it.
     */
    public SolitaireCard peekTopCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    /**
     * Removes and returns the exposed top card.
     */
    public SolitaireCard removeTopCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.remove(cards.size() - 1);
    }

    /**
     * Removes all cards for recycling back into the talon.
     */
    public List<SolitaireCard> removeAllCards() {
        List<SolitaireCard> recycled = new ArrayList<>(cards);
        cards.clear();
        return recycled;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    public List<SolitaireCard> getCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public String toString() {
        if (cards.isEmpty()) {
            return "[]";
        }

        return cards.toString();
    }
}