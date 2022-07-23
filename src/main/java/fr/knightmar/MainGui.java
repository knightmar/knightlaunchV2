package fr.knightmar;

import fr.knightmar.panes.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGui extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        int width = 1280, height = 720;

        Scene scene = new Scene(new MainPane(primaryStage, width, height), width, height);
        primaryStage.setResizable(false);
        primaryStage.setTitle("First JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
