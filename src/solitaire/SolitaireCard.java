package solitaire;

import core.Card;
import core.Rank;
import core.TerminalColors;

public class SolitaireCard {
    private final Card card;
    private boolean faceUp;

    public SolitaireCard(Card card) {
        this.card = card;
        this.faceUp = false;
    }

    public Card getCard() {
        return card;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void turnFaceUp() {
        faceUp = true;
    }

    public void turnFaceDown() {
        faceUp = false;
    }

    public Rank getRank() {
        return card.getRank();
    }

    @Override
    public String toString() {
        return faceUp ? TerminalColors.card(card) : "XX";
    }
}
