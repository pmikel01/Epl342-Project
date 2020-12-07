package sample.MainScenesControllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.MediaListsControllers.EventSearchController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class DatabaseController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleDeleteButton() throws IOException {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("DELETE DATABASE");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Are You Sure?(Enter \"OK\"): ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0) {
            if (input.getText().equals("OK")) {
                ResultSet rs = null;
                CallableStatement stmt = null;
                try {
                    stmt = conn.prepareCall("{call Procedure_RESET}");
                    stmt.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Stage stage = (Stage) p_pane.getScene().getWindow();
                stage.close();
                openStage("../MainScenes/sign_in.fxml");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
