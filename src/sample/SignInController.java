package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class SignInController {
    @FXML
    public Button closeButton;

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button registerButton;

    private double x, y;

    @FXML
    public void handleRegisterButton(ActionEvent event) {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
        openStage("register.fxml");
    }

    @FXML
    private Button signInButton;

    @FXML
    public void handleSignInButton(ActionEvent event) {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();
        openStage("main_app.fxml");
    }

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
