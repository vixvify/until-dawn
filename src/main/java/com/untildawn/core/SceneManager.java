package com.untildawn.core;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import com.untildawn.controller.LobbyController;
import com.untildawn.controller.NightController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class SceneManager implements AutoCloseable {
    private final Scene scene;
    private final AppSettings settings = new AppSettings();
    private Runnable releaseScreen = () -> {
    };

    public SceneManager(Stage stage) {
        scene = new Scene(new StackPane(), 1280, 720, Color.BLACK);
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/css/lobby.css")).toExternalForm());
        stage.setTitle("Until Dawn");
        stage.setMinWidth(960);
        stage.setMinHeight(580);
        stage.setScene(scene);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreenExitHint("Press F11 to leave fullscreen");

        settings.fullScreenProperty().addListener((observable, previous, enabled) -> stage.setFullScreen(enabled));
        stage.fullScreenProperty()
                .addListener((observable, previous, enabled) -> settings.fullScreenProperty().set(enabled));
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.F11) {
                settings.fullScreenProperty().set(!settings.fullScreenProperty().get());
                event.consume();
            }
        });
    }

    public void showLobby() {
        FXMLLoader loader = loader("/fxml/lobby.fxml");
        Parent root = load(loader);
        LobbyController controller = loader.getController();
        replaceScreen(root, controller::close);
        controller.configure(this::startNewGame, settings);
    }

    public void startNewGame() {
        FXMLLoader loader = loader("/fxml/night.fxml");
        Parent root = load(loader);
        NightController controller = loader.getController();
        replaceScreen(root, () -> {
        });
        controller.configure(NightDefinition.SINGLE_NIGHT, this::showLobby);
    }

    private FXMLLoader loader(String resource) {
        return new FXMLLoader(Objects.requireNonNull(getClass().getResource(resource), resource));
    }

    private Parent load(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not load screen: " + loader.getLocation(), exception);
        }
    }

    private void replaceScreen(Parent root, Runnable releaseNextScreen) {
        releaseScreen.run();
        scene.setRoot(root);
        releaseScreen = releaseNextScreen;
    }

    @Override
    public void close() {
        releaseScreen.run();
        releaseScreen = () -> {
        };
    }
}
