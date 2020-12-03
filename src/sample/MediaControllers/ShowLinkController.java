package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowLinkController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Hyperlink link;

    @FXML
    private Label caption;

    @FXML
    private Label message;

    @FXML
    private Label l_id;

    private String id;
    private String myID;
    private String link_id;
    private Connection conn;

    public void initData(String id, String myID, String link_id, Connection conn) {
        this.id = id;
        this.link_id = link_id;
        this.myID = myID;
        this.conn = conn;

        name.setText("");
        description.setText("");
        link.setText("");
        caption.setText("");
        message.setText("");
        l_id.setText(link_id);
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Description,URL,Caption,Message FROM LINK WHERE Link_ID=?");
            stmt.setInt(1, Integer.parseInt(link_id));
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
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("link", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/links_list.fxml"));
            Pane view = loader.load();

            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            controller.initData("link",id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
