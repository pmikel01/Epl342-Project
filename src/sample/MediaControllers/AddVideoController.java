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
import sample.SearchMediaControllers.SearchAlbumController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddVideoController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleBrowseVideoButton() {
        FileChooser fileC = new FileChooser();
        fileC.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mp3", "*.wmv", "*.mov", "*.flv", "*.mkv")
            );

        File selectedF = fileC.showOpenDialog(null);

        if (selectedF != null) {
            sourcePath.setText(selectedF.getPath());
        } else {
            System.out.println("NOt");
        }
    }

    @FXML
    private void handleAddVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("video", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("video", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}