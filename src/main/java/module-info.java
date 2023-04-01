module com.example.thechat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.thechat to javafx.fxml;
    exports com.example.thechat;
}