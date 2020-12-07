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
import java.sql.*;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Spinner<Integer> spinF;

    @FXML
    private Spinner<Integer> spinU;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
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
        controller.initData("stat1", myID, conn, 0);

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
        controller.initData("stat2", myID, conn, 0);

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
        controller.initData("stat3", myID, conn, 0);

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
        controller.initData("stat4", myID, conn, 0);

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
        controller.initData("stat5", myID, conn, spinF.getValue());

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
        controller.initData("stat6", myID, conn, spinU.getValue());

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
        controller.initData("stat7", myID, conn, 0);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    public Button avg_b;

    @FXML
    public void pressAvgButton(ActionEvent event) {
        //Procedure_Network_AVG_AGE
        ResultSet rs = null;
        CallableStatement stmt = null;
        try {
            stmt = conn.prepareCall("{call Procedure_Network_AVG_AGE(?)}");
            stmt.setInt(1,Integer.parseInt(myID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                int avg = rs.getInt(1);

                sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("Average Age Of Network", avg+"", "avg");
                dialog.openDialog();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinFCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9999,0);
        this.spinF.setValueFactory(spinFCount);
        SpinnerValueFactory<Integer> spinUCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,9999,0);
        this.spinU.setValueFactory(spinUCount);
    }
}
