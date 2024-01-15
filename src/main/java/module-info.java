module com.example.demomart {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.example.demomart to javafx.fxml;
    exports com.example.demomart;
    exports com.example.demomart.utils;
    opens com.example.demomart.utils to javafx.fxml;
    exports com.example.demomart.models;
    opens com.example.demomart.models to javafx.fxml;
    exports com.example.demomart.controllers;
    opens com.example.demomart.controllers to javafx.fxml;
    exports com.example.demomart.dataCollections;
    opens com.example.demomart.dataCollections to javafx.fxml;
}