package org.sla;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FilmsGUI extends Application {
    Controller theController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Top Films Data");
        primaryStage.setScene(new Scene(root, 865, 703));
        primaryStage.show();

        theController = loader.getController();
        theController.setMyStage(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        theController.saveData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
