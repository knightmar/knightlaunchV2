package fr.knightmar.panes;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainPane extends Pane {
    private final Line loginSeparator = new Line();
    private final VBox loginPane = new VBox();
    private final Stage primaryStage;
    private int width;
    private int height;
    private final CheckBox cracked = new CheckBox("Play in cracked mod");
    private final TextField pseudo = new TextField();
    private final Button login = new Button("Login");
    private final Button login_ms = new Button();


    public MainPane(Stage primaryStage, int width, int height) throws IllegalAccessException {
        this.primaryStage = primaryStage;
        this.width = width;
        this.height = height;
        setupUiProperties();
        setupUiActions();
        this.getChildren().addAll(loginPane, loginSeparator, cracked, pseudo, login, login_ms);
    }

    private void setupUiActions() {
        cracked.setOnAction((actionEvent) -> setCracked(cracked.isSelected()));
    }


    private void setupUiProperties() {
        // Setup the loginPane
        loginPane.setMinSize(300, height - 40);
        loginPane.setId("login_pane");
        loginPane.setTranslateX(20);
        loginPane.setTranslateY(20);

        // Setup the login with ms button
        ImageView login_ms_image = new ImageView(new Image("images/logo_ms.png"));
        login_ms_image.setPreserveRatio(true);
        login_ms_image.setFitWidth(180d);
        login_ms.setGraphic(login_ms_image);
        login_ms.setPrefWidth(200);
        login_ms.setPrefHeight(30);
        login_ms.setTranslateX(20 + (loginPane.getMinWidth() / 2.0) - (login_ms.getPrefWidth() / 2.0));
        login_ms.setTranslateY((height / 2.0) - 70 + 100);
        login_ms.setId("login_ms");


        // Setup the loginSeparator
        loginSeparator.setStartX(23);
        loginSeparator.setStartY((height / 2.0) + 50 + 100);
        loginSeparator.setEndX(17 + loginPane.getMinWidth());
        loginSeparator.setEndY((height / 2.0) + 50 + 100);
        loginSeparator.setId("login_separator");
        loginSeparator.setStroke(Color.BLACK);
        loginSeparator.setStrokeWidth(3);

        // Setup the cracked checkbox
        cracked.setTranslateX(85);
        cracked.setTranslateY((height / 2.0) + 10 + 100);
        cracked.setId("cracked");
        cracked.setSelected(false);

        // Setup the pseudo textfield
        pseudo.setId("pseudo");
        pseudo.setPromptText("Pseudo");
        pseudo.setPrefWidth(200);
        pseudo.setPrefHeight(30);
        pseudo.setTranslateX(20 + (loginPane.getMinWidth() / 2.0) - (pseudo.getPrefWidth() / 2.0));
        pseudo.setTranslateY((height / 2.0) + 70 + 100);

        // Setup the login button
        login.setId("login");
        login.setPrefWidth(200);
        login.setPrefHeight(30);
        login.setTranslateX(20 + (loginPane.getMinWidth() / 2.0) - (login.getPrefWidth() / 2.0));
        login.setTranslateY((height / 2.0) + 110 + 100);


        // Setup the global window properties
        this.getStylesheets().add("styles/MainPane.css");
        this.setId("root");
    }

    private void setCracked(boolean cracked) {
        pseudo.setDisable(cracked);
        login.setDisable(cracked);
    }
}
