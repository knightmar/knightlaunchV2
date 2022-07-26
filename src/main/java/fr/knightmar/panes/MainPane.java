package fr.knightmar.panes;

import fr.knightmar.MainGui;
import fr.knightmar.system.GameManager;
import fr.knightmar.system.VersionManager;
import fr.knightmar.utils.AccountUtils;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
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
    private Button settingsButton;

    private Label playerHead = new Label();

    private Label crackMessage = new Label();

    private Button reloadButton = new Button("Reload");

    private Button exit_button = new Button("");

    private Button hide_button = new Button("");


    public MainPane(double width, double height) {
        this.width = width;
        this.height = height;
        setupUiProperties();
        setupUiActions();

        this.getChildren().addAll(logoutButton, versionComboBox, playButton, playerHead, settingsButton, crackMessage, reloadButton, exit_button, hide_button);
    }

    private void setupUiActions() {
        logoutButton.setOnAction((event) -> {
            MainGui.getInstance().getLoginManager().logout();
        });

        playButton.setOnAction((event) -> {
            GameManager.initLaunch(versionComboBox.getValue().toString());
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
        crackMessage.setText("Warning !!! Your in crack mode, you can't play on online servers.");
        crackMessage.setId("crack_message");
        crackMessage.setTextAlignment(TextAlignment.CENTER);
        crackMessage.setContentDisplay(ContentDisplay.CENTER);
        crackMessage.setWrapText(false);
        crackMessage.setTranslateX(285);
        crackMessage.setTranslateY(height - crackMessage.getHeight() - 50);
        crackMessage.setVisible(!MainGui.getInstance().getLoginManager().getCracked());
        crackMessage.setPrefSize(841, 92);
        crackMessage.setGraphic(crackBanner);
        crackMessage.setFont(font);

        logoutButton = new Button("Logout");
        logoutButton.setId("logout_button");
        logoutButton.setPrefSize(100, 10);
        logoutButton.setTranslateX(18);
        logoutButton.setTranslateY(height - 41);
        logoutButton.setFont(font);
        logoutButton.setTextAlignment(TextAlignment.CENTER);
        logoutButton.setContentDisplay(ContentDisplay.TOP);


        versionComboBox.setId("version_combo_box");
        versionComboBox.setTranslateX(width / 2.0 - versionComboBox.getMinWidth() / 2);
        versionComboBox.setTranslateY(60);

        playButton.setId("play_button");
        playButton.setPrefSize(232,92);
        playButton.setTranslateX(512);
        playButton.setTranslateY(160);
        playButton.setFont(font);
        hide_button.setBorder(null);

        playerHead.setId("player_head");
        playerHead.setMaxSize(113, 113);
        playerHead.setTranslateX(12);
        playerHead.setTranslateY(9);
        Image headImage = new Image("https://minotar.net/helm/" + AccountUtils.getPseudo() + "/113.png");
        ImageView headImageView = new ImageView(headImage);
        headImageView.setFitHeight(113);
        headImageView.setPreserveRatio(true);
        playerHead.setGraphic(headImageView);

        settingsButton = new Button("");
        settingsButton.setId("settings_button");
        settingsButton.setPrefSize(135, 46);
        settingsButton.setTranslateX(0);
        settingsButton.setTranslateY(height - 109);
        settingsButton.setBorder(null);
        settingsButton.setFont(font);
    }
}
