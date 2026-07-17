package solitaire;

public record SolitaireMove(
        SolitaireMoveType type,
        int sourcePile,
        int destinationPile,
        int sourceCardIndex
) {
    public boolean reverses(SolitaireMove previousMove) {
        if (previousMove == null) {
            return false;
        }

        return type == SolitaireMoveType.TABLEAU_TO_TABLEAU
                && previousMove.type() == SolitaireMoveType.TABLEAU_TO_TABLEAU
                && sourcePile == previousMove.destinationPile()
                && destinationPile == previousMove.sourcePile();
    }
}