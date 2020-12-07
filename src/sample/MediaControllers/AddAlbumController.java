package sample.MediaControllers;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;
        import sample.Main.FxmlLoader;
        import sample.MediaListsControllers.EditMediaListController;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.*;
        import java.time.LocalDate;
        import java.util.ResourceBundle;
        import sample.Main.Location;

public class AddAlbumController implements Initializable {
    private static final String SQL_INSERT_ALBUM = "INSERT INTO [dbo].ALBUM (Title,Desciption,Privacy,Count_Images,Count_Videos,User_ID,Taken) VALUES (?,?,?,?,?,?,?)";

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private TextField taken;

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private AnchorPane p_pane ;

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
    private void handleAddAlbumButton() throws IOException {
        if (title.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //Title,Description,Privacy,Count,User_ID,Taken,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_INSERT_ALBUM);
                stmt.setString(1, title.getText());
                if (description.getText().isEmpty()) {
                    stmt.setNull(2, Types.VARCHAR);
                } else {
                    stmt.setString(2, description.getText());
                }
                //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                if (privacyBox.getValue().equals("OPEN")) {
                    stmt.setInt(3, 1);
                } else if (privacyBox.getValue().equals("CLOSED")) {
                    stmt.setInt(3, 2);
                } else if (privacyBox.getValue().equals("FRIEND")) {
                    stmt.setInt(3, 3);
                } else {
                    stmt.setInt(3, 4);
                }
                stmt.setInt(4, 0);
                stmt.setInt(5, 0);

                stmt.setInt(6, Integer.parseInt(myID));

                if (taken.getText().isEmpty()) {
                    stmt.setNull(7, Types.VARCHAR);
                } else {
                    stmt.setInt(7, Location.getLocID(conn,taken.getText()));
                }
                stmt.executeUpdate();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("album", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        privacyBox.setValue("OPEN");
        privacyBox.setItems(privacyList);
    }
}