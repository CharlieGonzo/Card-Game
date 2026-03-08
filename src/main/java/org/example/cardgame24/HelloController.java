package org.example.cardgame24;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Charles Gonzalez Jr
 * Main controller class of the game. Handles the UI elements.
 */
public class HelloController implements Initializable {

    private GameManager manager;

    @FXML
    private Button findButton;

    @FXML
    private TextField playerSolution;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField solutionField;

    @FXML
    private Button verifyButton;


    /**
     * Verify if equation passes all test. if it does, it will alert the user that they are right
     * otherwise, it will alert them on what they did wrong.
     * @param event
     */
    @FXML
    void verifyEquation(ActionEvent event) {
       if(manager.verify(playerSolution.getText())){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("You got it right! answer: " + manager.getCurrentAns());
           alert.showAndWait();
       }else{
           if(manager.getCurrentAns() == Integer.MIN_VALUE){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Your equation was invalid");
               alert.showAndWait();
               return;
           }
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           if(manager.getCurrentAns() == 24.0){
               alert.setContentText("You were so close, just have to use the right numbers! answer: "+manager.getCurrentAns());
           }else{
               alert.setContentText("Answer was wrong, try again. Answer: " + manager.getCurrentAns());
           }
           alert.showAndWait();
       }
    }

    /**
     * resets current playing cards
     * @param event
     */
    @FXML
    void refresh(ActionEvent event) {
        resetImages();
    }

    /**
     * sets the imageViews to new cards generated {@link GameManager}
     */
    private void resetImages(){
        manager.generateNewCards();
        img1.setImage(manager.getCurrentCards()[0].img());
        img2.setImage(manager.getCurrentCards()[1].img());
        img3.setImage(manager.getCurrentCards()[2].img());
        img4.setImage(manager.getCurrentCards()[3].img());
    }

    /**
     * Setup before game loads.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            manager = new GameManager();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error loading files. Closing...");
            alert.showAndWait();
            System.exit(0);
            throw new RuntimeException(e);
        }

        resetImages();
    }
}
