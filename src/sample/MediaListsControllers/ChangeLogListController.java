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
import sample.MainScenesControllers.ShowProfController;
import sample.MediaControllers.*;
import sample.SearchMediaControllers.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ChangeLogListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private ArrayList<Timestamp> logs = new ArrayList<Timestamp>();
    private ArrayList<String> logs_names = new ArrayList<String>();

    private String choose;
    private String myID;
    private Connection conn;

    public int getNewerPos(ArrayList<Timestamp> list){
        Timestamp max = java.sql.Timestamp.valueOf("1700-01-01 01:01:01.0");
        int max_pos=0;
        for(int i=0; i<list.size(); i++){
            //mytime.after(fromtime) && mytime.before(totime)
            if(list.get(i).after(max)){
                max = list.get(i);
                max_pos = i;
            }
        }
        return max_pos;
    }

    public void initData(String choose, String myID, Connection conn, int count) {
        this.choose = choose;
        this.myID = myID;
        this.conn = conn;

        if (choose.equals("Albums")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM ALBUM WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                int log_size=0;
                if (rs.next()) {
                    log_size = rs.getInt(1);
                }
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Title,ChangeLog FROM ALBUM WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add(rs.getString("Title"));
                    }
                }
                for(int i=0 ; i<log_size ;i++) {
                    int select = getNewerPos(logs);
                    String line = "Album: " + logs_names.get(select) + " :  " + logs.get(select).toString();
                    items.add(line);
                    logs.remove(select);
                    logs_names.remove(select);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("Pictures")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM PICTURE WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                int log_size=0;
                if (rs.next()) {
                    log_size = rs.getInt(1);
                }
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Pic_ID,ChangeLog FROM PICTURE WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add(rs.getInt("Pic_ID")+"");
                    }
                }
                for(int i=0 ; i<log_size ;i++) {
                    int select = getNewerPos(logs);
                    String line = "Picture: " + logs_names.get(select) + " :  " + logs.get(select).toString();
                    items.add(line);
                    logs.remove(select);
                    logs_names.remove(select);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("Videos")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM VIDEO WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                int log_size=0;
                if (rs.next()) {
                    log_size = rs.getInt(1);
                }
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Title,ChangeLog FROM VIDEO WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add(rs.getString("Title"));
                    }
                }
                for(int i=0 ; i<log_size ;i++) {
                    int select = getNewerPos(logs);
                    String line = "Video: " + logs_names.get(select) + " :  " + logs.get(select).toString();
                    items.add(line);
                    logs.remove(select);
                    logs_names.remove(select);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("Events")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM EVENT WHERE Creator=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                int log_size=0;
                if (rs.next()) {
                    log_size = rs.getInt(1);
                }
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Name,ChangeLog FROM EVENT WHERE Creator=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add(rs.getString("Name"));
                    }
                }
                for(int i=0 ; i<log_size ;i++) {
                    int select = getNewerPos(logs);
                    String line = "Event: " + logs_names.get(select) + " :  " + logs.get(select).toString();
                    items.add(line);
                    logs.remove(select);
                    logs_names.remove(select);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("Links")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt = conn.prepareStatement("SELECT COUNT(*) FROM LINK WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                int log_size=0;
                if (rs.next()) {
                    log_size = rs.getInt(1);
                }
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Name,ChangeLog FROM LINK WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add(rs.getString("Name"));
                    }
                }
                for(int i=0 ; i<log_size ;i++) {
                    int select = getNewerPos(logs);
                    String line = "Link: " + logs_names.get(select) + " :  " + logs.get(select).toString();
                    items.add(line);
                    logs.remove(select);
                    logs_names.remove(select);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("all")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            logs.clear();
            logs_names.clear();
            try {
                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Title,ChangeLog FROM ALBUM WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add("Album: " + rs.getString("Title"));
                    }
                }

                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Pic_ID,ChangeLog FROM PICTURE WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add("Picture: " + rs.getInt("Pic_ID")+"");
                    }
                }

                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Title,ChangeLog FROM VIDEO WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add("Video: " + rs.getString("Title"));
                    }
                }

                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Name,ChangeLog FROM EVENT WHERE Creator=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add("Event: " + rs.getString("Name"));
                    }
                }

                stmt=null;
                rs=null;
                stmt = conn.prepareStatement("SELECT Name,ChangeLog FROM LINK WHERE User_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                for(int i=0 ; i<count ;i++) {
                    if (rs.next()) {
                        logs.add(rs.getTimestamp("ChangeLog"));
                        logs_names.add("Link: " + rs.getString("Name"));
                    }
                }

                for(int i=0 ; i<count ;i++) {
                    if (!logs.isEmpty()) {
                        int select = getNewerPos(logs);
                        String line = logs_names.get(select) + " :  " + logs.get(select).toString();
                        items.add(line);
                        logs.remove(select);
                        logs_names.remove(select);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}