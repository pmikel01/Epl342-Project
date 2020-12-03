package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class EditPhotoController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    private String mediaID;
    private String myID;
    private Connection conn;
    private int albumID;

    public void initData(String mediaID, String myID, Connection conn, int albumID) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;
        this.albumID = albumID;
    }

    @FXML
    private void handleBrowsePhotoButton() {
        FileChooser fileC = new FileChooser();
            fileC.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

        File selectedF = fileC.showOpenDialog(null);

        if (selectedF != null) {
            sourcePath.setText(selectedF.getPath());
        } else {
            System.out.println("NOt");
        }
    }

    @FXML
    private void handleUpdatePhotoButton() throws IOException {
        if (albumID == 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("picture", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            ShowAlbumController controller = loader.getController();

            //create query
            controller.initData(myID, myID, albumID+"", conn);

            p_pane.getChildren().setAll(showProfParent);
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (albumID == 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("picture", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            ShowAlbumController controller = loader.getController();

            //create query
            controller.initData(myID, myID, albumID+"", conn);

            p_pane.getChildren().setAll(showProfParent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}