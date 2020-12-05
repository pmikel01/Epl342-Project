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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.w3c.dom.Text;
import sample.Main.FxmlLoader;
import sample.Main.Location;
import sample.MediaListsControllers.EditMediaListController;
import sample.SearchMediaControllers.SearchAlbumController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddVideoController implements Initializable {
    private static final String SQL_INSERT_VIDEO = "INSERT INTO [dbo].VIDEO (Title,Source,Description,Length,Likes,User_ID) VALUES (?,?,?,?,?,?)";

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
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
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
            ResultSet rs = null;
            try {
                stmt = conn.prepareStatement(SQL_INSERT_VIDEO);
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
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("video", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        }
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("video", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> CountL = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999999,1);
        this.length.setValueFactory(CountL);
    }
}