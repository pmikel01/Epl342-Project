package sample.MainScenesControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Main.FxmlLoader;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MyProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;

        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Birthday,Email,Website,Gender,Verified,BirthPlace,LivesIn FROM PROFILE WHERE ID=?");
            stmt.setInt(1, Integer.parseInt(myID));
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

                listV.setItems(items);
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
    private void handleChangelogButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/changelog.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        ChangelogController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAlbumButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("album",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePictureButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_photos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("picture",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleVideoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("video",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSpecEventButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("link",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleFriendsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_friends_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("friend",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEditButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/edit_information.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditInfoController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleEducationButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_education_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("education",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleInterestsButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_interests_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("interest",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleQuotesButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_quotes_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("quote",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleWorkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_work_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("work",myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
