package org.example.cardgame24;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.cardgame24.util.ImageContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(new Image(String.valueOf(HelloApplication.class.getResource("ace_of_spades.png"))).getUrl());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}