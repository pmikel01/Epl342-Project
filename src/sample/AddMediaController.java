package sample;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.FxmlLoader;

        import java.net.URL;
        import java.util.ResourceBundle;

public class AddMediaController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleAddAlbumButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_albums_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddEventButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_events_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_links_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddPhotoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_photos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddVideoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_videos_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}