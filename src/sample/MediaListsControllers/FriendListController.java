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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.MainScenesControllers.FriendRequestsController;
import sample.MainScenesControllers.ShowProfController;
import sample.MainScenesControllers.StatisticsController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class FriendListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String choose;
    private String myID;
    private Connection conn;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listV ;

    @FXML
    private Label name;

    public void initData(String choose, String myID, Connection conn, int count) {
        this.choose = choose;
        this.myID = myID;
        this.conn = conn;

        if (choose.equals("stat1")) {
            name.setText("Most Famous Friends");

            items = FXCollections.observableArrayList();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                CallableStatement stmt5 = conn.prepareCall("{call Procedure_Famous_Friends(?)}");
                stmt5.setInt(1,Integer.parseInt(myID));
                rs = stmt5.executeQuery();
                int i=0;
                while (rs.next() && i<12) {
                    PreparedStatement stmt2 = null;
                    ResultSet rs2 = null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1,rs.getInt(1));
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String name = rs2.getString("Name");
                        if (rs.getInt(2) == 1) {
                            String line = rs.getInt(1) + " :   " + name + " has "+ rs.getInt(2) + " Friend";
                            items.add(line);
                        } else {
                            String line = rs.getInt(1) + " :   " + name + " has "+ rs.getInt(2) + " Friends";
                            items.add(line);
                        }
                    }
                    i++;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("stat2")) {
            name.setText("Friends with Same Friends");

            items = FXCollections.observableArrayList();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;
                stmt2 = conn.prepareStatement("SELECT COUNT (FRIEND_ID) FROM FRIENDS WHERE USER_ID=?");
                stmt2.setInt(1,Integer.parseInt(myID));
                rs2 = stmt2.executeQuery();
                int my_count =0;
                if (rs2.next()) {
                    my_count = rs2.getInt(1);
                }

                CallableStatement stmt5 = conn.prepareCall("{call Procedure_Same_Friends}");
                rs = stmt5.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == Integer.parseInt(myID)) {
                        stmt2 = null;
                        rs2 = null;
                        stmt2 = conn.prepareStatement("SELECT COUNT (FRIEND_ID) FROM FRIENDS WHERE USER_ID=?");
                        stmt2.setInt(1,rs.getInt(2));
                        rs2 = stmt2.executeQuery();
                        int fr_count =0;
                        if (rs2.next()) {
                            fr_count = rs2.getInt(1);
                        }
                        if (rs.getInt(3) == my_count-1  && my_count==fr_count) {
                            stmt2 = null;
                            rs2 = null;
                            stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                            stmt2.setInt(1,Integer.parseInt(myID));
                            stmt2.setInt(2,rs.getInt(2));
                            rs2 = stmt2.executeQuery();
                            if (rs2.next()) {
                                stmt2 = null;
                                rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                                stmt2.setInt(1,rs.getInt(2));
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    String name = rs2.getString("Name");
                                    String line = rs.getInt(2) + " :   " + name;
                                    items.add(line);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);


        } else if (choose.equals("stat3")) {
            name.setText("Friends with At least Same Friends");

            items = FXCollections.observableArrayList();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;
                stmt2 = conn.prepareStatement("SELECT COUNT (FRIEND_ID) FROM FRIENDS WHERE USER_ID=?");
                stmt2.setInt(1,Integer.parseInt(myID));
                rs2 = stmt2.executeQuery();
                int my_count =0;
                if (rs2.next()) {
                    my_count = rs2.getInt(1);
                }

                CallableStatement stmt5 = conn.prepareCall("{call Procedure_Same_Friends}");
                rs = stmt5.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == Integer.parseInt(myID)) {
                        if (rs.getInt(3) == my_count-1) {
                            stmt2 = null;
                            rs2 = null;
                            stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                            stmt2.setInt(1,Integer.parseInt(myID));
                            stmt2.setInt(2,rs.getInt(2));
                            rs2 = stmt2.executeQuery();
                            if (rs2.next()) {
                                stmt2 = null;
                                rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                                stmt2.setInt(1,rs.getInt(2));
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    String name = rs2.getString("Name");
                                    String line = rs.getInt(2) + " :   " + name;
                                    items.add(line);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }  else if (choose.equals("stat4")) {
            name.setText("Network Of Friends");

            items = FXCollections.observableArrayList();
            ResultSet rs = null;
            CallableStatement stmt = null;
            try {
                stmt = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                stmt.setInt(1,Integer.parseInt(myID));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int net = rs.getInt(1);

                    PreparedStatement stmt2 = null;
                    ResultSet rs2 = null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1,net);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String name = rs2.getString("Name");
                        String line = net + " :   " + name;
                        items.add(line);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("stat5")) {
            name.setText("Friends With Album bigger than " + count);

            items = FXCollections.observableArrayList();
            ResultSet rs = null;
            CallableStatement stmt = null;
            try {
                stmt = conn.prepareCall("{call Procedure_ALBUM_X_FRIENDS(?,?)}");
                stmt.setInt(1,Integer.parseInt(myID));
                stmt.setInt(2,count);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int fr_id = rs.getInt(1);

                    PreparedStatement stmt2 = null;
                    ResultSet rs2 = null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1,fr_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String name = rs2.getString("Name");
                        String line = fr_id + " :   " + name;
                        items.add(line);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("stat6")) {
            name.setText("Users With Album bigger than " + count);

            items = FXCollections.observableArrayList();
            ResultSet rs = null;
            CallableStatement stmt = null;
            try {
                stmt = conn.prepareCall("{call Procedure_ALBUM_X_NETWORK(?,?)}");
                stmt.setInt(1,Integer.parseInt(myID));
                stmt.setInt(2,count);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int fr_id = rs.getInt(1);

                    PreparedStatement stmt2 = null;
                    ResultSet rs2 = null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                    stmt2.setInt(1,fr_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String name = rs2.getString("Name");
                        String line = fr_id + " :   " + name;
                        items.add(line);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("stat7")) {
            name.setText("Friends With Same Interests");

            items = FXCollections.observableArrayList();
            ResultSet rs = null;
            CallableStatement stmt = null;
            try {
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;
                stmt2 = conn.prepareStatement("SELECT COUNT (INTERESTS_ID) FROM PROFILES_INTERESTS WHERE USER_ID=?");
                stmt2.setInt(1,Integer.parseInt(myID));
                rs2 = stmt2.executeQuery();
                int my_count =0;
                if (rs2.next()) {
                    my_count = rs2.getInt(1);
                }

                stmt = conn.prepareCall("{call Procedure_Same_Interests(?)}");
                stmt.setInt(1,Integer.parseInt(myID));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int fr_id = rs.getInt(1);

                    stmt2 = null;
                    rs2 = null;
                    stmt2 = conn.prepareStatement("SELECT COUNT (INTERESTS_ID) FROM PROFILES_INTERESTS WHERE USER_ID=?");
                    stmt2.setInt(1,fr_id);
                    rs2 = stmt2.executeQuery();
                    int fr_count =0;
                    if (rs2.next()) {
                        fr_count = rs2.getInt(1);
                    }

                    if (my_count == fr_count) {
                        stmt2 = null;
                        rs2 = null;
                        stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                        stmt2.setInt(1,fr_id);
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            String name = rs2.getString("Name");
                            String line = fr_id + " :   " + name;
                            items.add(line);
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }
        listV.setCellFactory(param -> new FriendListController.XCell(p_pane, myID, conn));
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Profile");

        public XCell(AnchorPane p_pane, String myID, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../MainScenes/profile.fxml"));
                        Pane showProfParent = null;
                        showProfParent = loader.load();
                        //access the controller and call a method
                        ShowProfController controller = loader.getController();

                        //create query
                        controller.initData(firstWord(getItem()), myID, conn);

                        p_pane.getChildren().setAll(showProfParent);
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

    @FXML
    private void handleBackButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/statistics.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        StatisticsController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
