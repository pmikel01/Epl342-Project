package sample.MainScenesControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendRequestsController implements Initializable {
    @FXML
    private AnchorPane p_pane ;


    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
