package sample.MediaListsControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.MainScenesControllers.ShowProfController;
import sample.MediaControllers.*;
import sample.SearchMediaControllers.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeLogListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String choose;
    private String myID;

    public void initData(String choose, String myID) {
        this.choose = choose;
        this.myID = myID;

        if (choose.equals("Albums")) {
            listV.setItems(items);
            //loop
            items.add("album log");
        } else if (choose.equals("Pictures")) {
            listV.setItems(items);
            //loop
            items.add("picture name");
        } else if (choose.equals("Videos")) {
            listV.setItems(items);
            //loop
            items.add("video log");
        } else if (choose.equals("Events")) {
            listV.setItems(items);
            //loop
            items.add("event log");
        } else if (choose.equals("Links")) {
            listV.setItems(items);
            //loop
            items.add("link log");
        } else if (choose.equals("Friends")) {
            listV.setItems(items);
            //loop
            items.add("friend log");
        } else if (choose.equals("all")) {
            listV.setItems(items);
            //loop
            items.add("logs");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}