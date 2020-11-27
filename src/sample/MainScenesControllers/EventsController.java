package sample.MainScenesControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaControllers.ShowEventController;
import sample.MediaListsControllers.EventSearchController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EventsController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleSearchButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventSearchController controller = loader.getController();

        SearchEvents events = new SearchEvents("", "", "", "", new Date());
        //create query
        controller.initData(events, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLeastPopularButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventSearchController controller = loader.getController();

        //create query
        controller.initData(myID);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
