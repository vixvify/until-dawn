package com.untildawn.util;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

public final class Viewport {
    private Viewport() {
    }

    public static void fit(Region viewport, Node content) {
        var ratio = Bindings.min(viewport.widthProperty().divide(1280),
                viewport.heightProperty().divide(720));
        Scale scale = new Scale();
        scale.xProperty().bind(ratio);
        scale.yProperty().bind(ratio);
        content.getTransforms().add(scale);
    }
}
