module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
}