package ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("Hannah's Card Games");

        Button warButton = new Button("War Card Game");
        Button solitaireButton = new Button("Solitaire");

        warButton.setOnAction(event ->
                System.out.println("Open War Card Game")
        );

        solitaireButton.setOnAction(event ->
                System.out.println("Open Solitaire")
        );

        VBox root = new VBox(
                20,
                title,
                warButton,
                solitaireButton
        );

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Card Games");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}