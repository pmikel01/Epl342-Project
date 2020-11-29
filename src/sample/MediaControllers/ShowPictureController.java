package sample.MediaControllers;

import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ShowPictureController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String photo_id;
    private Connection conn;

    public void initData(String id, String myID, String photo_id, Connection conn) {
        this.id = id;
        this.photo_id = photo_id;
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("picture", "my id", conn);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/photos_list.fxml"));
            Pane view = loader.load();

            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            controller.initData("picture",id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @FXML
    private void handleLikeButton(){
//        if (user already likes photo) {
//            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("LIKE", "Already liked this Picture");
//            dialog.openDialog();
//        } else {
//            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("LIKE", "Congratulations you liked the photo");
//            dialog.openDialog();
//        }
        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("LIKE", "Congratulations you liked the photo", "like");
        dialog.openDialog();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
