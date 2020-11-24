package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleChangelogButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/changelog");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAlbumButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_albums_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_photos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_videos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_events_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_links_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFriendsButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_friends_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEditButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/edit_information");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEducationButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_education_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_interests_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_quotes_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_work_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
