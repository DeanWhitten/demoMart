package com.example.demomart;

import com.example.demomart.dataCollections.Cart;
import com.example.demomart.models.Product;
import com.example.demomart.utils.Scanner;
import com.example.demomart.utils.VirtualJournalServer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

public class Main extends javafx.application.Application {
    private final Scanner scanner = new Scanner();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("POS_GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("DemoMart");
        stage.setScene(scene);
        stage.isFocused();
        stage.isAlwaysOnTop();
        stage.show();

        scene.setOnKeyTyped(scanner::handleKeyEvent);
    }

    public static void main(String[] args) throws IOException {
        VirtualJournalServer.virtualServer();


        launch();
    }
}