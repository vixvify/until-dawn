package com.untildawn.controller;

import java.util.List;

import com.untildawn.core.AppSettings;
import com.untildawn.util.SurveillanceEffect;
import com.untildawn.util.Viewport;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public final class LobbyController implements AutoCloseable {
    @FXML
    private StackPane viewport;
    @FXML
    private AnchorPane design;
    @FXML
    private AnchorPane lobbyContent;
    @FXML
    private Canvas staticCanvas;
    @FXML
    private Button newGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private StackPane settingsLayer;
    @FXML
    private CheckBox fullScreenToggle;
    @FXML
    private CheckBox effectsToggle;
    @FXML
    private Button doneButton;

    private Runnable startNewGame;
    private AppSettings settings;
    private SurveillanceEffect surveillance;
    private ChangeListener<Boolean> effectsListener;

    @FXML
    private void initialize() {
        Viewport.fit(viewport, design);
        surveillance = new SurveillanceEffect(staticCanvas);
        viewport.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKey);
    }

    public void configure(Runnable startNewGame, AppSettings settings) {
        this.startNewGame = startNewGame;
        this.settings = settings;
        fullScreenToggle.selectedProperty().bindBidirectional(settings.fullScreenProperty());
        effectsToggle.selectedProperty().bindBidirectional(settings.visualEffectsProperty());
        effectsListener = (observable, previous, enabled) -> surveillance.setEnabled(enabled);
        settings.visualEffectsProperty().addListener(effectsListener);
        surveillance.setEnabled(settings.visualEffectsProperty().get());
        Platform.runLater(newGameButton::requestFocus);
    }

    @FXML
    private void handleNewGame() {
        startNewGame.run();
    }

    @FXML
    private void openSettings() {
        lobbyContent.setDisable(true);
        settingsLayer.setVisible(true);
        fullScreenToggle.requestFocus();
    }

    @FXML
    private void closeSettings() {
        settingsLayer.setVisible(false);
        lobbyContent.setDisable(false);
        settingsButton.requestFocus();
    }

    private void handleKey(KeyEvent event) {
        KeyCode key = event.getCode();
        if (settingsLayer.isVisible()) {
            if (key == KeyCode.ESCAPE) {
                closeSettings();
                event.consume();
            } else if (key == KeyCode.TAB) {
                List<Node> controls = List.of(fullScreenToggle, effectsToggle, doneButton);
                int current = controls.indexOf(viewport.getScene().getFocusOwner());
                int next = Math.floorMod(current + (event.isShiftDown() ? -1 : 1), controls.size());
                controls.get(next).requestFocus();
                event.consume();
            } else if (key == KeyCode.ENTER && doneButton.isFocused()) {
                closeSettings();
                event.consume();
            }
            return;
        }
        if (key == KeyCode.UP || key == KeyCode.DOWN) {
            (newGameButton.isFocused() ? settingsButton : newGameButton).requestFocus();
            event.consume();
        } else if (key == KeyCode.ENTER) {
            (settingsButton.isFocused() ? settingsButton : newGameButton).fire();
            event.consume();
        } else if (key == KeyCode.ESCAPE && settings.fullScreenProperty().get()) {
            settings.fullScreenProperty().set(false);
            event.consume();
        }
    }

    @Override
    public void close() {
        surveillance.close();
        if (settings != null) {
            settings.visualEffectsProperty().removeListener(effectsListener);
            fullScreenToggle.selectedProperty().unbindBidirectional(settings.fullScreenProperty());
            effectsToggle.selectedProperty().unbindBidirectional(settings.visualEffectsProperty());
        }
    }
}
