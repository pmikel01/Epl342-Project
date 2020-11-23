package sample.searchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchVidController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> spinn;

    @FXML
    private void handleSearchVideoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/search_videos_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9999,1);
        this.spinn.setValueFactory(spinnCount);
    }
}