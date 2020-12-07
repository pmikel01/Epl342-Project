package sample.MainScenesControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField name;

    @FXML
    private TextField owner;

    @FXML
    private TextField location;

    @FXML
    private TextField venue;

    @FXML
    private DatePicker date;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleSearchButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventSearchController controller = loader.getController();

        SearchEvents events = new SearchEvents(owner.getText(), name.getText(), location.getText(), venue.getText(), date);
        //create query
        controller.initData(events, myID, conn);

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
