package sample.MediaControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.Main.FxmlLoader;
import sample.Main.Location;
import sample.MediaListsControllers.EditMediaListController;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {
    private static final String SQL_INSERT_EVENT = "INSERT INTO [dbo].EVENT (Name,Description,StartTime,EndTime,Privacy,Venue,Location,Creator,ChangeLog) VALUES (?,?,?,?,?,?,?,?,?)";

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    @FXML
    private ComboBox<String> privacyBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> startF;

    @FXML
    private Spinner<Integer> startL;

    @FXML
    private Spinner<Integer> endF;

    @FXML
    private Spinner<Integer> endL;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private TextField venue;

    @FXML
    private TextField location;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label error_l;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
        error_l.setTextFill(Color.web("#D8D9D9"));
    }

    @FXML
    private void handleAddEventButton() throws IOException {
        if (name.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //Name,Description,StartTime,EndTime,Privacy,Venue,Location,Creator,ChangeLog
            try {
                stmt = conn.prepareStatement(SQL_INSERT_EVENT);
                stmt.setString(1, name.getText());
                if (description.getText().isEmpty()) {
                    stmt.setNull(2, Types.VARCHAR);
                } else {
                    stmt.setString(2, description.getText());
                }
                String startD = startDate.getValue().toString();
                String startAll = startD + " " + startF.getValue().toString() + ":" + startL.getValue().toString() + ":0.0";
                java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(startAll);

                stmt.setTimestamp(3,timestamp);

                String endD = endDate.getValue().toString();
                String endAll = endD + " " + endF.getValue().toString() + ":" + endL.getValue().toString() + ":0.0";
                java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf(endAll);

                stmt.setTimestamp(4,timestamp2);

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

                if (venue.getText().isEmpty()) {
                    stmt.setNull(6, Types.VARCHAR);
                } else {
                    stmt.setString(6, venue.getText());
                }

                if (location.getText().isEmpty()) {
                    stmt.setNull(7, Types.VARCHAR);
                } else {
                    stmt.setInt(7, Location.getLocID(conn,location.getText()));
                }

                stmt.setInt(8, Integer.parseInt(myID));

                stmt.setDate(9, java.sql.Date.valueOf(java.time.LocalDate.now()));
                stmt.executeUpdate();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @FXML
    private void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/edit_events_list.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        EditMediaListController controller = loader.getController();

        //create query
        controller.initData("event", myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> CountStartF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0);
        this.startF.setValueFactory(CountStartF);
        SpinnerValueFactory<Integer> CountStartL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.startL.setValueFactory(CountStartL);
        SpinnerValueFactory<Integer> CountEndF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,0);
        this.endF.setValueFactory(CountEndF);
        SpinnerValueFactory<Integer> CountEndL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        this.endL.setValueFactory(CountEndL);

        privacyBox.setValue("OPEN");
        privacyBox.setItems(privacyList);
    }
}