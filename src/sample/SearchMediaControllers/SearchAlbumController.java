package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Objects.SearchAlbums;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchAlbumController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String id;

    public void initData(String id) {
        this.id = id;
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
        SearchAlbums album = new SearchAlbums("","", "", "");
        controller.initData(album);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}