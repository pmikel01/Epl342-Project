package sample.MainScenesControllers;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.FriendListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Spinner<Integer> spinF;

    @FXML
    private Spinner<Integer> spinU;

    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleMostFamousButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat1", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSameFriendsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat2", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleNetworkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat3", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSameFriendsMoreButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat4", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFriendsMoreAlbumsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat5", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleUsersMoreAlbumsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat6", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSameInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/friends_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendListController controller = loader.getController();

        //create query
        controller.initData("stat7", myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    public Button avg_b;

    @FXML
    public void pressAvgButton(ActionEvent event) {
        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("Average Age Of Network", "45", "avg");
        dialog.openDialog();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinFCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,1);
        this.spinF.setValueFactory(spinFCount);
        SpinnerValueFactory<Integer> spinUCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,1);
        this.spinU.setValueFactory(spinUCount);
    }
}
