package fr.knightmar.panes;

import fr.knightmar.MainGui;
import fr.knightmar.system.GameManager;
import fr.knightmar.system.VersionManager;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;


public class MainPane extends Pane {
    private ComboBox versionComboBox = new ComboBox(FXCollections.observableArrayList(VersionManager.getVersions()));
    private Button playButton = new Button("Play");
    private final double width;
    private final double height;
    private Button logoutButton;

    public MainPane(double width, double height) {
        this.width = width;
        this.height = height;
        setupUiProperties();
        setupUiActions();

        this.getChildren().addAll(logoutButton, versionComboBox, playButton);
    }

    private void setupUiActions() {
        logoutButton.setOnAction((event) -> {
            System.out.println("Logout");
            MainGui.getInstance().getLoginManager().logout();
        });

        playButton.setOnAction((event) -> {
            System.out.println("Play");
            GameManager.initLaunch(versionComboBox.getValue().toString());
        });
    }

    private void setupUiProperties() {
        this.setId("main_pane");
        this.getStylesheets().add("styles/MainPane.css");
        logoutButton = new Button("Logout");
        logoutButton.setId("logout_button");
        logoutButton.setTranslateX(width / 2.0 - logoutButton.getMinWidth() / 2);
        logoutButton.setTranslateY(30);

        versionComboBox.setId("version_combo_box");
        versionComboBox.setTranslateX(width / 2.0 - versionComboBox.getMinWidth() / 2);
        versionComboBox.setTranslateY(60);

        playButton.setId("play_button");
        playButton.setTranslateX(width / 2.0 - playButton.getMinWidth() / 2);
        playButton.setTranslateY(100);
    }
}
