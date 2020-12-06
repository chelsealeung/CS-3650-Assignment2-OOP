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
        addUser.setMaxWidth(120);
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
        addGroup.setMaxWidth(120);
        gridpane.add(addGroup,1,1);
        addGroup.setOnAction(e -> {
            TreeItem<SysEntry> branch = tree.getSelectionModel().getSelectedItem();
            String userID = groupIDTextField.getText();
            isGroupButton = true;
            makeBranch(branch, rootGroup, userID);
            groupIDTextField.clear();
        });

        Button openUserView = new Button("Open User View");
        openUserView.setMaxWidth(120);
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
        displayBox.setMinWidth(200);
        gridpane.add(displayBox,0,4);

        // visitor pattern
        Button showUser = new Button("User Total");
        showUser.setMaxWidth(120);
        gridpane.add(showUser,0,5);
        showUser.setOnAction(e -> {
            UserTotal userTotal = new UserTotal();
            rootGroup.accept(userTotal);
            displayBox.setText("Total User(s): " + userTotal.getter());
        });

        // visitor pattern
        Button showGroup = new Button("Group Total");
        showGroup.setMaxWidth(120);
        gridpane.add(showGroup,1,5);
        showGroup.setOnAction(e -> {
            GroupTotal groupTotal = new GroupTotal();
            rootGroup.accept(groupTotal);
            displayBox.setText("Total Group(s): " + groupTotal.getter());
        });

        // visitor pattern
        Button showMsg = new Button("Messages Total");
        showMsg.setMaxWidth(120);
        gridpane.add(showMsg,0,6);
        showMsg.setOnAction(e -> {
            MessagesTotal messagesTotal = new MessagesTotal();
            rootGroup.accept(messagesTotal);
            displayBox.setText("Total Message(s): " + messagesTotal.getter());
        });

        // visitor pattern
        Button showPos = new Button("Positive Percentage");
        showPos.setMaxWidth(120);
        gridpane.add(showPos,1,6);
        showPos.setOnAction(e -> {
            PositivePercentage positivePercentage = new PositivePercentage();
            rootGroup.accept(positivePercentage);
            displayBox.setText("Positive Message (integer): " + positivePercentage.getter() + "%");
        });

        /*
        Assignment 3: add two new buttons
        visitor pattern
         */
        Button checkID = new Button("ID Verification");
        checkID.setMaxWidth(120);
        gridpane.add(checkID,0,7);
        checkID.setOnAction(e -> {
            ValidID validID = new ValidID();
            rootGroup.accept(validID);
            if (validID.getValid()) {
                displayBox.setText("All IDs are valid");
            }
            else {
                displayBox.setText("Not all IDs are valid");
            }
        });

        Button lastUpdate = new Button("Last Updated");
        lastUpdate.setMaxWidth(120);
        gridpane.add(lastUpdate,1,7);
        lastUpdate.setOnAction(e -> {
            LatestUpdate latestUpdate = new LatestUpdate();
            rootGroup.accept(latestUpdate);
            displayBox.setText("Last Updated User: " + latestUpdate.getCurrentLatestUser());
        });

        VBox leftControl = new VBox(tree);        // left side of the scene
        VBox rightControl = new VBox(gridpane);     // right side of the scene
        leftControl.setMaxWidth(180);
        splitPane = new SplitPane(leftControl, rightControl);   // merge left and right together back to the scene
    }

    // add user or group under another group
    // composite pattern: user and group implements SysEntry
    // show dialog when ID is duplicated or contain spaces
    private TreeItem<SysEntry> makeBranch(TreeItem<SysEntry> sysEntryTreeItem, Group rootGroup, String name) {
        alertBox = new Alert(Alert.AlertType.ERROR);
        if (sysEntryTreeItem == null || sysEntryTreeItem.getValue() instanceof User) {
            alertBox.setContentText("Select a group");
            alertBox.showAndWait();
        }
        else if (sysEntryTreeItem.getValue() instanceof Group) {
            if (rootGroup.containsUserID(name) || rootGroup.containsGroupID(name)) {
                alertBox.setContentText(name + " is already in the group");
                alertBox.showAndWait();
                return sysEntryTreeItem;
            }
            if (rootGroup.containsSpace(name)) {
                alertBox.setContentText(name + " should not contain spaces");
                alertBox.showAndWait();
                return sysEntryTreeItem;
            }
            if (isUserButton) {
                User user = new User(); // create new user
                user.setID(name);
                user.setCreationTime();
                ((Group) sysEntryTreeItem.getValue()).setSysEntries(user);  // add new user to group (list)
                sysEntryTreeItem.getChildren().add(new TreeItem<>(user));   // tree item group add tree item user (tree)
                sysEntryTreeItem.setExpanded(true);
                isUserButton = false;
            }
            else if (isGroupButton) {
                Group group = new Group();  // create new group
                group.setID(name);
                group.setCreationTime();
                ((Group) sysEntryTreeItem.getValue()).setSysEntries(group); // add new group to group (list)
                sysEntryTreeItem.getChildren().add(new TreeItem<>(group));  // tree item group add tree item group (tree)
                sysEntryTreeItem.setExpanded(true);
                isGroupButton = false;
            }
        }
        return sysEntryTreeItem;
    }

    public SplitPane getAdminWindow() {
        return splitPane;
    }

}
