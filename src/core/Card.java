package core;

import java.util.Objects;

public final class Card {
    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;

    public Card(Suit suit, Rank rank) {
        this(suit, rank, false);
    }

    public Card(Suit suit, Rank rank, boolean faceUp) {
        this.suit = Objects.requireNonNull(suit);
        this.rank = Objects.requireNonNull(rank);
        this.faceUp = faceUp;
    }

    public static Card fromString(String cardString) {
        String[] parts = cardString.split("-");

        Suit suit = Suit.fromString(parts[0]);
        Rank rank = Rank.fromString(parts[1]);

        return new Card(suit, rank);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
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

    public boolean isRed() {
        return suit == Suit.DIAMONDS || suit == Suit.HEARTS;
    }

    @Override
//    public String toString() {
//        return rank + " of " + suit;
//    }
    public String toString() {
        return suit.getSymbol() + "-" + rank.getSymbol();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Card other)) {
            return false;
        }

        return rank == other.rank && suit == other.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
