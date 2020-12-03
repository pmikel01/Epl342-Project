package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowVideoController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Label title;

    @FXML
    private Label description;

    @FXML
    private Label length;

    @FXML
    private Label likes;

    private String id;
    private String video_id;
    private String myID;
    private Connection conn;
    private int album_id;

    public void initData(String id, String myID, String video_id, Connection conn, int album_id) {
        this.id = id;
        this.video_id = video_id;
        this.myID = myID;
        this.conn = conn;
        this.album_id = album_id;

        title.setText("");
        description.setText("");
        likes.setText("");
        length.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Title,Description,Length,Likes FROM VIDEO WHERE Vid_ID=?");
            stmt.setInt(1, Integer.parseInt(video_id));
            rs = stmt.executeQuery();
            if (rs.next()) {
                title.setText(rs.getString("Title"));
                if (rs.getString("Description") != null) {
                    description.setText(rs.getString("Description"));
                }
                double len = rs.getDouble("Length");
                if (len != 0) {
                    length.setText(len + "");
                }
                if (rs.getInt("Likes") != 0) {
                    likes.setText(rs.getInt("Likes") + "");
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (album_id != 0) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../Media/show_album.fxml"));
                Pane view = null;
                view = loader.load();
                //access the controller and call a method
                ShowAlbumController controller = loader.getController();
                //create query
                controller.initData(myID, myID, album_id+"", conn);

                p_pane.getChildren().setAll(view);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("video", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("video",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        }
    }

    @FXML
    private void handleLikeButton(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Likes FROM VIDEO WHERE Vid_ID=?");
            stmt.setInt(1, Integer.parseInt(video_id));
            rs = stmt.executeQuery();
            int likes = 0 ;
            if (rs.next()) {
                if (rs.getInt("Likes") != 0) {
                    likes = rs.getInt("Likes") ;
                }
            }
            stmt = null;
            rs = null;
            stmt = conn.prepareStatement("UPDATE VIDEO SET Likes=? WHERE Vid_ID=?");
            stmt.setInt(1, likes+1);
            stmt.setInt(2, Integer.parseInt(video_id));
            stmt.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("LIKE", "Congratulations you liked the video", "like");
        dialog.openDialog();
        initData(id,myID,video_id,conn,album_id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
