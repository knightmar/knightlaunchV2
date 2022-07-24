package fr.knightmar.panes;

import fr.knightmar.MainGui;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


public class MainPane extends Pane {
    public MainPane() {
        this.setId("main_pane");
        this.getStylesheets().add("styles/MainPane.css");

        Button logoutButton = new Button("Logout");

        this.getChildren().add(logoutButton);

        logoutButton.setOnAction((event) -> {
            System.out.println("Logout");
            MainGui.getInstance().getLoginManager().logout();
        });

    }
}
