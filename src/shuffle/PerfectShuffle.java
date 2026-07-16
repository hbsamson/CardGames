package shuffle;

import core.Card;
import java.util.ArrayList;
import java.util.List;

public class PerfectShuffle implements Shuffle {
    @Override
    public List<Card> shuffle(List<Card> deck) {
        int mid = deck.size() / 2;

        List<Card> firstHalf = deck.subList(0, mid);
        List<Card> secondHalf = deck.subList(mid, deck.size());
        List<Card> shuffledDeck = new ArrayList<>(deck.size());

        int maxHalfSize = Math.max(firstHalf.size(), secondHalf.size());
        for (int j=0; j < maxHalfSize; j++) {
            shuffledDeck.add(firstHalf.get(j));
            if (j < secondHalf.size()) {
                shuffledDeck.add(secondHalf.get(j));
            }
        }

        return shuffledDeck;
    }
}
