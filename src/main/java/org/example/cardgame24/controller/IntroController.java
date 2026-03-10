package org.example.cardgame24.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.cardgame24.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

    @FXML
    private Button continueButton;

    @FXML
    private Label ruleTezt;

    @FXML
    void nextPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stage = (Stage) ruleTezt.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ruleTezt.setText("""
                
                Game Rules & Requirements
                
                
                
                1. Card Values
                
                The cards are represented by:
                Numbers 2-10 as their face value.
                Ace as 1.
                Jack as 11.
                Queen as 12.
                King as 13.
         
                2. Gameplay
                
                The application randomly selects four playing cards and displays them.
                The player must enter an arithmetic expression that:
                Uses all four numbers exactly once.
                Evaluates to 24.
                Can include addition (+), subtraction (-), multiplication (*), and division (/).
                Can use parentheses for grouping.
                When your expression box is red, that means your equation is invalid
                When your expression is green, that means you equation is a valid equation, but that does not mean you are correct
                The hint button gives you the answer equation one symbol at a time
                The solution is always solvable
                """);
        ruleTezt.setStyle("-fx-background-color: black; -fx-text-fill: white;");
    }
}
