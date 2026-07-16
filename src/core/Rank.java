package core;

public enum Rank {
    ACE("A"),
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("10"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2");

    private final String symbol;

    Rank(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Rank fromString(String value) {
        for (Rank rank : values()) {
            if (rank.symbol.equals(value)) {
                return rank;
            }
        }

        throw new IllegalArgumentException("Invalid rank: " + value);
    }
}