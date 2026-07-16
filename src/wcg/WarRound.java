package wcg;

import core.Card;

import java.util.ArrayList;
import java.util.List;

import static wcg.WarGame.NO_WINNER;
public class WarRound {
    public static String gameRound(int roundNumber, List<WarPlayer> players, List<Card> orderedDeck, List<Card> shuffledDeck) {
        List<Card> roundCards = new ArrayList<>();
        String winner = NO_WINNER;

        List<WarPlayer> toRemove = new ArrayList<>();
        for (WarPlayer player : players) {
            if (player.hasCards()) {
                Card playedCard = player.playCard();
                roundCards.add(playedCard);
            } else { // add players with no cards to toRemove list 
                // roundCards.add(null);
                toRemove.add(player);
            }
        }
        // players.removeAll(toRemove);

        // find highest card
        Card highest = null;
        for (Card element : orderedDeck) {
            if (roundCards.contains(element)) {
                highest = element;
                break;
            }
        }

        if (highest != null) {
            int winnerIndex = roundCards.indexOf(highest);
            WarPlayer winnerRound = players.get(winnerIndex);
            // System.out.println("Winner of round " + (round_number) + ": Player " + winner_round);

            // moving highest card to index 0
            roundCards.remove(winnerIndex);
            roundCards.addFirst(highest);

            // remove all losing player's cards
            roundCards.removeIf(card -> card == null);

            System.out.println("\nRound " + (roundNumber) + " Top Cards Played: " + roundCards);
            for (WarPlayer player : players) {
                System.out.println(player.getName() + "'s Cards in Hand: " + player.getHandAsString());
            }

            System.out.println("Winner of Round " + (roundNumber) + ": " + winnerRound.getName());

            // add round cards to winner's deck
            winnerRound.receiveCards(roundCards);
            System.out.println("Winner's deck (" + winnerRound.getName() + ") - " + winnerRound.getHandAsString());

            // finding winner, same card count as deck
            if (winnerRound.getCardCount() == orderedDeck.size()) {
                winner = winnerRound.getName();
                return winner;
            }

            // no other players have cards
            // if (players.size() == 1) {
            //     winner = players.get(0).getName();
            // }

            int zero_count = 0;
            for (WarPlayer player : players) {
                if (!player.hasCards()) {
                    zero_count += 1;
                } else {
                    winner = player.getName();
                }
            }

            if (zero_count == (players.size() - 1)) {
                return winner;
            }

        }
        return NO_WINNER;
    }

}