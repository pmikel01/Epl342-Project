package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;

public class EditPhotoController implements Initializable {
    private static final String SQL_UPDATE_PICTURE = "UPDATE PICTURE SET Source=?,Height=?,Width=?,Taken=?,Privacy=? WHERE Pic_ID=?";

    @FXML
    private AnchorPane p_pane ;

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private TextField sourcePath;

    @FXML
    private TextField location;

    @FXML
    private Label error_l;

    private String mediaID;
    private String myID;
    private Connection conn;
    private int albumID;

    public void initData(String mediaID, String myID, Connection conn, int albumID) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;
        this.albumID = albumID;

        sourcePath.setText("");
        location.setText("");
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Source,Taken,Privacy FROM PICTURE WHERE Pic_ID=?");
            stmt.setInt(1, Integer.parseInt(mediaID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                sourcePath.setText(s + "\\src\\Pictures\\"+rs.getString("Source"));

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
    private void handleUpdatePhotoButton() throws IOException {
        if (albumID == 0) {
            if (sourcePath.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                //Source=?,Height=?,Width=?,Taken=?
                try {
                    stmt = conn.prepareStatement(SQL_UPDATE_PICTURE);

                    int lastI = sourcePath.getText().lastIndexOf("\\");
                    String source_name = sourcePath.getText().substring(lastI+1,sourcePath.getText().length());
                    stmt.setString(1, source_name);

                    FileInputStream pic = null;
                    try {
                        pic = new FileInputStream(sourcePath.getText());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

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

                    if (location.getText().isEmpty()) {
                        stmt.setNull(4, Types.VARCHAR);
                    } else {
                        stmt.setInt(4, Location.getLocID(conn,location.getText()));
                    }
                    stmt.setInt(5,Integer.parseInt(mediaID));

                    stmt.executeUpdate();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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
        } else {
            if (sourcePath.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else {
                PreparedStatement stmt = null;
                ResultSet rs = null;
                //Source=?,Height=?,Width=?,Taken=?
                try {
                    stmt = conn.prepareStatement(SQL_UPDATE_PICTURE);

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

                    if (location.getText().isEmpty()) {
                        stmt.setNull(4, Types.VARCHAR);
                    } else {
                        stmt.setInt(4, Location.getLocID(conn, location.getText()));
                    }
                    //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                    if (privacyBox.getValue().equals("OPEN")) {
                        stmt.setInt(5, 1);
                    } else if (privacyBox.getValue().equals("CLOSED")) {
                        stmt.setInt(5, 2);
                    } else if (privacyBox.getValue().equals("FRIEND")) {
                        stmt.setInt(5, 3);
                    } else {
                        stmt.setInt(5, 4);
                    }

                    stmt.setInt(6, Integer.parseInt(mediaID));

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
        privacyBox.setItems(privacyList);
    }
}