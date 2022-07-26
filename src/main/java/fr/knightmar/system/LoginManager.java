package fr.knightmar.system;

import fr.knightmar.MainGui;
import fr.knightmar.panes.LoginPane;
import fr.knightmar.panes.MainPane;
import fr.knightmar.utils.AccountUtils;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;


public class LoginManager {
    private final MainGui instance;
    private final Saver saver;

    private String pseudo = "";
    private String uuid = "";
    private String refreshToken = "";
    private String accessToken = "";
    private Boolean isCracked = null;

    public LoginManager() {
        this.instance = MainGui.getInstance();
        this.saver = this.instance.getSaver();
    }

    public void init() {
        instance.getLogger().info("LoginManager init");

        this.pseudo = saver.get("pseudo");
        this.uuid = saver.get("uuid");

        setCracked(this.uuid == null || this.uuid.isEmpty());
    }

    public boolean isLogged() {
        return this.pseudo != null && !this.pseudo.isEmpty();
    }

    public void LoginWithMs(ActionEvent event) {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        authenticator.loginWithAsyncWebview().whenComplete((response, error) -> {
            if (error != null) {
                MainGui.getInstance().getLogger().err(error.toString());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(error.getMessage());
                alert.show();
                return;
            }

            saver.set("msAccessToken", response.getAccessToken());
            saver.set("msRefreshToken", response.getRefreshToken());
            saver.set("pseudo", response.getProfile().getName());
            saver.set("uuid", response.getProfile().getId());

            saver.save();

            MainGui.getInstance().getLogger().info("Hello " + response.getProfile().getName());

            Platform.runLater(() -> {
                instance.getLogger().info("User " + pseudo + " is logged");
                instance.setLogged(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Logged in successfully");
                alert.setHeaderText("You are now logged in");
                alert.setContentText("Welcome " + this.pseudo);
                alert.show();
                MainGui.getInstance().getPrimaryStage().setScene(new Scene(new MainPane(1280, 720), 1280, 720));
                setCracked(false);
            });
        });


//        Thread t = new Thread(() -> {
//
//            try {
//                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
//                MicrosoftAuthResult result = authenticator.loginWithWebview();
//                this.pseudo = result.getProfile().getName();
//                this.uuid = result.getProfile().getId();
//                this.refreshToken = result.getRefreshToken();
//                this.accessToken = result.getAccessToken();
//                saver.set("pseudo", this.pseudo);
//                saver.set("uuid", this.uuid);
//                saver.set("refreshToken", this.refreshToken);
//                saver.set("accessToken", this.accessToken);
//                saver.save();
//
//            } catch (MicrosoftAuthenticationException e) {
//                Platform.runLater(() -> {
//                    instance.getLogger().info("Erorr while logging with Microsoft" + e.getMessage());
//                    instance.setLogged(false);
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error on login");
//                    alert.setHeaderText("An error occured while logging with Microsoft");
//                    alert.setContentText("We are sorry, but an error occured while logging with Microsoft.\n" +
//                            "Please try again later or contact the support team.");
//                    alert.show();
//
//                });
//                throw new RuntimeException(e);
//            }
//
//            Platform.runLater(() -> {
//                instance.getLogger().info("User " + pseudo + " is logged");
//                instance.setLogged(true);
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Logged in successfully");
//                alert.setHeaderText("You are now logged in");
//                alert.setContentText("Welcome " + this.pseudo);
//                alert.show();
//                MainGui.getInstance().getPrimaryStage().setScene(new Scene(new MainPane(1280, 720), 1280, 720));
//                setCracked(false);
//            });
//        });
//        t.start();
    }


    public void loginCracked(String pseudo) {
        if (AccountUtils.checkValidPseudo(pseudo)) {
            this.pseudo = pseudo;
            this.uuid = "";
            this.refreshToken = "";
            saver.set("pseudo", this.pseudo);
            saver.set("uuid", this.uuid);
            saver.set("refreshToken", this.refreshToken);
            saver.save();
            instance.getLogger().info("User is logged");
            instance.setLogged(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logged in successfully");
            alert.setHeaderText("You are now logged in");
            alert.setContentText("Welcome " + this.pseudo);
            alert.show();
            MainGui.getInstance().getPrimaryStage().setScene(new Scene(new MainPane(1280, 720), 1280, 720));
            setCracked(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error on login");
            alert.setHeaderText("Your pseudo is not valid");
            alert.setContentText("We are sorry, but your pseudo is not valid.\n" +
                    "It must be between 3 and 16 characters and must contain only letters, numbers and underscores.");
            alert.showAndWait();
        }


    }

    public void logout() {
        this.pseudo = "";
        this.uuid = "";
        this.refreshToken = "";
        saver.set("pseudo", "");
        saver.set("uuid", "");
        saver.set("refreshToken", "");
        saver.save();
        instance.setLogged(false);
        MainGui.getInstance().getPrimaryStage().setScene(new Scene(new LoginPane(1280, 720), 1280, 720));
        isCracked = null;
    }

    public boolean getCracked() {
        uuid = saver.get("uuid");
        return uuid != null && !uuid.isEmpty();
    }

    public void setCracked(boolean cracked) {
        isCracked = cracked;
    }
}
