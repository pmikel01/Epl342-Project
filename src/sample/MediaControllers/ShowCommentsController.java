package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowCommentsController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String choose;
    private String mediaID;
    private Connection conn;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> listV;

    public void initData(String id, String myID, String choose, String mediaID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.choose = choose;
        this.mediaID = mediaID;
        this.conn = conn;

        if (choose.equals("video")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT COMMENT_ID,USER_ID FROM PROFILES_COMMENTS WHERE VIDEO_ID=?");
                stmt.setInt(1, Integer.parseInt(mediaID));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int user_id=rs.getInt("USER_ID");
                    int comm_id=rs.getInt("COMMENT_ID");;

                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;

                    stmt2=null;
                    rs2=null;
                    String comm = "";
                    try {
                        stmt2 = conn.prepareStatement("SELECT Comment_Text FROM COMMENT WHERE Comment_ID=?");
                        stmt2.setInt(1, comm_id);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            comm = rs2.getString("Comment_Text");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    stmt2=null;
                    rs2=null;
                    String name = "";
                    try {
                        stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                        stmt2.setInt(1, user_id);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            name = rs2.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    String line = name + ":  " + comm;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT COMMENT_ID,USER_ID FROM PROFILES_COMMENTS WHERE ALBUM_ID=?");
                stmt.setInt(1, Integer.parseInt(mediaID));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    int user_id=0;
                    int comm_id=0;
                    try {
                        stmt = conn.prepareStatement("SELECT COMMENT_ID,USER_ID FROM PROFILES_COMMENTS WHERE VIDEO_ID=?");
                        stmt.setInt(1, Integer.parseInt(mediaID));
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            user_id = rs.getInt("USER_ID");
                            comm_id = rs.getInt("COMMENT_ID");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    stmt2=null;
                    rs2=null;
                    String comm = "";
                    try {
                        stmt = conn.prepareStatement("SELECT Comment_Text FROM COMMENT WHERE Comment_ID=?");
                        stmt.setInt(1, comm_id);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            comm = rs.getString("Comment_Text");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    stmt2=null;
                    rs2=null;
                    String name = "";
                    try {
                        stmt = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                        stmt.setInt(1, user_id);
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            name = rs.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    String line = name + ":  " + comm;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        listV.setItems(items);
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (choose.equals("album")) {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("album", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("album",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        } else {
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
    public void pressAddButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("Make A Comment");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Comment: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0) {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //Source=?,Height=?,Width=?,Taken=?
            try {
                stmt = conn.prepareStatement("INSERT INTO [dbo].COMMENT (Comment_Text) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1,input.getText());
                stmt.executeUpdate();

                int id_created=0;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id_created = (int) generatedKeys.getLong(1);
                    }
                }

                if (choose.equals("video")) {
                    stmt=null;
                    stmt = conn.prepareStatement("INSERT INTO [dbo].PROFILES_COMMENTS (COMMENT_ID,USER_ID,VIDEO_ID) VALUES (?,?,?)");
                    stmt.setInt(1,id_created);
                    stmt.setInt(2,Integer.parseInt(myID));
                    stmt.setInt(3,Integer.parseInt(mediaID));
                    stmt.executeUpdate();
                    initData(id,myID,"video",mediaID,conn);
                } else {
                    stmt=null;
                    stmt = conn.prepareStatement("INSERT INTO [dbo].PROFILES_COMMENTS (COMMENT_ID,USER_ID,ALBUM_ID) VALUES (?,?,?)");
                    stmt.setInt(1,id_created);
                    stmt.setInt(2,Integer.parseInt(myID));
                    stmt.setInt(3,Integer.parseInt(mediaID));
                    stmt.executeUpdate();
                    initData(id,myID,"album",mediaID,conn);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            System.out.println("");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
