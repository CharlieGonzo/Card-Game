module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;
    requires langchain4j.google.ai.gemini;

    // ADD THIS LINE HERE:
    requires java.net.http;



    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
}