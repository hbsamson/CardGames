package solitaire;

import core.Suit;
import core.TerminalColors;

import java.util.ArrayList;
import java.util.List;

public class SolitaireTable {
    public static final int TABLEAU_COUNT = 7;

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

        for (TableauPile pile : m_stack) {
            maxHeight = Math.max(maxHeight, pile.size());
        }

        for (int stackIndex = 1; stackIndex <= TABLEAU_COUNT; stackIndex++) {
            System.out.printf("%-8s", "S" + stackIndex);
        }
        System.out.println();
        System.out.println("---------------------------------------------------");
        for (int row = 0; row < maxHeight; row++) {
            for (TableauPile pile : m_stack) {
                if (row < pile.size()) {
                    SolitaireCard card = pile.getCard(row);
                    if (card.isFaceUp()) {
                        System.out.print(TerminalColors.paddedCard(card.getCard(), 8));
                    } else {
                        System.out.printf("%-8s", card);
                    }
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
            System.out.print(TerminalColors.padded(
                    pile.getSuit(), pile.getSymbol(), 8
            ));
        }
        System.out.println();

        // cards ranks here (A-K format)
        for (FoundationPile pile : f_stack) {
            System.out.print(TerminalColors.padded(
                    pile.getSuit(), pile.getProgress(), 8
            ));
        }
        System.out.println();
    }



    public void printTalon() {
        System.out.println(talon.getCards());
    }

    public void printWaste() {
        System.out.println(waste);
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
