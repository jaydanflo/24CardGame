module com.example.cardgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;


    opens com.example.cardgame to javafx.fxml;
    exports com.example.cardgame;
}