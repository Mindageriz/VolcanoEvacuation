module com.example.volcanoevacuation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.volcanoevacuation to javafx.fxml;
    exports com.example.volcanoevacuation;
}