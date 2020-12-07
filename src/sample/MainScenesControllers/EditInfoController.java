package sample.MainScenesControllers;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import sample.Main.FxmlLoader;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.*;
        import java.time.LocalDate;
        import java.util.ResourceBundle;

public class EditInfoController implements Initializable {

    private static final String SQL_INSERT_LOC = "INSERT INTO [dbo].LOCATION (Name) VALUES (?)";

    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private AnchorPane p_pane;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private TextField website;

    @FXML
    private TextField birth;

    @FXML
    private TextField lives;

    @FXML
    private DatePicker bd;

    @FXML
    private Label error_l;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;

        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT FirstName,LastName,Birthday,Email,Website,Gender,BirthPlace,LivesIn FROM PROFILE WHERE ID=?");
            stmt.setInt(1, Integer.parseInt(myID));
            rs = stmt.executeQuery();
            if (rs.next()) {
                String first = rs.getString("FirstName");
                String last = rs.getString("LastName");
                Date bday = rs.getDate("Birthday");
                String mail = rs.getString("Email");
                String web = rs.getString("Website");
                boolean gender = rs.getBoolean("Gender");
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

                firstname.setText(first);
                lastname.setText(last);
                if(bday!=null) {
                    LocalDate localD = bday.toLocalDate();
                    bd.setValue(localD);
                }
                if(mail!=null) {
                    email.setText(mail);
                }
                if(web!=null) {
                    website.setText(web);
                }
                if(!gender) {
                    genderBox.setValue("Male");
                } else {
                    genderBox.setValue("Female");
                }
                if(birthPlaceID!=0) {
                    birth.setText(birthPlace);
                }
                if(livesInID!=0) {
                    lives.setText(livesIn);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButton() throws IOException, SQLException {
        if (firstname.getText().isEmpty() || lastname.getText().isEmpty()) {
            error_l.setTextFill(Color.RED);
        } else {
            PreparedStatement stmt=null;
            try {
                stmt = conn.prepareStatement("UPDATE PROFILE SET FirstName=?,LastName=?,Birthday=?,Email=?,Website=?,Gender=?,BirthPlace=?,LivesIn=? WHERE ID=?");
                stmt.setString(1, firstname.getText());
                stmt.setString(2, lastname.getText());
                if (bd.getValue()==null) {
                    stmt.setNull(3, Types.DATE);
                } else {
                    LocalDate bd_local = bd.getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(3, birthD);
                }
                if (email.getText().isEmpty()) {
                    stmt.setNull(4, Types.VARCHAR);
                } else {
                    stmt.setString(4, email.getText());
                }
                if (website.getText().isEmpty()) {
                    stmt.setNull(5, Types.VARCHAR);
                } else {
                    stmt.setString(5, website.getText());
                }
                boolean gend = genderBox.getValue().equals("Female");
                stmt.setBoolean(6,gend);

                int birthExists = 0;

                if (birth.getText().isEmpty()) {
                    stmt.setNull(7, Types.VARCHAR);
                } else {
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    try {
                        stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
                        stmtLoc.setString(1,birth.getText());
                        rsLoc = stmtLoc.executeQuery();
                        if (rsLoc.next()) {
                            birthExists = rsLoc.getInt("Location_ID");
                        } else {
                            PreparedStatement preparedStatement;
                            try {
                                preparedStatement = conn.prepareStatement(SQL_INSERT_LOC);
                                preparedStatement.setString(1, birth.getText());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.err.println("error in statement location");
                            }
                            stmtLoc.executeUpdate();

                            PreparedStatement stmt2=null;
                            ResultSet rs2=null;
                            try {
                                stmt2 = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
                                stmt2.setString(1,birth.getText());
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    birthExists = rs2.getInt("Location_ID");
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    stmt.setInt(7, birthExists);
                }

                int livesExists = 0;

                if (lives.getText().isEmpty()) {
                    stmt.setNull(8, Types.VARCHAR);
                } else {
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    try {
                        stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
                        stmtLoc.setString(1,lives.getText());
                        rsLoc = stmtLoc.executeQuery();
                        if (rsLoc.next()) {
                            livesExists = rsLoc.getInt("Location_ID");
                        } else {
                            PreparedStatement preparedStatement;
                            try {
                                preparedStatement = conn.prepareStatement(SQL_INSERT_LOC);
                                preparedStatement.setString(1, lives.getText());
                                preparedStatement.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                System.err.println("error in statement location");
                            }
                            stmtLoc.executeUpdate();

                            PreparedStatement stmt2=null;
                            ResultSet rs2=null;
                            try {
                                stmt2 = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
                                stmt2.setString(1,lives.getText());
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    livesExists = rs2.getInt("Location_ID");
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    stmt.setInt(8, livesExists);
                }
                
                stmt.setInt(9, Integer.parseInt(myID));

                stmt.executeUpdate();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MainScenes/my_profile.fxml"));
                Pane view = loader.load();

                //access the controller and call a method
                MyProfController controller = loader.getController();

                //create query
                controller.initData(myID, conn);

                p_pane.getChildren().setAll(view);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderBox.setValue("Male");
        genderBox.setItems(genderList);
        error_l.setTextFill(Color.web("#D8D9D9"));
    }
}