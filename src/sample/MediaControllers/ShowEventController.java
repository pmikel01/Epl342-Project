package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowEventController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String event_id;

    public void initData(String id, String myID, String event_id) {
        this.id = id;
        this.event_id = event_id;
        this.myID = myID;
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("event", myID);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/events_list.fxml"));
            Pane view = loader.load();

            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            controller.initData("event",id, myID);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
