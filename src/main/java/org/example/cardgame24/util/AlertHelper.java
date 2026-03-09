package org.example.cardgame24.util;

import javafx.scene.control.Alert;
import org.example.cardgame24.GameManager;
import org.example.cardgame24.model.AlertTypes;

/**
 * Helper class for creating alerts. Mainly made because making alerts is annoying
 * @author Charles Gonzalez Jr
 */
public class AlertHelper {

    public static Alert getAlert(AlertTypes type, GameManager manager) {
       return  switch ( type) {
            case AlertTypes.CORRECT -> alertCreator("You got it right! answer: " + manager.getCurrentAns(),Alert.AlertType.INFORMATION);
           case AlertTypes.INVALID -> alertCreator("Invalid equation! answer: " + manager.getCurrentAns(),Alert.AlertType.INFORMATION);
           case AlertTypes.WRONG_ANSWER -> alertCreator("Your answer is wrong! answer: " + manager.getCurrentAns(),Alert.AlertType.INFORMATION);
           case AlertTypes.WRONG_NUMBER ->  alertCreator("Almost there! Just need to use the right numbers. answer: " + manager.getCurrentAns(),Alert.AlertType.INFORMATION);
           case ERROR -> alertCreator("Error loading files",Alert.AlertType.ERROR);
       };

    }

    private static Alert alertCreator(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        return alert;
    }
}
