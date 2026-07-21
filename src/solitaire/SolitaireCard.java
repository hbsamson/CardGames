package solitaire;

import core.Card;
import core.Rank;
import core.Suit;
import ui.CardView;

public class SolitaireCard {
    private final Card card;
    private boolean faceUp;

    CardView cardView = new CardView();

    cardView.showCard(
            solitaireCard.getCard(),
            solitaireCard.isFaceUp()
            );

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

    public Suit getSuit() {
        return card.getSuit();
    }

    public String toTableauString() {
        return faceUp ? card.toString() : " X ";
    }

    @Override
    public String toString() {
        return faceUp ? card.toString() : "XX";
    }


}