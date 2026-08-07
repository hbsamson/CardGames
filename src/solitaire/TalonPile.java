package solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalonPile {
    private final List<SolitaireCard> cards;

    public TalonPile() {
        this.cards = new ArrayList<>();
    }

    public void addCard(SolitaireCard card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null.");
        }

        card.turnFaceDown();
        cards.add(card);
    }

    public void addCards(List<SolitaireCard> cardsToAdd) {
        if (cardsToAdd == null) {
            throw new IllegalArgumentException("Cards cannot be null.");
        }

        for (SolitaireCard card : cardsToAdd) {
            addCard(card);
        }
    }

    public SolitaireCard drawTopCard() {
        if (isEmpty()) {
            return null;
        }

        return cards.remove(cards.size() - 1);
    }

    public List<SolitaireCard> drawCards(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Draw count must be greater than zero.");
        }

        List<SolitaireCard> drawnCards = new ArrayList<>();

        for (int i = 0; i < count && !isEmpty(); i++) {
            SolitaireCard card = drawTopCard();
            card.turnFaceUp();
            drawnCards.add(card);
        }

        return drawnCards;
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
        return "Talon: " + size() + " card(s)";
    }
}