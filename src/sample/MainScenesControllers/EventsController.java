package sample.MainScenesControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleSearchButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("../MediaLists/search_events_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLeastPopularButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("../MediaLists/search_events_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}