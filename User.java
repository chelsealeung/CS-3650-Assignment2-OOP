package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

// composite, observer pattern
public class User extends Subject implements Observer, SysEntry {

    private String userID;
    private List<String> totalTweet = new ArrayList<String>();  // used for MessageTotal and PositivePercentage
    // ObservableList: A list that allows listeners to track changes when they occur
    private List<User> following = new ArrayList<User>();
    private ObservableList<User> followingObservable = FXCollections.observableList(following);
    private List<User> follower = new ArrayList<User>();
    private ObservableList<User> followerObservable = FXCollections.observableList(follower);
    private List<String> newsFeed = new ArrayList<>();
    private ObservableList<String> newsFeedObservable = FXCollections.observableList(newsFeed);

    @Override
    public String getID() {
        return this.userID;
    }

    @Override
    public void setID(String name) {
        this.userID = name;
    }

    @Override
    public String toString() {  // hashcode to value
        return this.userID;
    }   // hashcode to value

    @Override
    public void accept(SysEntryVisitor visitor) {
        visitor.visitUser(this);
    }

    public List<String> getTweet() {
        return totalTweet;
    }

    // Observer
    @Override
    public void update(Subject subject, String tweet) {
        if (subject instanceof User) {
            newsFeedObservable.add(((User) subject).getID() + ": " + tweet);
        }
    }

    public ObservableList<User> getFollowingObservable() {
        return followingObservable;
    }

    public void setFollowingObservable(User user) {
        followingObservable.add(user);
    }

    public ObservableList<User> getFollowerObservable() {
        return followerObservable;
    }

    public void setFollowerObservable(User user) {
        followerObservable.add(user);
    }

    public ObservableList<String> getNewsFeedObservable() {
        return newsFeedObservable;
    }

    public void setNewsFeedObservable(String tweet) {
        totalTweet.add(tweet);
        newsFeedObservable.add(userID + ": " + tweet);
        notifyObservers(tweet);
    }

}
