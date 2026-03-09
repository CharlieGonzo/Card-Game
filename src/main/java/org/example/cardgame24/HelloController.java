package org.example.cardgame24;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.cardgame24.model.AlertTypes;
import org.example.cardgame24.util.AlertHelper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Charles Gonzalez Jr
 * Main controller class of the game. Handles the UI elements.
 */
public class HelloController implements Initializable {

    private GameManager manager; // handles game state and porperties

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

    ImageView[] images;


    /**
     * Verify if equation passes all test. if it does, it will alert the user that they are right
     * otherwise, it will alert them on what they did wrong.
     * @param event
     */
    @FXML
    void verifyEquation(ActionEvent event) {
       if(manager.verify(playerSolution.getText())){
           AlertHelper.getAlert(AlertTypes.CORRECT,manager).showAndWait();
       }else{
           if(manager.getCurrentAns() == Integer.MIN_VALUE){
               AlertHelper.getAlert(AlertTypes.INVALID,manager).showAndWait();
               return; // cut method early
           }
           if(manager.getCurrentAns() == 24.0){
               AlertHelper.getAlert(AlertTypes.WRONG_NUMBER,manager).showAndWait();
           }else{
               AlertHelper.getAlert(AlertTypes.WRONG_ANSWER,manager).showAndWait();
           }
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
        Image[] newImages = manager.generateNewCards();
        int count = 0;
        for(ImageView image : images){
            image.setImage(newImages[count++]);
        }
    }

    /**
     * Setup before game loads.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try { // create game manager
            manager = new GameManager();
        } catch (IOException e) {
            AlertHelper.getAlert(AlertTypes.ERROR,manager).showAndWait();
            throw new RuntimeException(e);
        }
        images = new ImageView[]{img1,img2,img3,img4};
        resetImages();
    }
}
