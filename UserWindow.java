package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserWindow {

    private VBox vBox;  // scene

    private UserWindow(User user, Group rootGroup) {

        // Follow User
        TextField userID = new TextField();
        userID.setPromptText("Type Here");

        Button followUser = new Button("Follow User");
        followUser.setMinWidth(50);
        followUser.setOnAction(e -> {
            String name = userID.getText();
            User follower = rootGroup.returnGroupItem(name);
            checkFollowingCondition(user, follower);
            userID.clear();
        });

        // list view and observable list
        ListView currentFollowing = new ListView(user.getFollowingObservable());
        currentFollowing.setEditable(false);

        // Post Tweet
        TextField tweetMsg = new TextField();
        tweetMsg.setPromptText("Type Here");

        Button postTweet = new Button("Post Tweet");
        postTweet.setOnAction(e -> {
            String tweetMsgText = tweetMsg.getText();
            user.setNewsFeedObservable(tweetMsgText);
            tweetMsg.clear();
        });

        // list view and observable list
        ListView newsFeed = new ListView(user.getNewsFeedObservable());
        newsFeed.setEditable(false);

        // GUI set up
        HBox followHBox = new HBox(userID, followUser);
        followHBox.setPadding(new Insets(0,0,10,0));
        followHBox.setSpacing(10);
        HBox newsHBox = new HBox(tweetMsg, postTweet);
        newsHBox.setPadding(new Insets(10,0,10,0));
        newsHBox.setSpacing(10);
        vBox = new VBox(followHBox, currentFollowing, newsHBox, newsFeed);
        vBox.setPadding(new Insets(10,10,10,10));
    }

    public User checkFollowingCondition(User user, User following) {
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        if (following.equals(user)) {
            alertBox.setContentText("You cannot follow yourself");
            alertBox.showAndWait();
        }
        else if (user.getFollowingObservable().contains(following)) {
            alertBox.setContentText("You've already followed " + following);
            alertBox.showAndWait();
        }
        else {
            // observer pattern
            following.attach(user);
            user.setFollowingObservable(following);
        }
        return user;
    }

    public VBox getUserWindow() {
        return vBox;
    }

    // new window page set up
    public static void openUserWindow(User user, Group rootGroup) {
        UserWindow userWindow = new UserWindow(user, rootGroup);
        VBox vBox = userWindow.getUserWindow();
        Stage window = new Stage();
        window.setTitle(user.getID() + " | Mini Twitter");
        Scene scene = new Scene(vBox,700,400);
        window.setScene(scene);
        window.showAndWait();
    }

}
