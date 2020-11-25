package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class EditLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String id;

    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleAddLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("../MediaLists/edit_links_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}