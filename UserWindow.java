package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserWindow {

    // open new window
    public static void userWindow() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);     // prevent action before this window is closed
        window.setTitle("User Window");
//        window.setMinWidth(400);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Follow User
        TextField userID = new TextField("Type Here");
        gridPane.add(userID,0,0);

        Button followUser = new Button("Follow User");
        followUser.setMinWidth(50);
        gridPane.add(followUser,1,0);

        TextArea currentFollowing = new TextArea("Current Following:");
        currentFollowing.setEditable(false);
        gridPane.add(currentFollowing,0,1);

        followUser.setOnAction(e -> {
            String name = userID.getText();
            currentFollowing.appendText("\n" + name);
        });

        // Post Tweet
        TextField tweetMsg = new TextField("Type Here");
        gridPane.add(tweetMsg,0,2);

        Button postTweet = new Button("Post Tweet");
        gridPane.add(postTweet,1,2);

        TextArea newsFeed = new TextArea("News Feed:");
        newsFeed.setEditable(false);
        gridPane.add(newsFeed,0,3);

        postTweet.setOnAction(e -> {
            String news = tweetMsg.getText();
            newsFeed.appendText("\n" + news);
        });

        Scene scene = new Scene(gridPane,700,400);
        window.setScene(scene);
        window.showAndWait();   // prevent action before this window is closed
    }
}
