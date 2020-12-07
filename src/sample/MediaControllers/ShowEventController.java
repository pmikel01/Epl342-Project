package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.EventSearchController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ShowEventController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label startDate;

    @FXML
    private Label endDate;

    @FXML
    private Label location;

    @FXML
    private Label venue;

    @FXML
    private Label privacy;

    @FXML
    private Label e_id;

    private String id;
    private String myID;
    private String event_id;
    private String choose;
    private Connection conn;

    private SearchEvents events=null;


    public void initData(String id, String myID, String event_id, Connection conn) {
        this.id = id;
        this.event_id = event_id;
        this.myID = myID;
        this.conn = conn;
        events=null;

        name.setText("");
        description.setText("");
        startDate.setText("");
        endDate.setText("");
        privacy.setText("");
        venue.setText("");
        location.setText("");
        e_id.setText(event_id);
        show();
    }

    public void initData(String id, String myID, String event_id, SearchEvents events, Connection conn) {
        this.id = id;
        this.event_id = event_id;
        this.myID = myID;
        this.events = events;
        this.conn = conn;

        name.setText("");
        description.setText("");
        startDate.setText("");
        endDate.setText("");
        privacy.setText("");
        venue.setText("");
        location.setText("");
        e_id.setText(event_id);
        show();
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Description,StartTime,EndTime,Privacy,Venue,Location FROM EVENT WHERE Event_ID=?");
            stmt.setInt(1, Integer.parseInt(event_id));
            rs = stmt.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("Name"));
                if (rs.getString("Description") != null) {
                    description.setText(rs.getString("Description"));
                }

                startDate.setText(rs.getTimestamp("StartTime").toString());
                endDate.setText(rs.getTimestamp("EndTime").toString());

                //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                if (rs.getInt("Privacy") == 1) {
                    privacy.setText("OPEN");
                } else if (rs.getInt("Privacy") == 2) {
                    privacy.setText("CLOSED");
                } else if (rs.getInt("Privacy") == 3) {
                    privacy.setText("FRIEND");
                } else {
                    privacy.setText("NETWORK");
                }

                if (rs.getString("Venue") != null) {
                    venue.setText(rs.getString("Name"));
                }

                int loc_id = rs.getInt("Location");
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
    private void handleBackButton() throws IOException {
        if (events != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EventSearchController controller = loader.getController();

            //create query
            controller.initData(events, myID, conn);

            p_pane.getChildren().setAll(view);
        } else {
            if (myID.equals(id)) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
                Pane showProfParent = null;
                showProfParent = loader.load();
                //access the controller and call a method
                EditMediaListController controller = loader.getController();

                //create query
                controller.initData("event", myID, conn);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/events_list.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MediaListController controller = loader.getController();

                //create query
                controller.initData("event",id, myID, conn);

                p_pane.getChildren().setAll(view);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
