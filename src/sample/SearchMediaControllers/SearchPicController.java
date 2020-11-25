package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
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

    public void initData(String id) {
        this.id = id;
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
        controller.initData(pic);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9999,1);
        this.spinn.setValueFactory(spinnCount);
    }
}