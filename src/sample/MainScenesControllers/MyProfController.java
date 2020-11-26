package sample.MainScenesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String myID;

    public void initData(String myID) {
        this.myID = myID;

        listV.setItems(items);
        //loop
        items.add("Pantelis Mikelli");
        items.add("pantelismike@gmail.com");
    }

    @FXML
    private void handleChangelogButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/changelog.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        ChangelogController controller = loader.getController();

        //create query
        controller.initData(myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("picture",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("video",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFriendsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_friends_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("friend",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEditButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/edit_information.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditInfoController controller = loader.getController();

        //create query
        controller.initData(myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEducationButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_education_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("education",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_interests_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("interest",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_quotes_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("quote",myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_work_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("work",myID);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
