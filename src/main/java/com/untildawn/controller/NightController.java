package com.untildawn.controller;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.untildawn.core.GameSession;
import com.untildawn.core.NightDefinition;
import com.untildawn.util.Viewport;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public final class NightController {
    @FXML
    private StackPane viewport;
    @FXML
    private AnchorPane design;
    @FXML
    private Label nightLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label shiftLabel;
    @FXML
    private Button backButton;
    private Runnable backToLobby;

    @FXML
    private void initialize() {
        Viewport.fit(viewport, design);
        viewport.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                handleBack();
                event.consume();
            }
        });
    }

    public void configure(GameSession session, Runnable backToLobby) {
        this.backToLobby = backToLobby;
        NightDefinition night = session.getNight();
        DateTimeFormatter clock = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        nightLabel.setText(String.format("NIGHT %02d", night.number()));
        timeLabel.setText(night.startsAt().format(clock));
        shiftLabel.setText("ON DUTY UNTIL " + night.endsAt().format(clock)
                + "  /  " + session.getGhosts().size() + " PRESENCES");
        String presenceDescriptions = String.join("\n", session.describePresences());
        shiftLabel.setTooltip(new Tooltip(presenceDescriptions));
        shiftLabel.setAccessibleHelp(presenceDescriptions);
        Platform.runLater(backButton::requestFocus);
    }

    @FXML
    private void handleBack() {
        backToLobby.run();
    }
}
