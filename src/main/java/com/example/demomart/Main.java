package com.example.demomart;

import com.example.demomart.utils.Scanner;
import com.example.demomart.utils.VirtualJournalServer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    private Scanner scanner = new Scanner();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("POS_GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        stage.setTitle("DemoMart");
        stage.setScene(scene);
        stage.isFocused();
        stage.isAlwaysOnTop();
        stage.show();

        scene.setOnKeyTyped(e -> scanner.handleKeyEvent(e));
    }

    public static void main(String[] args) throws IOException {
        VirtualJournalServer.virtualServer();
        launch();
    }
}