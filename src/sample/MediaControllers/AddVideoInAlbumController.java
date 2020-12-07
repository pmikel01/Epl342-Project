package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import sample.MediaListsControllers.EditMediaListController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddVideoInAlbumController implements Initializable {
    private static final String SQL_INSERT_VIDEO = "INSERT INTO [dbo].VIDEO (Title,Source,Description,Length,Likes,User_ID) VALUES (?,?,?,?,?,?)";
    private static final String SQL_INSERT_VIDEO_ALBUM = "INSERT INTO [dbo].VIDEOS_ALBUMS (ALBUM_ID,VIDEO_ID) VALUES (?,?)";

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private Label error_l;

    @FXML
    private Spinner<Integer> length;


    private String myID;
    private String albumID;
    private Connection conn;

    public void initData(String myID, String albumID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
        this.albumID = albumID;
    }

    @FXML
    private void handleBrowseVideoButton() {
        FileChooser fileC = new FileChooser();
        fileC.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mp3", "*.wmv", "*.mov", "*.flv", "*.mkv")
            );

        File selectedF = fileC.showOpenDialog(null);

        if (selectedF != null) {
            sourcePath.setText(selectedF.getPath());
        } else {
            System.out.println("NOt");
        }
    }

    @FXML
    private void handleAddVideoButton() throws IOException {
        //Title,Description,Length,Likes,User_ID
        if (title.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else if(sourcePath.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else{
            PreparedStatement stmt = null;
            try {
                stmt = conn.prepareStatement(SQL_INSERT_VIDEO,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, title.getText());

                int lastI = sourcePath.getText().lastIndexOf("\\");
                String source_name = sourcePath.getText().substring(lastI+1,sourcePath.getText().length());
                stmt.setString(2, source_name);

                if (description.getText().isEmpty()) {
                    stmt.setNull(3, Types.VARCHAR);
                } else {
                    stmt.setString(3, description.getText());
                }

                double len = Double.parseDouble(length.getValue() + "");
                stmt.setDouble(4, len);

                stmt.setInt(5,0);
                stmt.setInt(6, Integer.parseInt(myID));
                stmt.executeUpdate();

                int id_created=0;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id_created = (int) generatedKeys.getLong(1);
                    }
                }

                stmt = null;
                ResultSet rs=null;
                stmt = conn.prepareStatement(SQL_INSERT_VIDEO_ALBUM);
                stmt.setInt(1,Integer.parseInt(albumID));
                stmt.setInt(2,id_created);
                stmt.executeUpdate();

                stmt = null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Count_Videos FROM ALBUM WHERE Album_ID=?");
                stmt.setInt(1, Integer.parseInt(albumID));
                rs = stmt.executeQuery();
                int count2 = 2 ;
                if (rs.next()) {
                    count2 = rs.getInt("Count_Videos");
                }

                stmt = null;
                stmt = conn.prepareStatement("UPDATE ALBUM SET Count_Videos=? WHERE Album_ID=?");
                stmt.setInt(1,count2+1);
                stmt.setInt(2,Integer.parseInt(albumID));
                stmt.executeUpdate();
            }catch (SQLException throwables) {
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
        SpinnerValueFactory<Integer> CountL = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999999,1);
        this.length.setValueFactory(CountL);
    }
}