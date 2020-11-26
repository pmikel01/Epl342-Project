package sample.MainScenesControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class QewiController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String choose;
    private String id;
    private String myID;

    public void initData(String choose, String id, String myID) {
        this.choose = choose;
        this.id = id;
        this.myID = myID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
