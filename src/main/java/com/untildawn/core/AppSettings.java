package com.untildawn.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class AppSettings {
    private final BooleanProperty fullScreen = new SimpleBooleanProperty(false);
    private final BooleanProperty visualEffects = new SimpleBooleanProperty(true);

    public BooleanProperty fullScreenProperty() {
        return fullScreen;
    }

    public BooleanProperty visualEffectsProperty() {
        return visualEffects;
    }
}
