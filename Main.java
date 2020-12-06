package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        AdminWindow adminWindow = AdminWindow.getInstance();    // main UI, entrance
        SplitPane splitPane = adminWindow.getAdminWindow();

        Scene scene = new Scene(splitPane,550,400);
        primaryStage.setTitle("Mini Twitter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
