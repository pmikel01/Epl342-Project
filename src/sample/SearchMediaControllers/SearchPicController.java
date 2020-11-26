package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchAlbums;
import sample.Objects.SearchLinks;
import sample.Objects.SearchPhotos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPicController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> spinn;

    private String id;
    private String myID;

    public void initData(String id, String myID) {
        this.id = id;
        this.myID = myID;
    }

    @FXML
    private void handleSearchPhotoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/search_photos_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        ShowPicListController controller = loader.getController();

        //create query
        SearchPhotos pic = new SearchPhotos("","", "", 0);
        controller.initData(pic, "id", "myID");

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            SearchAlbums album = new SearchAlbums("","", "", "");
            controller.initData("picture", "id");

            p_pane.getChildren().setAll(view);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/photos_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            SearchAlbums album = new SearchAlbums("","", "", "");
            controller.initData("picture", "id", "my id");

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9999,1);
        this.spinn.setValueFactory(spinnCount);
    }
}