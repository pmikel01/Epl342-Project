package sample.MainScenesControllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    private static final String SQL_INSERT_USER = "INSERT INTO [dbo].PROFILE (Username,Password,FirstName,LastName,Birthday,Email,Gender,Verified) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_LOC = "INSERT INTO [dbo].LOCATION (Name) VALUES (?)";

    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");

    @FXML
    public Button closeButton;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private Label error_l;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<String> genderBox;

    private Connection conn;

    public void initData(Connection conn) {
        this.conn = conn;
    }

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        openStage("../MainScenes/sign_in.fxml");
    }

    @FXML
    public Button signInButton;

    @FXML
    public void pressSignInButtonButton(ActionEvent event) {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();
        openStage("../MainScenes/sign_in.fxml");
    }

    @FXML
    public Button registerButton;

    @FXML
    public void pressRegisterButtonButton(ActionEvent event) {
        String user = username.getText();
        String first = firstname.getText();
        String last = lastname.getText();
        String pass1 = password1.getText();
        String pass2 = password2.getText();
        String mail = email.getText();
        String gender = genderBox.getValue();
        LocalDate bd_local = birthday.getValue();

        if (user.isEmpty()) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Enter username");
        } else if(first.isEmpty()) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Enter FirstName");
        } else if(last.isEmpty()) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Enter LastName");
        } else if(pass1.isEmpty()) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Enter password");
        } else if(pass2.isEmpty()) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Confirm password");
        } else if(!pass1.equals(pass2)) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Passwords differ");
        }  else if(user.equals(pass1)) {
            error_l.setTextFill(Color.RED);
            error_l.setText("Easy password");
        } else {
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT ID FROM PROFILE WHERE Username=?");
                stmt.setString(1,user);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    error_l.setTextFill(Color.RED);
                    error_l.setText("Username exists");
                } else {
                    PreparedStatement preparedStatement;
                    //Username,Password,FirstName,LastName,Birthday,Email,Gender,Verified
                    try {
                        preparedStatement = conn.prepareStatement(SQL_INSERT_USER);
                        preparedStatement.setString(1, user);
                        preparedStatement.setString(2, pass1);
                        preparedStatement.setString(3, first);
                        preparedStatement.setString(4, last);
                        if (bd_local==null) {
                            preparedStatement.setNull(5, Types.DATE);
                        } else {
                            Date bd = Date.valueOf(bd_local);
                            preparedStatement.setDate(5, bd);
                        }
                        if (mail.isEmpty()) {
                            preparedStatement.setNull(6, Types.VARCHAR);
                        } else {
                            preparedStatement.setString(6, mail);
                        }
                        boolean gend = gender.equals("Female");
                        preparedStatement.setBoolean(7,gend);
                        preparedStatement.setBoolean(8,false);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.err.println("error in statement users");
                    }
                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.close();
                    openStage("../MainScenes/sign_in.fxml");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private double x, y;

    private void openStage(String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);

            SignInController controller = fxmlLoader.getController();

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
        genderBox.setValue("Male");
        genderBox.setItems(genderList);
        error_l.setTextFill(Color.web("#224D5E"));
    }
}
