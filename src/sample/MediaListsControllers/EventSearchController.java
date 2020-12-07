package sample.MediaListsControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.MediaControllers.ShowEventController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EventSearchController implements Initializable {
    @FXML
    private AnchorPane p_pane;

    private String choose;
    private String myID;
    private Connection conn;

    private SearchEvents events;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listV;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
        items = FXCollections.observableArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
//            stmt = conn.prepareStatement("SELECT EVENT_ID,COUNT (USER_ID) as [Interested] FROM [INTERESTED-IN_EVENT] WHERE INTEREST_RANK!=2 GROUP BY EVENT_ID ORDER BY [Interested] ASC");
            CallableStatement stmt5 = conn.prepareCall("{call Procedure_Least_Events}");
            rs = stmt5.executeQuery();
            int i=0;
            while (rs.next() && i<15) {
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;
                stmt2 = conn.prepareStatement("SELECT Name,Privacy,Creator FROM EVENT WHERE Event_ID=?");
                stmt2.setInt(1,rs.getInt("EVENT_ID"));
                rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    int event_id = rs.getInt("EVENT_ID");
                    String name = rs2.getString("Name");
                    String line = event_id + "  " + name + rs.getInt(2);
                    if (rs2.getInt("Creator") == Integer.parseInt(myID)) {
                        items.add(line);
                    } else if (rs2.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs2.getInt("Privacy") == 3) {
                        PreparedStatement stmt3 =null;
                        ResultSet rs3=null;
                        stmt3 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt3.setInt(1, Integer.parseInt(myID));
                        stmt3.setInt(2, rs2.getInt("Creator"));
                        rs3 = stmt3.executeQuery();
                        if (rs3.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs3=null;
                        CallableStatement stmt3 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt3.setInt(1,Integer.parseInt(myID));
                        rs3 = stmt3.executeQuery();
                        while (rs3.next()) {
                            int possible = rs3.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs2.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listV.setItems(items);

        listV.setCellFactory(param -> new EventSearchController.XCell(p_pane, myID, events, conn));
    }

    public void initData(SearchEvents events, String myID, Connection conn) {
        this.events = events;
        this.myID = myID;

        if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                stmt.setInt(3,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(4, dt);
                stmt.setInt(5,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                stmt.setInt(3,loc_id);
                stmt.setInt(4,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                stmt.setInt(4,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setInt(2,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                stmt.setInt(4,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getVenue());
                stmt.setInt(2,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                stmt.setInt(4,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE Location=? AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setInt(1,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getName());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE AND SOUNDEX(Venue)=SOUNDEX(?) AND CAST(StartTime as DATE)=? AND Creator=?");
                stmt.setString(1, events.getVenue());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND Creator=?");
                stmt.setString(1, events.getVenue());
                stmt.setInt(2,loc_id);
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND Location=? AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setInt(2,loc_id);
                stmt.setInt(3,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE CAST(StartTime as DATE)=? AND Creator=?");
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(1, dt);
                stmt.setInt(2,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND Creator=?");
                stmt.setString(1, events.getName());
                stmt.setInt(2,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?) AND Creator=?");
                stmt.setString(1, events.getVenue());
                stmt.setInt(2,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE Location=? AND Creator=?");
                stmt.setInt(1,loc_id);
                stmt.setInt(2,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && !events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE Creator=?");
                stmt.setInt(1,Integer.parseInt(events.getId()));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                stmt.setInt(3,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(4, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND Location=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                stmt.setInt(3,loc_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?) AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getName());
                stmt.setInt(2,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?) AND Location=? AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getVenue());
                stmt.setInt(2,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(3, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE Location=? AND CAST(StartTime as DATE)=?");
                stmt.setInt(1,loc_id);
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getName());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Venue)=SOUNDEX(?)");
                stmt.setString(1, events.getName());
                stmt.setString(2, events.getVenue());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE AND SOUNDEX(Venue)=SOUNDEX(?) AND CAST(StartTime as DATE)=?");
                stmt.setString(1, events.getVenue());
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(2, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?) AND Location=?");
                stmt.setString(1, events.getVenue());
                stmt.setInt(2,loc_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?) AND Location=?");
                stmt.setString(1, events.getName());
                stmt.setInt(2,loc_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()!=null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE CAST(StartTime as DATE)=?");
                Date dt = Date.valueOf(events.getDate().getValue());
                stmt.setDate(1, dt);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmt.setString(1, events.getName());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && !events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE SOUNDEX(Venue)=SOUNDEX(?)");
                stmt.setString(1, events.getVenue());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && !events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,events.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT WHERE Location=?");
                stmt.setInt(1,loc_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (events.getName().isEmpty() && events.getVenue().isEmpty() && events.getLocation().isEmpty() && events.getDate().getValue()==null && events.getId().isEmpty()){
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name,Privacy,Creator FROM EVENT");
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    if (events.getId().equals(myID)) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, rs.getInt("Creator"));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==rs.getInt("Creator")) {
                                items.add(line);
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }
        listV.setCellFactory(param -> new EventSearchController.XCell(p_pane, myID, events, conn));
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Event");
        Pane pane2 = new Pane();
        Button button2 = new Button("Going");
        Pane pane3 = new Pane();
        Button button3 = new Button("Interested");
        Pane pane4 = new Pane();
        Button button4 = new Button("Not Going");
        Pane pane5 = new Pane();
        Button button5 = new Button("Interested Users");

        public XCell(AnchorPane p_pane, String myID, SearchEvents events, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            button4.setCursor(Cursor.HAND);
            button5.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane5, button5, pane, button, pane2, button2, pane3, button3, pane4, button4);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane5, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_event.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowEventController controller = loader.getController();

                        //String id, String myID, String event_id, SearchEvents events, Connection conn
                        controller.initData(myID, myID, firstWord(getItem()), events, conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        stmt = conn.prepareStatement("SELECT EVENT_ID FROM [INTERESTED-IN_EVENT] WHERE USER_ID=? AND EVENT_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("UPDATE [INTERESTED-IN_EVENT] SET INTEREST_RANK=? WHERE USER_ID=? AND EVENT_ID=?");
                            stmt.setInt(1, 1);
                            stmt.setInt(2, Integer.parseInt(myID));
                            stmt.setInt(3, Integer.parseInt(firstWord(getItem())));
                            stmt.executeUpdate();
                        } else {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("INSERT INTO [dbo].[INTERESTED-IN_EVENT] (USER_ID,EVENT_ID,INTEREST_RANK) VALUES (?,?,?)");
                            stmt.setInt(1, Integer.parseInt(myID));
                            stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                            stmt.setInt(3, 1);
                            stmt.executeUpdate();
                        }
                        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("EVENT", "You are Going to that Event", "like");
                        dialog.openDialog();
                    }catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        stmt = conn.prepareStatement("SELECT EVENT_ID FROM [INTERESTED-IN_EVENT] WHERE USER_ID=? AND EVENT_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("UPDATE [INTERESTED-IN_EVENT] SET INTEREST_RANK=? WHERE USER_ID=? AND EVENT_ID=?");
                            stmt.setInt(1, 3);
                            stmt.setInt(2, Integer.parseInt(myID));
                            stmt.setInt(3, Integer.parseInt(firstWord(getItem())));
                            stmt.executeUpdate();
                        } else {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("INSERT INTO [dbo].[INTERESTED-IN_EVENT] (USER_ID,EVENT_ID,INTEREST_RANK) VALUES (?,?,?)");
                            stmt.setInt(1, Integer.parseInt(myID));
                            stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                            stmt.setInt(3, 3);
                            stmt.executeUpdate();
                        }
                        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("EVENT", "You are Interested in that Event", "like");
                        dialog.openDialog();
                    }catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            button4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        PreparedStatement stmt = null;
                        ResultSet rs = null;
                        stmt = conn.prepareStatement("SELECT EVENT_ID FROM [INTERESTED-IN_EVENT] WHERE USER_ID=? AND EVENT_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        rs = stmt.executeQuery();
                        if (rs.next()) {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("UPDATE [INTERESTED-IN_EVENT] SET INTEREST_RANK=? WHERE USER_ID=? AND EVENT_ID=?");
                            stmt.setInt(1, 2);
                            stmt.setInt(2, Integer.parseInt(myID));
                            stmt.setInt(3, Integer.parseInt(firstWord(getItem())));
                            stmt.executeUpdate();
                        } else {
                            stmt = null;
                            rs = null;
                            stmt = conn.prepareStatement("INSERT INTO [dbo].[INTERESTED-IN_EVENT] (USER_ID,EVENT_ID,INTEREST_RANK) VALUES (?,?,?)");
                            stmt.setInt(1, Integer.parseInt(myID));
                            stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                            stmt.setInt(3, 2);
                            stmt.executeUpdate();
                        }
                        sample.Main.CustomDialog dialog = new sample.Main.CustomDialog("EVENT", "You are not Going to that Event", "like");
                        dialog.openDialog();
                    }catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            button5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../MediaLists/interested_list.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EventInterestedController controller = loader.getController();

                        //Integer.parseInt(firstWord(getItem()))
                        controller.initData(myID, conn, Integer.parseInt(firstWord(getItem())), events);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item);
                setGraphic(hbox);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
