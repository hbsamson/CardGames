package ui;

import core.Card;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CardImageLoader {
    private static final String CARD_DIRECTORY = "/cards/";
    private static final String CARD_BACK_FILENAME = "back.png";

    private final Map<String, Image> imageCache;

    public CardImageLoader() {
        imageCache = new HashMap<>();
    }

    public Image getCardImage(Card card) {
        if (card == null) {
            return null;
        }

        String filename = createFilename(card);
        return loadImage(filename);
    }

    public Image getCardBackImage() {
        return loadImage(CARD_BACK_FILENAME);
    }

    private String createFilename(Card card) {
        return card.getSuit().getCode()
                + "-"
                + card.getRank().getSymbol()
                + ".png";
    }

    private Image loadImage(String filename) {
        return imageCache.computeIfAbsent(
                filename,
                this::loadUncachedImage
        );
    }

    private Image loadUncachedImage(String filename) {
        String resourcePath = CARD_DIRECTORY + filename;

        URL resourceUrl =
                CardImageLoader.class.getResource(resourcePath);

        if (resourceUrl == null) {
            throw new IllegalStateException(
                    "Card image not found: " + resourcePath
            );
        }

        return new Image(resourceUrl.toExternalForm());
    }
}