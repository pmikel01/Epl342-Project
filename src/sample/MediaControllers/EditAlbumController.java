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
import sample.Main.FxmlLoader;
import sample.Main.Location;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditAlbumController implements Initializable {

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private TextField location;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Label error_l;

    private String mediaID;
    private String myID;
    private Connection conn;

    public void initData(String mediaID, String myID, Connection conn) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;

        name.setText("");
        description.setText("");
        location.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Title,Desciption,Taken,Privacy FROM ALBUM WHERE Album_ID=?");
            stmt.setInt(1, Integer.parseInt(mediaID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("Title"));

                if (rs.getString("Desciption") != null) {
                    description.setText(rs.getString("Desciption"));
                }

                int loc_id = rs.getInt("Taken");
                String loc ="";
                PreparedStatement stmt2=null;
                ResultSet rs2=null;
                if (loc_id!=0) {
                    try {
                        stmt2 = conn.prepareStatement("SELECT Name FROM LOCATION WHERE Location_ID=?");
                        stmt2.setInt(1, loc_id);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            loc = rs2.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if(loc_id!=0) {
                    location.setText(loc);
                }

                //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                if (rs.getInt("Privacy") == 1) {
                    privacyBox.setValue("OPEN");
                } else if (rs.getInt("Privacy") == 2) {
                    privacyBox.setValue("CLOSED");
                } else if (rs.getInt("Privacy") == 3) {
                    privacyBox.setValue("FRIEND");
                } else {
                    privacyBox.setValue("NETWORK");
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleAddPictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_photo_in_album.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddPhotoInAlbumController controller = loader.getController();

        //create query
        controller.initData(myID, mediaID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_video_in_album.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddVideoInAlbumController controller = loader.getController();

        //create query
        controller.initData(myID, mediaID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleUpdateAlbumButton() throws IOException {
        if (name.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            try {
                PreparedStatement stmt=null;
                stmt = conn.prepareStatement("UPDATE ALBUM SET Title=?,Description=?,Taken=?,Privacy=? WHERE Album_ID=?");
                stmt.setString(1, name.getText());
                if (description.getText().isEmpty()) {
                    stmt.setNull(2, Types.VARCHAR);
                } else {
                    stmt.setString(2, description.getText());
                }
                if (location.getText().isEmpty()) {
                    stmt.setNull(3, Types.VARCHAR);
                } else {
                    stmt.setInt(3, Location.getLocID(conn,location.getText()));
                }

                //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                if (privacyBox.getValue().equals("OPEN")) {
                    stmt.setInt(4, 1);
                } else if (privacyBox.getValue().equals("CLOSED")) {
                    stmt.setInt(4, 2);
                } else if (privacyBox.getValue().equals("FRIEND")) {
                    stmt.setInt(4, 3);
                } else {
                    stmt.setInt(4, 4);
                }
                stmt.setInt(5, Integer.parseInt(mediaID));
                stmt.executeUpdate();
            } catch (SQLException throwables) {
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