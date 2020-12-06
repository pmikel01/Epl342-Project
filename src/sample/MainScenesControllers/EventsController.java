package sample.MainScenesControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaControllers.ShowEventController;
import sample.MediaListsControllers.EventSearchController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.ResourceBundle;

public class EventsController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleSearchButton() throws IOException {
//        if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null) {
//
//        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null){
//
//        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null){
//
//        }




        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventSearchController controller = loader.getController();

        SearchEvents events = new SearchEvents("", "", "", "", new DatePicker());
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
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
