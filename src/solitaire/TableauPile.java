package solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableauPile {
    private final List<SolitaireCard> cards;

    public TableauPile() {
        cards = new ArrayList<>();
    }

    public void addCard(SolitaireCard card) {
        cards.add(card);
    }

    public void addCards(List<SolitaireCard> cardsToAdd) {
        cards.addAll(cardsToAdd);
    }

    public SolitaireCard getTopCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public SolitaireCard getCard(int index) {
        return cards.get(index);
    }

    public List<SolitaireCard> removeCardsFrom(int index) {
        List<SolitaireCard> removedCards =
                new ArrayList<>(cards.subList(index, cards.size()));

        cards.subList(index, cards.size()).clear();

        return removedCards;
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

    public void revealTopCard() {
        SolitaireCard topCard = getTopCard();

        if (topCard != null && !topCard.isFaceUp()) {
            topCard.turnFaceUp();
        }
    }

    @Override
    public String toString() {
        return cards.toString();
    }


}