package core;

import java.util.Collection;
import java.util.stream.Collectors;

public final class TerminalColors {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private TerminalColors() {
    }

    public static String colorize(Suit suit, String text) {
        String color = suit == Suit.HEARTS || suit == Suit.DIAMONDS
                ? RED
                : BLUE;
        return color + text + RESET;
    }

    public static String green(String text) {
        return GREEN + text + RESET;
    }

    public static String red(String text) {
        return RED + text + RESET;
    }

    public static String card(Card card) {
        return colorize(card.getSuit(), card.toString());
    }

    public static String cards(Collection<Card> cards) {
        return cards.stream()
                .map(TerminalColors::card)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public static String paddedCard(Card card, int width) {
        return padded(card.getSuit(), card.toString(), width);
    }

    public static String padded(Suit suit, String text, int width) {
        return colorize(suit, String.format("%-" + width + "s", text));
    }
}
