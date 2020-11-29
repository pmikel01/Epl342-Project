package sample.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.MainScenesControllers.*;
import sample.SearchMediaControllers.SearchEventController;

import java.io.IOException;
import java.sql.Connection;

public class MainAppController {
    @FXML
    public Button closeButton;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        openStage("../MainScenes/sign_in.fxml");
    }

    @FXML
    public Button signOutButton;

    @FXML
    public void pressSignOutButton(ActionEvent event) {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        stage.close();
        openStage("../MainScenes/sign_in.fxml");
    }

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleSearchProfileButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/search_profiles.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchProfController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        mainPane.setCenter(view);
    }

    @FXML
    private void handleMyProfileButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/my_profile.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        MyProfController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        mainPane.setCenter(view);
    }

    @FXML
    private void handleEventButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/events.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventsController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        mainPane.setCenter(view);
    }

    @FXML
    private void handleStatisticsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/statistics.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        StatisticsController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        mainPane.setCenter(view);
    }

    @FXML
    private void handleFriendRequestsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/friend_requests.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        FriendRequestsController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        mainPane.setCenter(view);
    }

    @FXML
    private void handleHomeButton() throws IOException {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("../MainScenes/home");
        mainPane.setCenter(view);
    }

    @FXML
    private AnchorPane main_stage;

    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) main_stage.getScene().getWindow();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private double x, y;

    private void openStage(String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);

            //we gonna drag the frame
            root1.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            root1.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() - x);
                stage.setY(mouseEvent.getScreenY() - y);
            });
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load window");
        }
    }
}
