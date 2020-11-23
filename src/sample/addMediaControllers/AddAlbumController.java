package sample.addMediaControllers;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.FileChooser;
        import sample.FxmlLoader;

        import java.io.File;
        import java.net.URL;
        import java.util.ResourceBundle;

public class AddAlbumController implements Initializable {

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleAddAlbumButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_albums_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        privacyBox.setValue("OPEN");
        privacyBox.setItems(privacyList);
    }
}