package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String mediaID;
    private String myID;

    public void initData(String mediaID, String myID) {
        this.mediaID = mediaID;
        this.myID = myID;
    }

    @FXML
    private void handleUpdateLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link", myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}