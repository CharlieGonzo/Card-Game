module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;


    requires java.net.http;


    requires java.scripting;




    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
}