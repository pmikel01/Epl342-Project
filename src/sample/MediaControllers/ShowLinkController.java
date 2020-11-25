package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowLinkController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;


    public void initData(String id) {
        this.id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
