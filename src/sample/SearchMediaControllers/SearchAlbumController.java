package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchAlbums;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SearchAlbumController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField name;

    @FXML
    private TextField location;

    @FXML
    private TextField description;

    private String id;
    private String myID;
    private Connection conn;

    public void initData(String id, String myID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleSearchAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/search_albums_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        ShowAlbumListController controller = loader.getController();

        //create query
        SearchAlbums album = new SearchAlbums("",name.getText(), location.getText(), description.getText());
        controller.initData(album, id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            controller.initData("album", myID, conn);

            p_pane.getChildren().setAll(view);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            MediaListController controller = loader.getController();

            controller.initData("album", id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}