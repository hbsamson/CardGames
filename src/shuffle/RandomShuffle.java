package shuffle;

import core.Card;

import java.util.Collections;
import java.util.List;

public class RandomShuffle implements Shuffle {
    @Override
    public List<Card> shuffle(List<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }
}
