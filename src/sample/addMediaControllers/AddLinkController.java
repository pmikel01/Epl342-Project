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

public class AddLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleAddLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("edit_lists/edit_links_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}