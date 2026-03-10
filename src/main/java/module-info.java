module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires javafx.graphics;

    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
    exports org.example.cardgame24.controller;
    opens org.example.cardgame24.controller to javafx.fxml;
    exports org.example.cardgame24.util;
    opens org.example.cardgame24.util to javafx.fxml;
}