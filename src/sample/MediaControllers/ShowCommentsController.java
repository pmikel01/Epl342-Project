package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowCommentsController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String choose;
    private String mediaID;
    private Connection conn;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> listV;

    public void initData(String id, String myID, String choose, String mediaID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.choose = choose;
        this.mediaID = mediaID;
        this.conn = conn;

        listV.setItems(items);
        //loop
        items.add("Pantelis Mikelli: COMMENT........");
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (choose.equals("album")) {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("album", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("album",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        } else {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("video", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("video",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        }
    }

    @FXML
    public void pressAddButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("Make A Comment");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Comment: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
