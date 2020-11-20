package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainAppController {
    @FXML
    public Button closeButton;

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        openStage("scenes/sign_in.fxml");
    }

    @FXML
    public Button signOutButton;

    @FXML
    public void pressSignOutButton(ActionEvent event) {
        Stage stage = (Stage) signOutButton.getScene().getWindow();
        stage.close();
        openStage("scenes/sign_in.fxml");
    }

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleSearchProfileButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/search_profiles");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleMyProfileButton(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/my_profile");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleEventButton(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/events");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleStatisticsButton(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/statistics");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleFriendRequestsButton(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/friend_requests");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleHomeButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/home");
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
