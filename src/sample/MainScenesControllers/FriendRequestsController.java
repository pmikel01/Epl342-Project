package sample.MainScenesControllers;

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
import javafx.scene.media.MediaException;
import sample.MediaControllers.EditAlbumController;
import sample.MediaControllers.ShowAlbumController;
import sample.MediaControllers.ShowCommentsController;
import sample.MediaListsControllers.EditMediaListController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FriendRequestsController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    @FXML
    private ListView<String> listV2 ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private ObservableList<String> items2 = FXCollections.observableArrayList();

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;

        items = FXCollections.observableArrayList();
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT SENT_ID,RESPONCE FROM FRIEND_REQUESTS WHERE RECEIVE_ID=?");
            stmt.setInt(1, Integer.parseInt(myID));
            rs = stmt.executeQuery();
            while (rs.next()) {
                int resp = rs.getInt("RESPONCE");
                if (resp==0) {
                    int id = rs.getInt("SENT_ID");
                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1, id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String line = id + "   " + rs2.getString("Name");
                        items.add(line);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listV.setItems(items);
        listV.setCellFactory(param -> new FriendRequestsController.RequestCell(p_pane, myID, conn));

        items2 = FXCollections.observableArrayList();
        stmt=null;
        rs=null;
        try {
            stmt = conn.prepareStatement("SELECT SENT_ID,RESPONCE FROM FRIEND_REQUESTS WHERE RECEIVE_ID=?");
            stmt.setInt(1, Integer.parseInt(myID));
            rs = stmt.executeQuery();
            while (rs.next()) {
                int resp = rs.getInt("RESPONCE");
                if (resp==3) {
                    int id = rs.getInt("SENT_ID");
                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1, id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String line = id + "   " + rs2.getString("Name");
                        items2.add(line);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listV2.setItems(items2);
        listV2.setCellFactory(param -> new FriendRequestsController.IgnoreCell(p_pane, myID, conn));
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    class RequestCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Accept");
        Pane pane2 = new Pane();
        Button button2 = new Button("Reject");
        Pane pane3 = new Pane();
        Button button3 = new Button("Ignore");

        public RequestCell(AnchorPane p_pane, String myID, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2, pane3, button3);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    //Source=?,Height=?,Width=?,Taken=?
                    try {
                        stmt = conn.prepareStatement("UPDATE FRIEND_REQUESTS SET RESPONCE=? WHERE SENT_ID=? AND RECEIVE_ID=?");
                        stmt.setInt(1,1);
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        stmt.setInt(3, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        stmt=null;
                        stmt = conn.prepareStatement("INSERT INTO [dbo].FRIENDS (USER_ID,FRIEND_ID) VALUES (?,?)");
                        stmt.setInt(1,Integer.parseInt(myID));
                        stmt.setInt(2,Integer.parseInt(firstWord(getItem())));
                        stmt.executeUpdate();

                        stmt=null;
                        stmt = conn.prepareStatement("INSERT INTO [dbo].FRIENDS (USER_ID,FRIEND_ID) VALUES (?,?)");
                        stmt.setInt(1,Integer.parseInt(firstWord(getItem())));
                        stmt.setInt(2,Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    //Source=?,Height=?,Width=?,Taken=?
                    try {
                        stmt = conn.prepareStatement("DELETE FROM FRIEND_REQUESTS WHERE SENT_ID=? AND RECEIVE_ID=?");
                        stmt.setInt(1, Integer.parseInt(firstWord(getItem())));
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    //Source=?,Height=?,Width=?,Taken=?
                    try {
                        stmt = conn.prepareStatement("UPDATE FRIEND_REQUESTS SET RESPONCE=? WHERE SENT_ID=? AND RECEIVE_ID=?");
                        stmt.setInt(1,3);
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        stmt.setInt(3, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    FriendRequestsController.this.initData(myID,conn);
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

    class IgnoreCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Remove From Ignored");

        public IgnoreCell(AnchorPane p_pane, String myID, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    //Source=?,Height=?,Width=?,Taken=?
                    try {
                        stmt = conn.prepareStatement("UPDATE FRIEND_REQUESTS SET RESPONCE=? WHERE SENT_ID=? AND RECEIVE_ID=?");
                        stmt.setNull(1, Types.INTEGER);
                        stmt.setInt(2, Integer.parseInt(firstWord(getItem())));
                        stmt.setInt(3, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    FriendRequestsController.this.initData(myID,conn);
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
