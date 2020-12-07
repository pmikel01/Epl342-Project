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
import sample.MainScenesControllers.MyProfController;
import sample.MainScenesControllers.ShowProfController;
import sample.Objects.ProfSelection;
import sample.Objects.SearchEvents;
import sample.SearchMediaControllers.SearchLinkController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventInterestedController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private Label number;

    private int event_id;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> profList;

    private String myID;
    private Connection conn;
    private SearchEvents events;

    private int[][] users ;
    private String[] users_names ;

    public void initData(String myID, Connection conn, int event_id, SearchEvents events) {
        this.myID = myID;
        this.conn = conn;
        this.event_id = event_id;
        this.events = events;

        items = FXCollections.observableArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT COUNT (USER_ID) as [Interested] FROM [INTERESTED-IN_EVENT] WHERE INTEREST_RANK=1 AND EVENT_ID=? GROUP BY EVENT_ID");
            stmt.setInt(1, event_id);
            rs = stmt.executeQuery();
            String going = "0";
            String interested = "0";
            String not_g = "0";
            if (rs.next()) {
                going = rs.getInt(1) + "";
            }

            stmt = null;
            rs = null;
            stmt = conn.prepareStatement("SELECT COUNT (USER_ID) as [Interested] FROM [INTERESTED-IN_EVENT] WHERE INTEREST_RANK=2 AND EVENT_ID=? GROUP BY EVENT_ID");
            stmt.setInt(1, event_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                not_g = rs.getInt(1) + "";
            }
            stmt = null;
            rs = null;
            stmt = conn.prepareStatement("SELECT COUNT (USER_ID) as [Interested] FROM [INTERESTED-IN_EVENT] WHERE INTEREST_RANK=3 AND EVENT_ID=? GROUP BY EVENT_ID");
            stmt.setInt(1, event_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                interested = rs.getInt(1) + "";
            }

            number.setText("Going: " + going + "  Not Going: " + not_g + "  Interested: " + interested);

            stmt = null;
            rs = null;
            stmt = conn.prepareStatement("SELECT USER_ID,INTEREST_RANK FROM [INTERESTED-IN_EVENT] WHERE EVENT_ID=?");
            stmt.setInt(1, event_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int interested_id = rs.getInt("USER_ID");
                int rank = rs.getInt("INTEREST_RANK");
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;
                stmt2 = conn.prepareStatement("SELECT Name FROM PROFILE WHERE ID=?");
                stmt2.setInt(1,interested_id);
                rs2 = stmt2.executeQuery();
                if (rs2.next()) {
                    String name = rs2.getString("Name");
                    String line="";
                    if (rank==1) {
                        line = interested_id + "  " + name + ":  is " + "Going";
                    } else  if (rank==2) {
                        line = interested_id + "  " + name + ":  is " + "Not Going";
                    } else {
                        line = interested_id + "  " + name + ":  is " + "Interested";
                    }
                    items.add(line);
                }
            }
            profList.setItems(items);
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        profList.setCellFactory(param -> new XCell(p_pane, myID, conn, events));
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Profile");

        public XCell(AnchorPane p_pane, String myID, Connection conn, SearchEvents events) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (myID.equals(firstWord(getItem()))) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../MainScenes/my_profile.fxml"));
                            Pane view = null;
                            view = loader.load();
                            //access the controller and call a method
                            MyProfController controller = loader.getController();

                            //create query
                            controller.initData(myID, conn);

                            p_pane.getChildren().setAll(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
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
        loader.setLocation(getClass().getResource("../MediaLists/main_events_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        EventSearchController controller = loader.getController();

        //create query
        controller.initData(events, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
