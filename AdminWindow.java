package sample;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AdminWindow {

    private SplitPane splitPane;
    private boolean isGroupButton = false;
    private boolean isUserButton = false;
    private Alert alertBox;

    // singleton pattern
    private static AdminWindow instance;

    // singleton pattern
    public static AdminWindow getInstance() {
        if (instance == null) {
            instance = new AdminWindow();
        }
        return instance;
    }

    // singleton pattern
    private AdminWindow() {

        // set up left pane
        Group rootGroup = new Group();
        rootGroup.setID("Root");
        TreeItem<SysEntry> root = new TreeItem<>(rootGroup);    // root: container for all branches (a and b)
        root.setExpanded(true);     // everything expanded by default
        TreeView<SysEntry> tree = new TreeView<>();    // create tree
        tree.setRoot(root);

        // set up right pane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10,10,10,10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        TextField userIDTextField = new TextField();
        userIDTextField.setPromptText("Type User ID");
        gridpane.add(userIDTextField,0,0);

        Button addUser = new Button("Add User");
        addUser.setMaxWidth(170);
        gridpane.add(addUser,1,0);
        addUser.setOnAction(e -> {
            TreeItem<SysEntry> branch = tree.getSelectionModel().getSelectedItem();
            String userID = userIDTextField.getText();
            isUserButton = true;
            makeBranch(branch, rootGroup, userID);
            userIDTextField.clear();
        });

        TextField groupIDTextField = new TextField();
        groupIDTextField.setPromptText("Type Group ID");
        gridpane.add(groupIDTextField,0,1);

        Button addGroup = new Button("Add Group");
        addGroup.setMaxWidth(170);
        gridpane.add(addGroup,1,1);
        addGroup.setOnAction(e -> {
            TreeItem<SysEntry> branch = tree.getSelectionModel().getSelectedItem();
            String userID = groupIDTextField.getText();
            isGroupButton = true;
            makeBranch(branch, rootGroup, userID);
            groupIDTextField.clear();
        });

        Button openUserView = new Button("Open User View");
        openUserView.setMaxWidth(170);
        gridpane.add(openUserView,0,2);
        openUserView.setOnAction(e -> {
            TreeItem<SysEntry> branch = tree.getSelectionModel().getSelectedItem();
            if (branch.getValue() instanceof Group) {
                alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setContentText("Select a user");
                alertBox.showAndWait();
            }
            if (branch.getValue() instanceof User) {
                User user = (User)branch.getValue();
                UserWindow.openUserWindow(user, rootGroup);
            }
        });

        TextField displayBox = new TextField();
        displayBox.setPromptText("Message Display Here");
        displayBox.setEditable(false);
        displayBox.setMinWidth(250);
        gridpane.add(displayBox,0,4);

        // visitor pattern
        Button showUser = new Button("Show User Total");
        showUser.setMaxWidth(170);
        gridpane.add(showUser,0,5);
        showUser.setOnAction(e -> {
            UserTotal userTotal = new UserTotal();
            rootGroup.accept(userTotal);
            displayBox.setText("Total User(s): " + userTotal.getter());
        });

        // visitor pattern
        Button showGroup = new Button("Show Group Total");
        showGroup.setMaxWidth(170);
        gridpane.add(showGroup,1,5);
        showGroup.setOnAction(e -> {
            GroupTotal groupTotal = new GroupTotal();
            rootGroup.accept(groupTotal);
            displayBox.setText("Total Group(s): " + groupTotal.getter());
        });

        // visitor pattern
        Button showMsg = new Button("Show Messages Total");
        showMsg.setMaxWidth(170);
        gridpane.add(showMsg,0,6);
        showMsg.setOnAction(e -> {
            MessagesTotal messagesTotal = new MessagesTotal();
            rootGroup.accept(messagesTotal);
            displayBox.setText("Total Message(s): " + messagesTotal.getter());
        });

        // visitor pattern
        Button showPos = new Button("Show Positive Percentage");
        showPos.setMaxWidth(170);
        gridpane.add(showPos,1,6);
        showPos.setOnAction(e -> {
            PositivePercentage positivePercentage = new PositivePercentage();
            rootGroup.accept(positivePercentage);
            displayBox.setText("Positive Message (integer): " + positivePercentage.getter() + "%");
        });

        VBox leftControl = new VBox(tree);        // left side of the scene
        VBox rightControl = new VBox(gridpane);     // right side of the scene
        rightControl.setMinWidth(450);
        splitPane = new SplitPane(leftControl, rightControl);   // merge left and right together back to the scene
    }

    // add user or group under another group
    // composite pattern: user and group implements SysEntry
    private TreeItem<SysEntry> makeBranch(TreeItem<SysEntry> sysEntryTreeItem, Group rootGroup, String name) {
        alertBox = new Alert(Alert.AlertType.ERROR);
        if (sysEntryTreeItem == null || sysEntryTreeItem.getValue() instanceof User) {
            alertBox.setContentText("Select a group");
            alertBox.showAndWait();
        }
        else if (sysEntryTreeItem.getValue() instanceof Group) {
            if (isUserButton) {
                if (rootGroup.containsUserID(name)) {
                    alertBox.setContentText(name + " is already in the group");
                    alertBox.showAndWait();
                }
                else {
                    User user = new User(); // create new user
                    user.setID(name);
                    ((Group) sysEntryTreeItem.getValue()).setSysEntries(user);  // add new user to group (list)
                    sysEntryTreeItem.getChildren().add(new TreeItem<>(user));   // tree item group add tree item user (tree)
                    sysEntryTreeItem.setExpanded(true);
                    isUserButton = false;
                }
            }
            if (isGroupButton) {
                if (rootGroup.containsGroupID(name)) {
                    alertBox.setContentText(name + " is already in the group");
                    alertBox.showAndWait();
                }
                else {
                    Group group = new Group();  // create new group
                    group.setID(name);
                    ((Group) sysEntryTreeItem.getValue()).setSysEntries(group); // add new group to group (list)
                    sysEntryTreeItem.getChildren().add(new TreeItem<>(group));  // tree item group add tree item group (tree)
                    sysEntryTreeItem.setExpanded(true);
                    isGroupButton = false;
                }
            }
        }
        return sysEntryTreeItem;
    }

    public SplitPane getAdminWindow() {
        return splitPane;
    }

}
