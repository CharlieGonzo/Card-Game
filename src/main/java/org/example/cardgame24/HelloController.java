package org.example.cardgame24;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.example.cardgame24.model.AlertTypes;
import org.example.cardgame24.util.AlertHelper;
import org.example.cardgame24.util.NodeActionHelper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Charles Gonzalez Jr
 * Main controller class of the game. Handles the UI elements.
 */
public class HelloController implements Initializable {

    private GameManager manager; // handles game state and porperties

    @FXML
    private Label answerLabel;

    @FXML
    private Button hintButton;

    @FXML
    private TextField playerSolution;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private Label isCalculatingMessage;

    @FXML
    private ImageView img4;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField solutionField;

    @FXML
    private Button verifyButton;

    int currentHintIndex = 1;

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
           return;
       }
       if(manager.getCurrentAns() == 24.0){
           AlertHelper.getAlert(AlertTypes.WRONG_NUMBER,manager).showAndWait();
       }else{
           AlertHelper.getAlert(AlertTypes.WRONG_ANSWER,manager).showAndWait();
       }

    }

    /**
     * resets current playing cards
     * @param event
     */
    @FXML
    void refresh(ActionEvent event) {
        Image[] newImages = manager.generateNewCards();
        currentHintIndex = 0;
        solutionField.clear();
        int count = 0;
        for(ImageView image : images){
            image.setImage(newImages[count++]);
        }
    }


    @FXML
    void findSolution(ActionEvent event) {
        solutionField.setText(manager.getCurrentEquation().substring(0,currentHintIndex++));
    }

    @FXML
    void checkInput(KeyEvent event) {
        if (manager.validateEquation(playerSolution.getText())) {
            NodeActionHelper.changeBackgroundToGreen(playerSolution);
            manager.setIsValid(true);
            answerLabel.setText(String.format("Answer: %.2f",manager.getCurrentAns()));
        } else {
            NodeActionHelper.changeBackgroundToRed(playerSolution);
            answerLabel.setText("Answer:");
            manager.setIsValid(false);
        }
    }

    /**
     * Setup before game loads.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // create a game manager
        if(!testOnlineConnection()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You need an internet connection to play this game.");
            System.exit(0);
        }
        manager = new GameManager();
        images = new ImageView[]{img1,img2,img3,img4};
        refresh(null);
        startLoop().start();
    }

    private boolean testOnlineConnection(){
        try {
            boolean isOnline = InetAddress.getByName("8.8.8.8").isReachable(2000);
        } catch (IOException e) {
            isCalculatingMessage.setText("Need internet connection for solution...");
            return false;
        }
        return true;
    }

    private AnimationTimer startLoop(){
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(manager.getCurrentEquation() != null){
                    isCalculatingMessage.setVisible(false);
                    hintButton.setDisable(false);
                }else{
                    isCalculatingMessage.setVisible(true);
                    hintButton.setDisable(true);
                }
                if(manager.getIsValid()){
                    verifyButton.setDisable(false);
                }else{
                    verifyButton.setDisable(true);
                }

            }
        };
    }
}
