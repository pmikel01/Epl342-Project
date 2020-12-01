package sample.MainScenesControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main.MainAppController;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;


public class SignInController implements Initializable {
    @FXML
    public Button closeButton;

    private Connection conn;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label error_l;

    public void initData(Connection conn) {
        this.conn = conn;
    }

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
        openStageRegister("../MainScenes/register.fxml");
    }

    @FXML
    private Button signInButton;

    @FXML
    public void handleSignInButton(ActionEvent event) {
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery(   "SELECT ID FROM PROFILE WHERE Username=? ");
            stmt = conn.prepareStatement("SELECT ID FROM PROFILE WHERE Username=? AND Password=?");
            stmt.setString(1,username.getText());
            stmt.setString(2,password.getText());
            rs = stmt.executeQuery();
            if (rs.next()) {
                int myID = rs.getInt("ID");
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.close();
                openStage("../MainScenes/main_app.fxml", myID+"");
            } else {
                error_l.setTextFill(Color.RED);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void openStage(String fileName, String myID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);

            MainAppController controller = fxmlLoader.getController();

            //create query
            controller.initData(myID, conn);

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

    private void openStageRegister(String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);

            RegisterController controller = fxmlLoader.getController();

            //create query
            controller.initData(conn);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_l.setTextFill(Color.web("#224D5E"));
    }
}
