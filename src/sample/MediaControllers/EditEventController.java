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
import java.time.ZoneId;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {
    private static final String SQL_UPDATE_EVENT = "UPDATE EVENT SET Name=?,Description=?,StartTime=?,EndTime=?,Privacy=?,Venue=?,Location=? WHERE Event_ID=?";

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

    private String mediaID;
    private String myID;
    private Connection conn;

    public void initData(String mediaID, String myID, Connection conn) {
        this.mediaID = mediaID;
        this.myID = myID;
        this.conn = conn;

        name.setText("");
        description.setText("");
        venue.setText("");
        location.setText("");
        show();
    }

    public static String secondWord(String input) {
        return input.split(" ")[1];
    }

    public static String firstWord2(String input) {
        return input.split(":")[0];
    }

    public static String secondWord2(String input) {
        return input.split(":")[1];
    }

    public void show(){
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT Name,Description,StartTime,EndTime,Privacy,Venue,Location FROM EVENT WHERE Event_ID=?");
            stmt.setInt(1, Integer.parseInt(mediaID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("Name"));
                if (rs.getString("Description") != null) {
                    description.setText(rs.getString("Description"));
                }

                startDate.setValue(rs.getTimestamp("StartTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                endDate.setValue(rs.getTimestamp("EndTime").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                String timeS = secondWord(rs.getTimestamp("StartTime").toString());
                SpinnerValueFactory<Integer> CountStartF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,Integer.parseInt(firstWord2(timeS)));
                this.startF.setValueFactory(CountStartF);
                SpinnerValueFactory<Integer> CountStartL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,Integer.parseInt(secondWord2(timeS)));
                this.startL.setValueFactory(CountStartL);


                String timeE = secondWord(rs.getTimestamp("EndTime").toString());
                SpinnerValueFactory<Integer> CountEndF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,Integer.parseInt(firstWord2(timeE)));
                this.endF.setValueFactory(CountEndF);
                SpinnerValueFactory<Integer> CountEndL = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,Integer.parseInt(secondWord2(timeE)));
                this.endL.setValueFactory(CountEndL);

                //"OPEN", "CLOSED", "FRIEND", "NETWORK"
                if (rs.getInt("Privacy") == 1) {
                    privacyBox.setValue("OPEN");
                } else if (rs.getInt("Privacy") == 2) {
                    privacyBox.setValue("CLOSED");
                } else if (rs.getInt("Privacy") == 3) {
                    privacyBox.setValue("FRIEND");
                } else {
                    privacyBox.setValue("NETWORK");
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
    private void handleUpdateEventButton() throws IOException {
        if (name.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else if (startDate.getValue()==null || endDate.getValue()==null) {
            error_l.setTextFill(Color.RED);
        } else if (startDate.getValue()==null || endDate.getValue()==null) {
            error_l.setTextFill(Color.RED);
        } else if (endDate.getValue().isBefore(startDate.getValue()))  {
            error_l.setTextFill(Color.RED);
        } else if (endDate.getValue().isEqual(startDate.getValue()) && startF.getValue()>=endF.getValue())  {
            error_l.setTextFill(Color.RED);
        } else if (endDate.getValue().isEqual(startDate.getValue()) && startF.getValue().equals(endF.getValue()) && startL.getValue()>=endL.getValue())  {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //Name=?,Description=?,StartTime=?,EndTime=?,Privacy=?,Venue=?,Location=?
            try {
                stmt = conn.prepareStatement(SQL_UPDATE_EVENT);
                stmt.setString(1, name.getText());
                if (description.getText().isEmpty()) {
                    stmt.setNull(2, Types.VARCHAR);
                } else {
                    stmt.setString(2, description.getText());
                }
                String startD = startDate.getValue().toString();
                String startAll = startD + " " + startF.getValue().toString() + ":" + startL.getValue().toString() + ":0.0";
                java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(startAll);

                stmt.setTimestamp(3, timestamp);

                String endD = endDate.getValue().toString();
                String endAll = endD + " " + endF.getValue().toString() + ":" + endL.getValue().toString() + ":0.0";
                java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf(endAll);

                stmt.setTimestamp(4, timestamp2);

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
                    stmt.setInt(7, Location.getLocID(conn, location.getText()));
                }
                stmt.setInt(8,Integer.parseInt(mediaID));

                stmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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