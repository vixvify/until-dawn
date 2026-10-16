package com.untildawn.util;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class SurveillanceEffect implements AutoCloseable {
    private final Canvas canvas;
    private final Random random = new Random();
    private final Timeline timeline;
    private int frame;

    public SurveillanceEffect(Canvas canvas) {
        this.canvas = canvas;
        timeline = new Timeline(new KeyFrame(Duration.millis(120), event -> draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setEnabled(boolean enabled) {
        canvas.setVisible(enabled);
        if (enabled) {
            draw();
            timeline.play();
        } else {
            timeline.stop();
        }
    }

    private void draw() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        graphics.clearRect(0, 0, width, height);
        graphics.setFill(Color.rgb(0, 0, 0, 0.18));
        for (int y = 0; y < height; y += 4) {
            graphics.fillRect(0, y, width, 1);
        }
        graphics.setFill(Color.rgb(224, 222, 205, 0.07));
        for (int i = 0; i < 950; i++) {
            graphics.fillRect(random.nextDouble() * width, random.nextDouble() * height,
                    1 + random.nextInt(3), 1);
        }
        graphics.setFill(Color.rgb(220, 225, 205, 0.025));
        graphics.fillRect(0, (frame++ * 3) % (height + 60) - 60, width, 60);
    }

    @Override
    public void close() {
        timeline.stop();
    }
}
