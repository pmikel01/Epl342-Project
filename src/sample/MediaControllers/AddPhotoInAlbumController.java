package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import sample.Main.FxmlLoader;
import sample.Main.Location;
import sample.MediaListsControllers.EditMediaListController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddPhotoInAlbumController implements Initializable {
    private static final String SQL_INSERT_PICTURE = "INSERT INTO [dbo].PICTURE (Source,Height,Width,User_ID,Link,Likes,Taken) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_PICTURE_ALBUM = "INSERT INTO [dbo].PICTURES_ALBUMS (ALBUM_ID,PICTURE_ID) VALUES (?,?)";

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    @FXML
    private TextField location;

    @FXML
    private Label error_l;

    private String myID;
    private String albumID;
    private Connection conn;

    public void initData(String myID, String albumID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
        this.albumID = albumID;
        error_l.setTextFill(Color.web("#D8D9D9"));
    }

    @FXML
    private void handleBrowsePhotoButton() {
        FileChooser fileC = new FileChooser();
            fileC.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

        File selectedF = fileC.showOpenDialog(null);

        if (selectedF != null) {
            sourcePath.setText(selectedF.getPath());
        } else {
            System.out.println("NOt");
        }
    }

    @FXML
    private void handleAddPhotoButton() throws IOException {
        if (sourcePath.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            //Source,Height,Width,User_ID,Link,Likes,Taken,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_INSERT_PICTURE,Statement.RETURN_GENERATED_KEYS);

                int lastI = sourcePath.getText().lastIndexOf("\\");
                String source_name = sourcePath.getText().substring(lastI + 1, sourcePath.getText().length());
                stmt.setString(1, source_name);

                FileInputStream pic = null;
                try {
                    pic = new FileInputStream(sourcePath.getText());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                BufferedImage bimg = null;
                int width = 0;
                int height = 0;
                try {
                    assert pic != null;
                    bimg = ImageIO.read(pic);
                    width = bimg.getWidth();
                    height = bimg.getHeight();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stmt.setInt(2, height);
                stmt.setInt(3, width);

                stmt.setInt(4, Integer.parseInt(myID));

                stmt.setNull(5, Types.INTEGER);

                stmt.setInt(6, 0);

                if (location.getText().isEmpty()) {
                    stmt.setNull(7, Types.VARCHAR);
                } else {
                    stmt.setInt(7, Location.getLocID(conn, location.getText()));
                }

                stmt.executeUpdate();

                int id_created=0;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id_created = (int) generatedKeys.getLong(1);
                    }
                }

                stmt = null;
                ResultSet rs = null;

                stmt = conn.prepareStatement(SQL_INSERT_PICTURE_ALBUM);
                stmt.setInt(1,Integer.parseInt(albumID));
                stmt.setInt(2,id_created);
                stmt.executeUpdate();

                stmt = null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Count FROM ALBUM WHERE Album_ID=?");
                stmt.setInt(1, Integer.parseInt(albumID));
                rs = stmt.executeQuery();
                int count2 = 2 ;
                if (rs.next()) {
                    count2 = rs.getInt("Count");
                }

                stmt = null;
                stmt = conn.prepareStatement("UPDATE ALBUM SET Count=? WHERE Album_ID=?");
                stmt.setInt(1,count2+1);
                stmt.setInt(2,Integer.parseInt(albumID));
                stmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("album", myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/edit_album.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditAlbumController controller = loader.getController();

        //create query
        controller.initData(albumID, myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}