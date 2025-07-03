package com.nepal.tourism;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TourismApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Starting application...");
            var resource = getClass().getResource("/fxml/MainView.fxml");
            System.out.println("FXML Resource: " + resource);
            
            FXMLLoader loader = new FXMLLoader(resource);
            System.out.println("Loading FXML...");
            Scene scene = new Scene(loader.load(), 1200, 800);
            
//            var cssResource = getClass().getResource("/css/styles.css");
//            System.out.println("CSS Resource: " + cssResource);
//            scene.getStylesheets().add(Objects.requireNonNull(cssResource).toExternalForm());
            
            primaryStage.setTitle("Nepal Tourism Management System");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error starting application:");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}