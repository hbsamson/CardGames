package core;

public interface Game {
    void initialize() throws Exception;

    void play();

    boolean isGameOver();

    void displayResult();
}
