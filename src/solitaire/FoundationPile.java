package solitaire;

import core.Rank;
import core.Suit;

import java.util.ArrayList;
import java.util.List;

public class FoundationPile {
    private final Suit suit;
    private final List<SolitaireCard> cards;

    public FoundationPile(Suit suit) {
        this.suit = suit;
        this.cards = new ArrayList<>();
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean canAdd(SolitaireCard candidate) {
        if (candidate == null || !candidate.isFaceUp()) {
            return false;
        }

        if (cards.isEmpty()) {
            return candidate.getCard().getRank() == Rank.ACE;
        }

        SolitaireCard top = peekTopCard();

        Suit topSuit = top.getCard().getSuit();
        Suit candidateSuit =
                candidate.getCard().getSuit();

        int topRank = top.getCard().getRank().getValue();
        int candidateRank =
                candidate.getCard().getRank().getValue();

        return topSuit == candidateSuit
                && candidateRank == topRank + 1;
    }

    public void addCard(SolitaireCard card) {
        if (!canAdd(card)) {
            throw new IllegalArgumentException(
                    "Card cannot be added to foundation."
            );
        }

        cards.add(card);
    }

    public SolitaireCard peekTopCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public boolean isComplete() {
        return cards.size() == 13;
    }

    public String getProgress() {
        if (cards.isEmpty()) {
            return "Empty";
        }

        SolitaireCard top = peekTopCard();

        if (top.getRank() == Rank.ACE) {
            return "A";
        }

        return "A-" + top.getRank().getSymbol();
    }

    public String getSymbol() {
        return suit.getSymbol();
    }
}