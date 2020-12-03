package sample.MediaControllers;

import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ShowPictureController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Label likes;

    @FXML
    private Label height;

    @FXML
    private Label width;

    @FXML
    private Label location;

    @FXML
    private ImageView image;

    private String id;
    private String myID;
    private String photo_id;
    private Connection conn;
    private int album_id;

    public void initData(String id, String myID, String photo_id, Connection conn, int album_id) {
        this.id = id;
        this.photo_id = photo_id;
        this.myID = myID;
        this.conn = conn;
        this.album_id = album_id;

        image.setPreserveRatio(true);
        likes.setText("");
        width.setText("");
        height.setText("");
        location.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Source,Height,Width,Likes,Taken FROM PICTURE WHERE Pic_ID=?");
            stmt.setInt(1, Integer.parseInt(photo_id));
            rs = stmt.executeQuery();
            if (rs.next()) {
                String img = rs.getString("Source");
                image.setImage(new Image("Pictures/"+img));

                height.setText(rs.getInt("Height") + "");

                width.setText(rs.getInt("Width") + "");

                likes.setText(rs.getInt("Likes") + "");

                int loc_id = rs.getInt("Taken");
                String loc ="";
                stmt=null;
                rs=null;
                if (loc_id!=0) {
                    try {
                        stmt = conn.prepareStatement("SELECT Name FROM LOCATION WHERE Location_ID=?");
                        stmt.setInt(1, loc_id);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            loc = rs.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if(loc_id!=0) {
                    location.setText(loc);
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
                loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("picture", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/photos_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("picture",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        }
    }

    @FXML
    private void handleLikeButton(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Likes FROM PICTURE WHERE Pic_ID=?");
            stmt.setInt(1, Integer.parseInt(photo_id));
            rs = stmt.executeQuery();
            int likes = 0 ;
            if (rs.next()) {
                if (rs.getInt("Likes") != 0) {
                    likes = rs.getInt("Likes") ;
                }
            }
            stmt = null;
            rs = null;
            stmt = conn.prepareStatement("UPDATE PICTURE SET Likes=? WHERE Pic_ID=?");
            stmt.setInt(1, likes+1);
            stmt.setInt(2, Integer.parseInt(photo_id));
            stmt.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("LIKE", "Congratulations you liked the picture", "like");
        dialog.openDialog();
        initData(id,myID,photo_id,conn,album_id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
