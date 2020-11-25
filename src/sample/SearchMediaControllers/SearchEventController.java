package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.Objects.SearchAlbums;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SearchEventController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String id;

    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleSearchEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/search_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        ShowEventListController controller = loader.getController();

        //create query
        SearchEvents event = new SearchEvents("","", "", "", new Date());
        controller.initData(event);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}