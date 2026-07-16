package core;

public enum Suit {
    DIAMONDS("D"),
    HEARTS("H"),
    SPADES("S"),
    CLUBS("C");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Suit fromString(String value) {
        for (Suit suit : values()) {
            if (suit.symbol.equals(value)) {
                return suit;
            }
        }

        throw new IllegalArgumentException("Invalid suit: " + value);
    }
}
