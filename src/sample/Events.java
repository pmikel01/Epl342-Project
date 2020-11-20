package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Events implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleSearchButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("events_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLeastPopularButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("events_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
