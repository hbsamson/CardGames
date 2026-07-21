package ui;

import core.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.CardView;

public class WarView {
    private final BorderPane root;
    private final FlowPane playerArea;

    private final Label titleLabel;
    private final Label roundLabel;
    private final Label statusLabel;

    private final Button startButton;
    private final Button nextRoundButton;
    private final Button autoPlayButton;

    public WarView() {
        root = new BorderPane();
        root.setPadding(new Insets(20));

        titleLabel = new Label("War Card Game");
        titleLabel.setStyle("""
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                """);

        roundLabel = new Label("Round: 0");
        roundLabel.setStyle("-fx-font-size: 18px;");

        statusLabel = new Label("Press Start Game to begin.");
        statusLabel.setStyle("-fx-font-size: 16px;");

        VBox header = new VBox(
                8,
                titleLabel,
                roundLabel,
                statusLabel
        );

        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(0, 0, 20, 0));

        playerArea = new FlowPane();
        playerArea.setAlignment(Pos.CENTER);
        playerArea.setHgap(30);
        playerArea.setVgap(25);
        playerArea.setPadding(new Insets(20));

        startButton = new Button("Start Game");
        nextRoundButton = new Button("Next Round");
        autoPlayButton = new Button("Auto Play");

        nextRoundButton.setDisable(true);
        autoPlayButton.setDisable(true);

        HBox controls = new HBox(
                12,
                startButton,
                nextRoundButton,
                autoPlayButton
        );

        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20, 0, 0, 0));

        root.setTop(header);
        root.setCenter(playerArea);
        root.setBottom(controls);
    }

    public Parent getRoot() {
        return root;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getNextRoundButton() {
        return nextRoundButton;
    }

    public Button getAutoPlayButton() {
        return autoPlayButton;
    }

    public void setRoundNumber(int roundNumber) {
        roundLabel.setText("Round: " + roundNumber);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void clearPlayers() {
        playerArea.getChildren().clear();
    }

    public void addPlayer(
            String playerName,
            int cardCount,
            Card lastPlayedCard
    ) {
        Label nameLabel = new Label(playerName);
        nameLabel.setStyle("""
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                """);

        Label cardCountLabel =
                new Label("Cards remaining: " + cardCount);

        CardView playedCardView = new CardView();
        playedCardView.showCard(lastPlayedCard);

        Label playedCardLabel = new Label("Played card");

        VBox playerDisplay = new VBox(
                8,
                nameLabel,
                cardCountLabel,
                playedCardLabel,
                playedCardView
        );

        playerDisplay.setAlignment(Pos.CENTER);
        playerDisplay.setPadding(new Insets(15));
        playerDisplay.setPrefWidth(180);

        playerDisplay.setStyle("""
                -fx-background-color: rgba(255, 255, 255, 0.85);
                -fx-border-color: gray;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                """);

        playerArea.getChildren().add(playerDisplay);
    }

    public void setGameStarted(boolean started) {
        startButton.setDisable(started);
        nextRoundButton.setDisable(!started);
        autoPlayButton.setDisable(!started);
    }

    public void setGameOver() {
        nextRoundButton.setDisable(true);
        autoPlayButton.setDisable(true);
    }

    public void setAutoPlaying(boolean autoPlaying) {
        autoPlayButton.setText(
                autoPlaying ? "Stop Auto Play" : "Auto Play"
        );
    }
}