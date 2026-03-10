module org.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires java.desktop;
    requires langchain4j.google.ai.gemini;


    requires java.net.http;


    requires java.scripting;
    requires org.graalvm.js.scriptengine;



    opens org.example.cardgame24 to javafx.fxml;
    exports org.example.cardgame24;
}