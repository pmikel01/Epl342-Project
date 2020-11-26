package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable {

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private AnchorPane p_pane ;

    private String mediaID;
    private String myID;

    public void initData(String mediaID, String myID) {
        this.mediaID = mediaID;
        this.myID = myID;
    }

    @FXML
    private void handleAddPictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_photo_in_album.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddPhotoInAlbumController controller = loader.getController();

        //create query
        controller.initData(myID, mediaID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleUpdateAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        privacyBox.setValue("OPEN");
        privacyBox.setItems(privacyList);
    }
}