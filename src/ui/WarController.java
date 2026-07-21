package ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import wcg.WarGame;
import wcg.WarPlayer;

public class WarController {
    private static final Duration ROUND_DELAY =
            Duration.millis(500);

    private final WarGame game;
    private final WarView view;
    private final Timeline autoPlayTimeline;

    private boolean gameStarted;

    public WarController(
            WarGame game,
            WarView view
    ) {
        this.game = game;
        this.view = view;
        this.gameStarted = false;

        autoPlayTimeline = new Timeline(
                new KeyFrame(
                        ROUND_DELAY,
                        event -> playNextRound()
                )
        );

        autoPlayTimeline.setCycleCount(
                Animation.INDEFINITE
        );

        configureActions();
    }

    private void configureActions() {
        view.getStartButton().setOnAction(
                event -> startGame()
        );

        view.getNextRoundButton().setOnAction(
                event -> playNextRound()
        );

        view.getAutoPlayButton().setOnAction(
                event -> toggleAutoPlay()
        );
    }

    private void startGame() {
        try {
            game.initialize();
            gameStarted = true;

            view.setGameStarted(true);
            view.setStatus("Game initialized.");

            render();
        } catch (Exception exception) {
            view.setStatus(
                    "Unable to start game: "
                            + exception.getMessage()
            );

            exception.printStackTrace();
        }
    }

    private void playNextRound() {
        if (!gameStarted || game.isGameOver()) {
            stopAutoPlay();
            return;
        }

        game.playNextRound();
        render();

        if (game.isGameOver()) {
            stopAutoPlay();
            view.setGameOver();

            view.setStatus(
                    "Winner: " + game.getWinner().name()
            );
        }
    }

    private void toggleAutoPlay() {
        if (autoPlayTimeline.getStatus()
                == Animation.Status.RUNNING) {

            stopAutoPlay();
            return;
        }

        if (!gameStarted || game.isGameOver()) {
            return;
        }

        autoPlayTimeline.play();
        view.setAutoPlaying(true);
        view.setStatus("Auto play running...");
    }

    private void stopAutoPlay() {
        autoPlayTimeline.stop();
        view.setAutoPlaying(false);
    }

    private void render() {
        view.setRoundNumber(game.getRoundNumber());
        view.clearPlayers();

        for (WarPlayer player : game.getPlayers()) {
            view.addPlayer(
                    player.getName(),
                    player.getCardCount(),
                    player.getLastPlayedCard()
            );
        }

        if (!game.isGameOver()) {
            view.setStatus(
                    "Round " + game.getRoundNumber()
                            + " completed."
            );
        }
    }
}