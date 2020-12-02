package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.Main.FxmlLoader;
import sample.Main.Location;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddLinkController implements Initializable {
    private static final String SQL_INSERT_LINK = "INSERT INTO [dbo].LINK (URL,Name,Caption,Description,Message,User_ID,ChangeLog) VALUES (?,?,?,?,?,?,?)";

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField name;

    @FXML
    private TextField link;

    @FXML
    private TextField description;

    @FXML
    private TextField caption;

    @FXML
    private TextField message;

    @FXML
    private Label error_l;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
        error_l.setTextFill(Color.web("#D8D9D9"));
    }

    @FXML
    private void handleAddLinkButton() throws IOException {
        if (name.getText().isEmpty() || link.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //URL,Name,Caption,Description,Message,User_ID,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_INSERT_LINK);
                stmt.setString(1, link.getText());
                stmt.setString(2, name.getText());

                if (caption.getText().isEmpty()) {
                    stmt.setNull(3, Types.VARCHAR);
                } else {
                    stmt.setString(3, caption.getText());
                }

                if (description.getText().isEmpty()) {
                    stmt.setNull(4, Types.VARCHAR);
                } else {
                    stmt.setString(4, description.getText());
                }

                if (message.getText().isEmpty()) {
                    stmt.setNull(5, Types.VARCHAR);
                } else {
                    stmt.setString(5, message.getText());
                }

                stmt.setInt(6, Integer.parseInt(myID));
                stmt.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now()));
                stmt.executeUpdate();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}