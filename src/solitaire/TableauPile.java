package solitaire;

import core.Rank;
import core.Suit;

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

    public SolitaireCard getBottomCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public SolitaireCard getCard(int index) {
        return cards.get(index);
    }

    public SolitaireCard removeBottomCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
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

    public void revealBottomCard() {
        SolitaireCard botCard = getBottomCard();

        if (botCard != null && !botCard.isFaceUp()) {
            botCard.turnFaceUp();
        }
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public boolean canAdd(SolitaireCard candidate, int size) {
        if (candidate == null || !candidate.isFaceUp()) {
            return false;
        }

        SolitaireCard bot = peekBottomCard();

        // moving king to empty
        if (bot == null) { // no card in stack (empty)
            if (cards.isEmpty() && size != 1) { // destination is empty && source is not already in empty stack
                return candidate.getCard().getRank() == Rank.KING;
            }
        } else { // else compare with bottom card
            boolean botSuit = bot.getCard().isRed(); // is dest is red suit
            boolean candidateSuit = candidate.getCard().isRed(); // is source red suit

            int botRank = bot.getCard().getRank().getValue();
            int candidateRank = candidate.getCard().getRank().getValue();

            return candidateRank == botRank - 1 && candidateSuit != botSuit ; // card is 1 less than and different suit
        }
        return false;
    }

    public SolitaireCard peekBottomCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public int getFirstFaceUpIndex() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isFaceUp()) {
                return i;
            }
        }

        return -1;
    }

    public SolitaireCard getFirstFaceUpCard() {
        int index = getFirstFaceUpIndex();

        if (index == -1) {
            return null;
        }

        return cards.get(index);
    }
}