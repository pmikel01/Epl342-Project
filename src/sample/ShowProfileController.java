package sample;

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
import sample.ShowMedia.MediaController;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowProfileController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private Profile prof;

    @FXML
    ListView<String> infoList;

    public void initData(Profile prof) {
        this.prof = prof;

        infoList.setItems(items);
        //loop
        items.add("903458   Pantelis Mikelli");
    }

    @FXML
    private void handleAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/albums_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaController controller = loader.getController();

        //create query
        controller.initData("album","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/photos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaController controller = loader.getController();

        //create query
        controller.initData("picture","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/videos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaController controller = loader.getController();

        //create query
        controller.initData("video","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/events_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaController controller = loader.getController();

        //create query
        controller.initData("event","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/links_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaController controller = loader.getController();

        //create query
        controller.initData("link","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSendFRButton() throws IOException {
        //handle FR
    }

    @FXML
    private void handleEducationButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/education_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        QewiController controller = loader.getController();

        //create query
        controller.initData("education","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/interests_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        QewiController controller = loader.getController();

        //create query
        controller.initData("interest","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/quotes_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        QewiController controller = loader.getController();

        //create query
        controller.initData("quote","id");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/work_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        QewiController controller = loader.getController();

        //create query
        controller.initData("work","id");

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
