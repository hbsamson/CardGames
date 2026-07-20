package solitaire;

import core.Suit;

import java.util.ArrayList;
import java.util.List;

public class SolitaireTable {
    public static final int TABLEAU_COUNT = 7;
    public static final int FOUNDATION_COUNT = 4;

    private final List<TableauPile> m_stack;
    private final List<FoundationPile> f_stack;
    private final TalonPile talon;
    private final WastePile waste;

    public SolitaireTable() {
        m_stack = new ArrayList<>();
        f_stack = new ArrayList<>();

        for (int i = 0; i < TABLEAU_COUNT; i++) {
            m_stack.add(new TableauPile());
        }

        f_stack.add(new FoundationPile(Suit.SPADES));
        f_stack.add(new FoundationPile(Suit.CLUBS));
        f_stack.add(new FoundationPile(Suit.HEARTS));
        f_stack.add(new FoundationPile(Suit.DIAMONDS));

        talon = new TalonPile();
        waste = new WastePile();
    }

    public List<TableauPile> getTableau() {
        return m_stack;
    }

    public List<FoundationPile> getFoundations() {
        return f_stack;
    }

    public TalonPile getTalon() {
        return talon;
    }

    public WastePile getWaste() {
        return waste;
    }

    public void printTableau() {
        int maxHeight = 0;

        // Find the tallest tableau pile
        for (TableauPile pile : m_stack) {
            maxHeight = Math.max(maxHeight, pile.size());
        }

        System.out.println("---------------------------------------------------");
        for (int row = 0; row < maxHeight; row++) {
            for (TableauPile pile : m_stack) {
                if (row < pile.size()) {
                    System.out.printf("%-8s", pile.getCard(row));
                } else {
                    System.out.printf("%-8s", "");
                }
            }

            System.out.println();
        }
        System.out.println("---------------------------------------------------");
    }

    public void printFoundations() {
        for (FoundationPile pile : f_stack) {
            System.out.printf("%-8s", pile.getSymbol());
        }
        System.out.println();

        // cards ranks here (A-K format)
        for (FoundationPile pile : f_stack) {
            System.out.printf("%-8s", pile.getProgress());
        }
        System.out.println();
    }



    public void printTalon() {
        System.out.println(talon.getCards());
    }

    public void printWaste() {
        System.out.println(waste.getCards());
    }


    public boolean areFoundationsComplete() {
        for (FoundationPile foundation : f_stack) {
            if (!foundation.isComplete()) {
                return false;
            }
        }

        return true;
    }

    public void drawFromTalon(int drawCount) {
        List<SolitaireCard> drawnCards = talon.drawCards(drawCount);
        waste.addCards(drawnCards);
    }

    public void recycleWaste() {
        talon.addCards(waste.getCards());
        List<SolitaireCard> recycledCards = waste.removeAllCards();
    }
}