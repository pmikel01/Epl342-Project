package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> startF;

    @FXML
    private Spinner<Integer> startL;

    @FXML
    private Spinner<Integer> endF;

    @FXML
    private Spinner<Integer> endL;

    private String id;

    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleAddEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event", "my id");

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event", "my id");

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> CountStartF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0);
        this.startF.setValueFactory(CountStartF);
        SpinnerValueFactory<Integer> CountStartL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.startL.setValueFactory(CountStartL);
        SpinnerValueFactory<Integer> CountEndF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0);
        this.endF.setValueFactory(CountEndF);
        SpinnerValueFactory<Integer> CountEndL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.endL.setValueFactory(CountEndL);

        privacyBox.setValue("OPEN");
        privacyBox.setItems(privacyList);
    }
}