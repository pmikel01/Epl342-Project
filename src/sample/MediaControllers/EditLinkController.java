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
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditLinkController implements Initializable {
    private static final String SQL_UPDATE_LINK = "UPDATE LINK SET URL=?,Name=?,Caption=?,Description=?,Message=? WHERE Link_ID=?";

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

    private String mediaID;
    private String myID;
    private Connection conn;

    public void initData(String mediaID, String myID, Connection conn) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;
        error_l.setTextFill(Color.web("#D8D9D9"));

        name.setText("");
        description.setText("");
        link.setText("");
        caption.setText("");
        message.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Description,URL,Caption,Message FROM LINK WHERE Link_ID=?");
            stmt.setInt(1, Integer.parseInt(mediaID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("Name"));
                link.setText(rs.getString("URL"));
                if (rs.getString("Description") != null) {
                    description.setText(rs.getString("Description"));
                }

                if (rs.getString("Caption") != null) {
                    caption.setText(rs.getString("Caption"));
                }

                if (rs.getString("Message") != null) {
                    message.setText(rs.getString("Message"));
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateLinkButton() throws IOException {
        if (name.getText().isEmpty() || link.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //URL,Name,Caption,Description,Message,User_ID,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_UPDATE_LINK);
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

                stmt.setInt(6, Integer.parseInt(mediaID));

                stmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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