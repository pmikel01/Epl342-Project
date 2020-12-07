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
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;

public class EditVideoController implements Initializable {
    private static final String SQL_UPDATE_VIDEO = "UPDATE VIDEO SET Title=?,Source=?,Description=?,Length=? WHERE Vid_ID=?";

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

    private String mediaID;
    private String myID;
    private Connection conn;
    private int albumID;

    public void initData(String mediaID, String myID, Connection conn, int albumID) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;
        this.albumID = albumID;

        title.setText("");
        description.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Title,Source,Description,Length FROM VIDEO WHERE Vid_ID=?");
            stmt.setInt(1, Integer.parseInt(mediaID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                title.setText(rs.getString("Title"));

                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                sourcePath.setText(s + "\\src\\Videos\\"+rs.getString("Source"));

                if (rs.getString("Description") != null) {
                    description.setText(rs.getString("Description"));
                }
                double len = rs.getDouble("Length");
                if (len != 0) {
                    SpinnerValueFactory<Integer> CountL = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999999, (int) len);
                    this.length.setValueFactory(CountL);
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    private void handleUpdateVideoButton() throws IOException {
        if (albumID == 0) {
            if (title.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else if(sourcePath.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else{
                //Title=?,Source=?,Description=?,Length=?
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = conn.prepareStatement(SQL_UPDATE_VIDEO);
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

                    stmt.setInt(5, Integer.parseInt(mediaID));

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
        } else {
            if (title.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else if (sourcePath.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = conn.prepareStatement(SQL_UPDATE_VIDEO);
                    stmt.setString(1, title.getText());

                    int lastI = sourcePath.getText().lastIndexOf("\\");
                    String source_name = sourcePath.getText().substring(lastI + 1, sourcePath.getText().length());
                    stmt.setString(2, source_name);

                    if (description.getText().isEmpty()) {
                        stmt.setNull(3, Types.VARCHAR);
                    } else {
                        stmt.setString(3, description.getText());
                    }

                    double len = Double.parseDouble(length.getValue() + "");
                    stmt.setDouble(4, len);

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
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (albumID == 0) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> CountL = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999999,1);
        this.length.setValueFactory(CountL);
    }
}