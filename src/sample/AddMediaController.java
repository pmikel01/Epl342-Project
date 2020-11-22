package sample;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import sample.FxmlLoader;

        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;
        import java.util.logging.Logger;

public class AddMediaController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private FileChooser fileChooser;

    private File file;

    private final Desktop desktop = Desktop.getDesktop();

    @FXML
    private Button addButtonPic;

    @FXML
    private Button addButtonVid;

    @FXML
    private void handleBrowsePhotoButton() {
        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
                );

        file = fileChooser.showOpenDialog(addButtonPic.getScene().getWindow());

        if (file != null) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                System.out.println("Error in Browse Image");
            }
        }
    }

    @FXML
    private void handleBrowseVideoButton() {
        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mp3")
        );

        file = fileChooser.showOpenDialog(addButtonVid.getScene().getWindow());

        if (file != null) {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                System.out.println("Error in Browse Image");
            }
        }
    }

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