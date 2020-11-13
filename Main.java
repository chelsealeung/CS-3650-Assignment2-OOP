package sample;

import com.sun.source.tree.Tree;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    Stage window;
    TreeView<String> tree;

//    private static Stage window;
//
//    // Singleton Pattern (not working)
//
//    private AdminWindow() {
//    }
//
//    public static Stage getInstance() {
//        if (window == null) {
//            window = new Stage();
//        }
//        return window;
//    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Mini Twitter");

        // scene
        SplitPane splitPane = new SplitPane();

        // right side of the scene
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10,10,10,10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        TextField userIDTextField = new TextField("Type User ID");
        gridpane.add(userIDTextField,0,0);

        Button addUser = new Button("Add User");
        gridpane.add(addUser,1,0);

        TextField groupIDTextField = new TextField("Type Group ID");
        gridpane.add(groupIDTextField,0,1);

        Button addGroup = new Button("Add Group");
        gridpane.add(addGroup,1,1);

        Button openUserView = new Button("Open User View");
        gridpane.add(openUserView,0,2);
        openUserView.setOnAction(e -> {
            UserWindow.userWindow();
        });

        TextField displayBox = new TextField("Message Display Here");
        displayBox.setEditable(false);
        gridpane.add(displayBox,0,4);

        Button showUser = new Button("Show User Total");
        gridpane.add(showUser,0,5);

        Button showGroup = new Button("Show Group Total");
        gridpane.add(showGroup,1,5);

        Button showMsg = new Button("Show Messages Total");
        gridpane.add(showMsg,0,6);

        Button showPos = new Button("Show Positive Percentage");
        gridpane.add(showPos,1,6);

        VBox rightControl = new VBox(gridpane);

        // left side of the scene
        TreeItem<String> root;
        root = new TreeItem<>();    // root: container for all branches (a and b)
        root.setValue("Root");
        root.setExpanded(true);     // everything expanded by default
        tree = new TreeView<>(root);    // create tree
        VBox leftControl = new VBox(tree);

        // buttons action
        addUser.setOnAction(e -> {
            // branch
            ArrayList<TreeItem> userList = new ArrayList<>();
            TreeItem<String> branch;
            branch = makeBranch(userIDTextField.getText(), root);
            userList.add(branch);
        });

        addGroup.setOnAction(e -> {
            Group group = new Group();
            group.setSysEntryList(Arrays.asList(new SysEntry[] {}));
            // branch
            TreeItem<String> branch;
            branch = makeBranch(groupIDTextField.getText(), root);
        });

        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            handleMouseClicked(event);
        };
        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

        // merge left and right together back to the scene
        splitPane.getItems().addAll(leftControl, rightControl);

        Scene scene = new Scene(splitPane,700,500);
        window.setScene(scene);
        window.show();
    }

    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem)tree.getSelectionModel().getSelectedItem()).getValue();
            System.out.println("Node click: " + name);
        }
    }

    // create branches (user or group)
    public TreeItem<String> makeBranch(String name, TreeItem<String> parent) {
//        ArrayList<TreeItem> selectedTreeItems = new ArrayList<>();
//        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
//                System.out.println("Selected Text: " + newValue.getValue()));
        TreeItem<String> item = new TreeItem<>(name);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
