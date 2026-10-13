package com.untildawn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("UNTIL DAWN");
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        StackPane root = new StackPane(title);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Until Dawn");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
