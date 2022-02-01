package com.test.jfxdb;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Scene scene;
    @Override
    public void start(Stage stage){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
            ScrollPane scrollPane = fxmlLoader.load();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            scene = new Scene(scrollPane);
            stage.setTitle("DB Application");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getScene(){
        return  scene;
    }

    public static void main(String[] args) {
        launch();
    }
}