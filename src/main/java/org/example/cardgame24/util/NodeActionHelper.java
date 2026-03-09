package org.example.cardgame24.util;

import javafx.scene.Node;
import javafx.scene.control.Control;

public class NodeActionHelper {

    public static void changeBackgroundToRed(Control node){
        node.getStyleClass().clear();
        node.getStyleClass().add("invalid");
    }

    public static void changeBackgroundToGreen(Control node){
        node.getStyleClass().clear();
        node.getStyleClass().add("correct");
    }
}
