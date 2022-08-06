package fr.knightmar.panes;

import fr.knightmar.MainGui;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LoginPane extends Pane {
    private final Line loginSeparator = new Line();
    private final VBox loginPane = new VBox();
    private final int width;
    private final int height;
    private final CheckBox cracked = new CheckBox("Play in cracked mode");
    private final TextField pseudo = new TextField();
    private final Button login = new Button("Login");
    private final Button login_ms = new Button();
    private final Label login_label = new Label("Login");


    public LoginPane(int width, int height) {

        this.setOnMousePressed(event -> {
            MainGui.getInstance().xOffset = event.getSceneX();
            MainGui.getInstance().yOffset = event.getSceneY();
        });
        this.setOnMouseDragged(event -> {
            MainGui.getInstance().getPrimaryStage().setX(event.getScreenX() - MainGui.getInstance().xOffset);
            MainGui.getInstance().getPrimaryStage().setY(event.getScreenY() - MainGui.getInstance().yOffset);
        });



        this.width = width;
        this.height = height;
        setupUiProperties();
        setupUiActions();
        this.getChildren().addAll(loginPane, cracked, pseudo, login, login_ms, login_label);
        cracked.setSelected(false);
        setCracked(false);
    }

    private void setupUiActions() {
        cracked.setOnAction((actionEvent) -> setCracked(cracked.isSelected()));
    }


    private void setupUiProperties() {
        // Setup the loginPane
        loginPane.setMinSize(width / 2.0, (height / 3.0) * 2);
        loginPane.setId("login_pane");
        loginPane.setTranslateX((width / 2.0) - loginPane.getMinWidth() / 2);
        loginPane.setTranslateY((height / 2.0) - loginPane.getMinHeight() / 2);

        // Setup the login label
        login_label.setText("Login");
        login_label.setId("login_label");
        login_label.setMinWidth(100);
        login_label.setTranslateX(loginPane.getTranslateX() + loginPane.getMinWidth() / 2 - login_label.getMinWidth() / 2);
        login_label.setTranslateY(loginPane.getTranslateY() + login_label.getMinHeight() / 2);


        // Setup the login with ms button
        ImageView login_ms_image = new ImageView(new Image("images/logo_ms.png"));
        login_ms_image.setPreserveRatio(true);
        login_ms_image.setFitWidth(180d);
        login_ms.setGraphic(login_ms_image);
        login_ms.setPrefWidth(200);
        login_ms.setPrefHeight(30);
        login_ms.setTranslateX(loginPane.getTranslateX() + loginPane.getMinWidth() / 2 - login_ms.getPrefWidth() / 2);
        login_ms.setTranslateY((height / 4.0) * 1.5);
        login_ms.setId("login_ms");
        login_ms.setOnAction((event -> {
            login_ms.setDisable(true);
            MainGui.getInstance().getLoginManager().LoginWithMs(event);
        }));


        // Setup the loginSeparator
        loginSeparator.setStartX(loginPane.getTranslateX());
        loginSeparator.setStartY(loginPane.getMinHeight() / 2.0);
        loginSeparator.setEndX(loginPane.getTranslateX() + loginPane.getMinWidth());
        loginSeparator.setEndY((loginPane.getMinHeight() / 2.0));
        loginSeparator.setId("login_separator");
        loginSeparator.setStroke(Color.BLACK);
        loginSeparator.setStrokeWidth(3);

        // Setup the cracked checkbox
        cracked.setPrefWidth(185);
        cracked.setTranslateX(loginPane.getTranslateX() + loginPane.getMinWidth() / 2 - cracked.getPrefWidth() / 2);
        cracked.setTranslateY((height / 3.0) * 1.5);
        cracked.setId("cracked");
        cracked.setSelected(false);

        // Setup the pseudo textfield
        pseudo.setId("pseudo");
        pseudo.setPromptText("Pseudo");
        pseudo.setPrefWidth(200);
        pseudo.setPrefHeight(30);
        pseudo.setTranslateX(loginPane.getTranslateX() + loginPane.getMinWidth() / 2 - pseudo.getPrefWidth() / 2);
        pseudo.setTranslateY((height / 7.0) * 4);
        pseudo.getStyleClass().add("buttons");

        // Setup the login button
        login.setId("login");
        login.setPrefWidth(200);
        login.setPrefHeight(30);
        login.setTranslateX(loginPane.getTranslateX() + loginPane.getMinWidth() / 2 - login.getPrefWidth() / 2);
        login.setTranslateY(((height / 7.0) * 4) + login.getPrefHeight() + 10);
        login.getStyleClass().add("buttons");
        login.setOnAction((event) -> {
            MainGui.getInstance().getLoginManager().loginCracked(pseudo.getText());
        });


        // Setup the global window properties
        this.getStylesheets().add("styles/LoginPane.css");
        this.setId("root");
    }

    private void setCracked(boolean cracked) {
        pseudo.setDisable(!cracked);
        login.setDisable(!cracked);
        login_ms.setDisable(cracked);
    }
}
