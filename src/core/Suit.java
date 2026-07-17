package core;

public enum Suit {
    DIAMONDS("D", "♦"),
    HEARTS("H", "♥"),
    SPADES("S", "♠"),
    CLUBS("C", "♣");

    private final String code;
    private final String symbol;

    Suit(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Suit fromString(String value) {
        for (Suit suit : values()) {
            if (suit.code.equals(value)) {
                return suit;
            }
        }

        throw new IllegalArgumentException("Invalid suit: " + value);
    }
}
