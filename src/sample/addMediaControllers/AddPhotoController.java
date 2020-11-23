package sample.addMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.FxmlLoader;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPhotoController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    @FXML
    private void handleBrowsePhotoButton() {
        FileChooser fileC = new FileChooser();
            fileC.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

        File selectedF = fileC.showOpenDialog(null);

        if (selectedF != null) {
            sourcePath.setText(selectedF.getPath());
        } else {
            System.out.println("NOt");
        }
    }

    @FXML
    private void handleAddPhotoButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_photos_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}