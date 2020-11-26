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
import java.util.ResourceBundle;

public class AddPhotoInAlbumController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    private String myID;
    private String albumID;

    public void initData(String myID, String albumID) {
        this.myID = myID;
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
    private void handleAddPhotoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/edit_album.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditAlbumController controller = loader.getController();

        //create query
        controller.initData(albumID, myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}