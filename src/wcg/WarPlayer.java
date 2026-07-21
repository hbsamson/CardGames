package wcg;

import core.Card;
import core.Deck;
import core.Player;

import java.util.Collection;

public class WarPlayer extends Player {
    private final Deck hand;
    private Card lastPlayedCard;

    public WarPlayer(int playerNumber) {
        super("Player " + playerNumber);
        hand = new Deck();
    }

    public WarPlayer(String name) {
        super("Player " + name);
        hand = new Deck();
    }

//    public Card playCard() {
//        return hand.drawTopCard();
//    }

    public Card playCard() {
        lastPlayedCard = hand.drawTopCard(); // use your existing draw operation
        return lastPlayedCard;
    }

    public void receiveCard(Card card) {
        hand.addToBottom(card);
    }

    public void receiveCards(Collection<Card> cards) {
        hand.addAllToBottom(cards);
    }

    public int getCardCount() {
        return hand.size();
    }

    public boolean hasCards() {
        return !hand.isEmpty();
    }

    public Deck getHand() {
        return hand;
    }

    public String getHandAsString() {
        return hand.asList().toString();
    }


    public String getName() {
        return name;
    }

    public Card getLastPlayedCard() {
        return lastPlayedCard;
    }
}