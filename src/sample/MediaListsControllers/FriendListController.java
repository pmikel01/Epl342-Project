package sample.MediaListsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String choose;
    private String myID;

    public void initData(String choose, String myID) {
        this.choose = choose;
        this.myID = myID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
