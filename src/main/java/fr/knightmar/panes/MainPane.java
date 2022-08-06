package fr.knightmar.panes;

import fr.knightmar.MainGui;
import fr.knightmar.system.GameManager;
import fr.knightmar.system.VersionManager;
import fr.knightmar.utils.AccountUtils;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class MainPane extends Pane {
    private ComboBox versionComboBox = new ComboBox(FXCollections.observableArrayList(VersionManager.getMinecraftVersions(false)));
    private Button playButton = new Button("Play");
    private final double width;
    private final double height;
    private Button logoutButton;
    private Button settingsButton = new Button("Settings");

    private Label playerHead = new Label();

    private Label crackMessage = new Label();

    private Button reloadButton = new Button("Reload");

    private Button exit_button = new Button("");

    private Button hide_button = new Button("");

    private ProgressBar progressBar = new ProgressBar();
    private Label percentLabel = new Label();

    private Label infoLabel = new Label();


    public MainPane(double width, double height) {
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

        this.getChildren().addAll(logoutButton, versionComboBox, playButton, playerHead, settingsButton, crackMessage, reloadButton, exit_button, hide_button, progressBar, percentLabel, infoLabel);
    }

    private void setupUiActions() {
        logoutButton.setOnAction((event) -> {
            MainGui.getInstance().getLoginManager().logout();
        });

        playButton.setOnAction((event) -> {
            Thread thread = new Thread(() -> GameManager.initLaunch(versionComboBox.getValue().toString()));
            thread.start();
        });

        reloadButton.setOnAction((event) -> {
            MainGui.getInstance().getPrimaryStage().setScene(new Scene(new MainPane(1280, 720), 1280, 720));
        });
        exit_button.setOnAction((event) -> {
            System.exit(0);
        });
        hide_button.setOnAction((event) -> {
            MainGui.getInstance().getPrimaryStage().setIconified(true);
        });
    }

    private void setupUiProperties() {

        exit_button.setPrefSize(18, 18);
        exit_button.setLayoutX(width - 55);
        exit_button.setLayoutY(7);
        exit_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        exit_button.setGraphic(new ImageView(new Image("/images/exit_button.png")));
        exit_button.setBorder(null);
        exit_button.setBackground(null);

        hide_button.setPrefSize(18, 18);
        hide_button.setLayoutX(width - 137);
        hide_button.setLayoutY(7);
        hide_button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        hide_button.setGraphic(new ImageView(new Image("/images/hide_button.png")));
        hide_button.setBorder(null);
        hide_button.setBackground(null);


        Font font = Font.loadFont(getClass().getResourceAsStream("/font/mc_font.otf"), 20);

        this.setId("main_pane");
        this.getStylesheets().add("styles/MainPane.css");

        ImageView crackBanner = new ImageView(new Image("images/crack_message.png"));
        crackMessage.setText("Warning !!! You're in crack mode, you can't play on online servers.");
        crackMessage.setId("crack_message");
        crackMessage.setTextAlignment(TextAlignment.CENTER);
        crackMessage.setContentDisplay(ContentDisplay.CENTER);
        crackMessage.setWrapText(false);
        crackMessage.setTranslateX(285);
        crackMessage.setVisible(!MainGui.getInstance().getLoginManager().getCracked());
        crackMessage.setPrefSize(841, 92);
        crackMessage.setGraphic(crackBanner);
        crackMessage.setFont(font);
        crackMessage.setTranslateY(height - crackMessage.getPrefHeight() - 10);


        settingsButton.setId("settings_button");
        settingsButton.setPrefSize(135, 46);
        settingsButton.setTranslateX(0);
        settingsButton.setTranslateY(height - 109);
        settingsButton.setBorder(null);
        settingsButton.setFont(font);

        logoutButton = new Button("Logout");
        logoutButton.setId("logout_button");
        logoutButton.setFont(font);
        logoutButton.setTextAlignment(TextAlignment.CENTER);
        logoutButton.setContentDisplay(ContentDisplay.TOP);
        logoutButton.setPrefSize(135, 46);
        logoutButton.setTranslateX(0);
        logoutButton.setBorder(null);
        logoutButton.setTranslateY(settingsButton.getTranslateY() + logoutButton.getPrefHeight());


        versionComboBox.setId("version_combo_box");
        versionComboBox.setPrefSize(170, 34);
        versionComboBox.setTranslateX(743);
        versionComboBox.setTranslateY(206);
        versionComboBox.getSelectionModel().selectFirst();

        Font playFont = Font.loadFont(getClass().getResourceAsStream("/font/mc_font_bold.otf"), 45);


        playButton.setId("play_button");
        playButton.setPrefSize(232, 92);
        playButton.setTranslateX(512);
        playButton.setTranslateY(160);
        playButton.setFont(playFont);
        playButton.setBorder(null);


        playerHead.setId("player_head");
        playerHead.setMaxSize(113, 113);
        playerHead.setTranslateX(12);
        playerHead.setTranslateY(9);
        Image headImage = new Image("https://minotar.net/helm/" + AccountUtils.getPseudo() + "/113.png");
        ImageView headImageView = new ImageView(headImage);
        headImageView.setFitHeight(113);
        headImageView.setPreserveRatio(true);
        playerHead.setGraphic(headImageView);


        progressBar.setId("progress_bar");
        progressBar.setPrefSize(width - 200, 30);
        progressBar.setTranslateX(180);
        progressBar.setTranslateY(height - progressBar.getPrefHeight() - 100);
        progressBar.setVisible(true);
        progressBar.setProgress(0);

        percentLabel.setId("progress_label");
        percentLabel.setPrefSize(120, 30);
        percentLabel.setTranslateX(progressBar.getTranslateX() + 10);
        percentLabel.setTranslateY(height - progressBar.getPrefHeight() - 100);
        percentLabel.setVisible(true);
        percentLabel.setText("banana");
        percentLabel.setFont(font);
        percentLabel.setTextAlignment(TextAlignment.CENTER);
        percentLabel.setContentDisplay(ContentDisplay.CENTER);
        percentLabel.setWrapText(false);

        infoLabel.setId("info_label");
        infoLabel.setPrefSize(300, 30);
        infoLabel.setTranslateX(progressBar.getTranslateX() + progressBar.getPrefWidth() / 2.0 - infoLabel.getPrefWidth() / 2.0);
        infoLabel.setTranslateY(height - progressBar.getPrefHeight() - 130);
        infoLabel.setVisible(true);
        infoLabel.setText("truc");
        infoLabel.setFont(font);
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setContentDisplay(ContentDisplay.CENTER);
        infoLabel.setWrapText(false);


    }

    public void setProgressBar(double progression) {
        int progress = (int) (progression * 100);
        System.out.println(progress);
        progressBar.setProgress(progression);
        percentLabel.setText(progress + "%");
        percentLabel.setTranslateX(progressBar.getTranslateX() + 10 + progression / 2 * (progressBar.getPrefWidth() - 100));
    }

    public void setInfoLabel(String text) {
        infoLabel.setText(text);
    }
}
