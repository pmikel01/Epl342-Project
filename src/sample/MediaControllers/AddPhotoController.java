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

public class AddPhotoController implements Initializable {
    private static final String SQL_INSERT_PICTURE = "INSERT INTO [dbo].PICTURE (Source,Height,Width,User_ID,Link,Likes,Taken,ChangeLog) VALUES (?,?,?,?,?,?,?,?)";

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField sourcePath;

    @FXML
    private TextField location;

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
    private void handleBrowsePhotoButton() {
        if (sourcePath.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //Source,Height,Width,User_ID,Link,Likes,Taken,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_INSERT_PICTURE);

                FileInputStream pic = null;
                try {
                    pic = new FileInputStream(sourcePath.getText());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                stmt.setBinaryStream(1, pic);

                BufferedImage bimg = null;
                int width=0;
                int height=0;
                try {
                    assert pic != null;
                    bimg = ImageIO.read(pic);
                    width = bimg.getWidth();
                    height = bimg.getHeight();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                stmt.setInt(2,height);
                stmt.setInt(3,width);

                stmt.setInt(4, Integer.parseInt(myID));

                stmt.setNull(5, Types.INTEGER);

                stmt.setInt(6, 0);

                if (location.getText().isEmpty()) {
                    stmt.setNull(7, Types.VARCHAR);
                } else {
                    stmt.setInt(7, Location.getLocID(conn,location.getText()));
                }

                stmt.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now()));
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("picture", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("picture", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}