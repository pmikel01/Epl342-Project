package sample.SearchMediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchAlbums;
import sample.Objects.SearchPhotos;
import sample.Objects.SearchVideos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SearchVidController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> spinn;

    @FXML
    private Spinner<Integer> spinn2;

    @FXML
    private TextField message;

    @FXML
    private TextField description;

    private String id;
    private String myID;
    private Connection conn;

    public void initData(String id, String myID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleSearchVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/search_videos_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        ShowVidListController controller = loader.getController();

        //create query
        SearchVideos video = new SearchVideos("",message.getText(),description.getText(),spinn.getValue(),spinn2.getValue());
        controller.initData(video, id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            controller.initData("video", myID, conn);

            p_pane.getChildren().setAll(view);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            MediaListController controller = loader.getController();

            controller.initData("video", id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9999,0);
        this.spinn.setValueFactory(spinnCount);

        SpinnerValueFactory<Integer> spinnCount2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9999,0);
        this.spinn2.setValueFactory(spinnCount2);
    }
}