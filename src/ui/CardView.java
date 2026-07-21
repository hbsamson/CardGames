package ui;

import core.Card;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CardView extends StackPane {
    private static final double CARD_WIDTH = 90;
    private static final double CARD_HEIGHT = 126;

    private static final CardImageLoader IMAGE_LOADER =
            new CardImageLoader();

    private final ImageView imageView;

    public CardView() {
        imageView = new ImageView();

        imageView.setFitWidth(CARD_WIDTH);
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        setAlignment(Pos.CENTER);

        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        setMinSize(CARD_WIDTH, CARD_HEIGHT);
        setMaxSize(CARD_WIDTH, CARD_HEIGHT);

        getChildren().add(imageView);

        showEmpty();
    }

    public CardView(Card card) {
        this();
        showCard(card);
    }

    public void showCard(Card card) {
        if (card == null) {
            showEmpty();
            return;
        }

        imageView.setImage(
                IMAGE_LOADER.getCardImage(card)
        );

        imageView.setVisible(true);

        setStyle("""
                -fx-background-color: transparent;
                -fx-border-color: transparent;
                """);
    }

    public void showCard(Card card, boolean faceUp) {
        if (faceUp) {
            showCard(card);
        } else {
            showFaceDown();
        }
    }

    public void showFaceDown() {
        imageView.setImage(
                IMAGE_LOADER.getCardBackImage()
        );

        imageView.setVisible(true);

        setStyle("""
                -fx-background-color: transparent;
                -fx-border-color: transparent;
                """);
    }

    public void showEmpty() {
        imageView.setImage(null);
        imageView.setVisible(false);

        setStyle("""
                -fx-background-color: rgba(255, 255, 255, 0.08);
                -fx-border-color: rgba(255, 255, 255, 0.55);
                -fx-border-width: 2;
                -fx-border-style: dashed;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                """);
    }


}