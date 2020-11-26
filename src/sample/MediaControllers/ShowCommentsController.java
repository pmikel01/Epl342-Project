package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowCommentsController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String choose;
    private String mediaID;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> listV;

    public void initData(String id, String myID, String choose, String mediaID) {
        this.id = id;
        this.myID = myID;
        this.choose = choose;
        this.mediaID = mediaID;

        listV.setItems(items);
        //loop
        items.add("Pantelis Mikelli: COMMENT........");
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (choose.equals("album")) {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("album", "my id");

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("album","id", "my id");

                p_pane.getChildren().setAll(view);
            }
        } else {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("video", "my id");

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("video","id", "my id");

                p_pane.getChildren().setAll(view);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
