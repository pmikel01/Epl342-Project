package sample;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.FxmlLoader;

        import java.net.URL;
        import java.util.ResourceBundle;

public class SearchMediaController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleSearchAlbumButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/search_albums");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchEventButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/search_events");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/search_links");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchPhotoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/search_photos");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchVideoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/search_videos");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}