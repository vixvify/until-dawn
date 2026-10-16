package com.untildawn;

import javafx.application.Application;
import javafx.stage.Stage;
import com.untildawn.core.SceneManager;

public class Main extends Application {
    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        sceneManager.showLobby();
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (sceneManager != null) {
            sceneManager.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
