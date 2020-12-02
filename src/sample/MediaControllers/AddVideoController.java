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
    private static final String SQL_INSERT_VIDEO = "INSERT INTO [dbo].VIDEO (Title,Description,Length,Likes,User_ID,ChangeLog) VALUES (?,?,?,?,?,?)";

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private Spinner<Integer> length;

    @FXML
    private Label error_l;


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
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.prepareStatement(SQL_INSERT_VIDEO);
                stmt.setString(1, title.getText());
                if (description.getText().isEmpty()) {
                    stmt.setNull(2, Types.VARCHAR);
                } else {
                    stmt.setString(2, description.getText());
                }
                double len = Double.parseDouble(length.getValue().toString());
                stmt.setDouble(3,len);
                stmt.setInt(4,0);
                stmt.setInt(5, Integer.parseInt(myID));
                stmt.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
                stmt.executeUpdate();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        SpinnerValueFactory<Integer> CountLength = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99999,1);
        this.length.setValueFactory(CountLength);
    }
}