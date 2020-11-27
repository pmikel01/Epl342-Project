package sample.MainScenesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.ProfSelection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> infoList;

    String myID;

    public void initData(String id, String myID) {
        this.id = id;
        this.myID = myID;

        infoList.setItems(items);
        //loop
        items.add("903458   Pantelis Mikelli");
    }

    @FXML
    private void handleAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("album",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/photos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("picture",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("video",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/events_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("event",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/links_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("link",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSendFRButton() throws IOException {
        //handle FR
    }

    @FXML
    private void handleEducationButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/education_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("education",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/interests_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("interest",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/quotes_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("quote",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/work_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("work",id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFRButton() {
//        if (user already likes photo) {
//            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("Send Friend Request", "Already friends with this user");
//            dialog.openDialog();
//        } else {
//            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("Send Friend Request", "Congratulations you liked the photo");
//            dialog.openDialog();
//        }
        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("Send Friend Request", "Congratulations request sent", "fr");
        dialog.openDialog();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
