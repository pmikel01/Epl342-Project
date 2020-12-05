package sample.MainScenesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.ProfSelection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ShowProfController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> infoList;

    private String myID;
    private Connection conn;

    public void initData(String id, String myID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.conn = conn;

        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Birthday,Email,Website,Gender,Verified,BirthPlace,LivesIn FROM PROFILE WHERE ID=?");
            stmt.setInt(1, Integer.parseInt(id));
            rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("Name");
                Date bd = rs.getDate("Birthday");
                String email = rs.getString("Email");
                String web = rs.getString("Website");
                boolean gender = rs.getBoolean("Gender");
                boolean ver = rs.getBoolean("Verified");
                int birthPlaceID = rs.getInt("BirthPlace");
                int livesInID = rs.getInt("LivesIn");
                String birthPlace ="";
                String livesIn ="";

                PreparedStatement stmtL=null;
                ResultSet rsL=null;
                if (birthPlaceID!=0) {
                    try {
                        stmtL = conn.prepareStatement("SELECT Name FROM LOCATION WHERE Location_ID=?");
                        stmtL.setInt(1, birthPlaceID);
                        rsL = stmtL.executeQuery();
                        if (rsL.next()) {
                            birthPlace = rsL.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                stmtL=null;
                rsL=null;
                if (livesInID!=0) {
                    try {
                        stmtL = conn.prepareStatement("SELECT Name FROM LOCATION WHERE Location_ID=?");
                        stmtL.setInt(1, livesInID);
                        rsL = stmtL.executeQuery();
                        if (rsL.next()) {
                            livesIn = rsL.getString("Name");
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                infoList.setItems(items);
                //loop
                items.add(    "Name:        " + name);
                if(bd!=null) {
                    items.add("Birthday:    " + bd.toString());
                }
                if(email!=null) {
                    items.add("Email:         " + email);
                }
                if(web!=null) {
                    items.add("Website:     " + web);
                }
                if(!gender) {
                    items.add("Gender:      " + "Male");
                } else {
                    items.add("Gender:      " + "Female");
                }
                if(birthPlaceID!=0) {
                    items.add("Birthplace:  " + birthPlace);
                }
                if(livesInID!=0) {
                    items.add("Lives In:      " + livesIn);
                }
                if(!ver) {
                    items.add("Not Verified");
                } else {
                    items.add("Verified");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("album",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/photos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("picture",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("video",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/events_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("event",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/links_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("link",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSendFRButton() throws IOException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (!id.equals(myID)) {
            try {
                stmt = conn.prepareStatement("SELECT USER_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                stmt.setInt(1,Integer.parseInt(myID));
                stmt.setInt(2, Integer.parseInt(id));
                rs = stmt.executeQuery();
                if (rs.next()) {
                    sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("FRIEND", "This user is Already friend with you", "like");
                    dialog.openDialog();
                } else {
                    stmt = null;
                    rs = null;
                    stmt = conn.prepareStatement("SELECT RESPONCE FROM FRIEND_REQUESTS WHERE SENT_ID=? AND RECEIVE_ID=?");
                    stmt.setInt(1,Integer.parseInt(myID));
                    stmt.setInt(2, Integer.parseInt(id));
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("FRIEND", "You have already sent friend request", "like");
                        dialog.openDialog();
                    } else {
                        stmt = null;
                        rs = null;
                        stmt = conn.prepareStatement("SELECT RESPONCE FROM FRIEND_REQUESTS WHERE SENT_ID=? AND RECEIVE_ID=?");
                        stmt.setInt(1, Integer.parseInt(id));
                        stmt.setInt(2,Integer.parseInt(myID));
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("FRIEND", "This user is in your ignored list", "like");
                            dialog.openDialog();
                        }else {
                            stmt=null;
                            stmt = conn.prepareStatement("INSERT INTO [dbo].FRIEND_REQUESTS (SENT_ID,RECEIVE_ID,RESPONCE) VALUES (?,?,?)");
                            stmt.setInt(1,Integer.parseInt(myID));
                            stmt.setInt(2,Integer.parseInt(id));
                            stmt.setNull(3,Types.INTEGER);
                            stmt.executeUpdate();

                            sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("FRIEND", "Friend Request sent", "like");
                            dialog.openDialog();
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEducationButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/education_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("education",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/interests_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("interest",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/quotes_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("quote",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/work_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MediaListController controller = loader.getController();

        //create query
        controller.initData("work",id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
