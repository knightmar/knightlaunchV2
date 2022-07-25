package fr.knightmar;

import fr.flowarg.flowlogger.Logger;
import fr.knightmar.panes.LoginPane;
import fr.knightmar.panes.MainPane;
import fr.knightmar.system.LoginManager;
import fr.knightmar.system.VersionManager;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.nio.file.Path;

public class MainGui extends Application {
    private static MainGui instance;
    private final LoginManager loginManager;
    private final Path LAUNCHER_DIR = GameDirGenerator.createGameDir("knightlauncherV2", true);
    private Saver saver;


    private boolean isLogged;


    private Logger logger;
    private Scene currentScene;


    private Stage primaryStage;


    public MainGui() {
        instance = this;
        this.logger = new Logger("[knightLauncher]", this.LAUNCHER_DIR.resolve("launcher.log"));
        if (!this.LAUNCHER_DIR.toFile().exists()) {
            if (!this.LAUNCHER_DIR.toFile().mkdir()) {
                this.logger.err("Unable to create launcher folder");
            }
        }
        saver = new Saver(this.LAUNCHER_DIR.resolve("config.properties"));
        saver.load();

        loginManager = new LoginManager();
        loginManager.init();


        if (loginManager.isLogged()) {
            this.logger.info("User is logged");
            isLogged = true;
        } else {
            this.logger.info("User is not logged");
            isLogged = false;
        }
    }

    public Path getlauncherDir() {
        return LAUNCHER_DIR;
    }

    @Override
    public void start(Stage stage) {
        VersionManager.init();
        this.primaryStage = stage;
        int width = 1280, height = 720;
        if (!isLogged) {
            currentScene = new Scene(new LoginPane(width, height), width, height);
        } else {
            currentScene = new Scene(new MainPane(width, height), width, height);

        }
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle("First JavaFX Application");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

    public void setScene(Scene scene) {
        currentScene = scene;
    }

    public static MainGui getInstance() {
        return instance;
    }

    public Saver getSaver() {
        return saver;
    }

    public Logger getLogger() {
        return logger;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
