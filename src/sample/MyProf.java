package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProf implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleAlbumButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("albums_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("photos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("videos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("events_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("links_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFriendsButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("friends_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
