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
import sample.Objects.ProfSelection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowProfListController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private ProfSelection pSel;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> profList;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn, ProfSelection pSel) {
        this.myID = myID;
        this.conn = conn;
        this.pSel = pSel;

        items = FXCollections.observableArrayList();
        PreparedStatement stmt=null;
        ResultSet rs=null;

        //FirstName=?,LastName=?,Birthday=?,livesIn=?

        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
            if (!pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null) {
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(Name)=SOUNDEX(?) AND livesIn=? AND Birthday=?");
                    stmt.setString(1, pSel.getFirstName() + " " + pSel.getLastName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(3, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(Description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(Name)=SOUNDEX(?) AND livesIn=?");
                    stmt.setString(1, pSel.getFirstName() + " " + pSel.getLastName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(Name)=SOUNDEX(?) AND Birthday=?");
                    stmt.setString(1, pSel.getFirstName() + " " + pSel.getLastName());

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(2, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(firstname)=SOUNDEX(?) AND livesIn=? AND Birthday=?");
                    stmt.setString(1, pSel.getFirstName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(3, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(LastName)=SOUNDEX(?) AND livesIn=? AND Birthday=?");
                    stmt.setString(1, pSel.getLastName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(3, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE livesIn=? AND Birthday=?");

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(1, loc_id);

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(2, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(firstname)=SOUNDEX(?) AND Birthday=?");
                    stmt.setString(1, pSel.getFirstName());

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(2, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmt.setString(1, pSel.getFirstName() + " " + pSel.getLastName());

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(LastName)=SOUNDEX(?) AND Birthday=?");
                    stmt.setString(1, pSel.getLastName());

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(2, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(LastName)=SOUNDEX(?) AND livesIn=?");
                    stmt.setString(1, pSel.getLastName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(firstname)=SOUNDEX(?) AND livesIn=?");
                    stmt.setString(1, pSel.getFirstName());

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(2, loc_id);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()!=null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE Birthday=?");

                    LocalDate bd_local = pSel.getBirthD().getValue();
                    Date birthD = Date.valueOf(bd_local);
                    stmt.setDate(1, birthD);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (!pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(FirstName)=SOUNDEX(?)");
                    stmt.setString(1, pSel.getFirstName());

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && !pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE SOUNDEX(LastName)=SOUNDEX(?)");
                    stmt.setString(1, pSel.getLastName());

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && !pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE WHERE livesIn=?");

                    int loc_id = 0 ;
                    PreparedStatement stmtLoc=null;
                    ResultSet rsLoc=null;
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(name)=SOUNDEX(?)");
                    stmtLoc.setString(1,pSel.getLocation());
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        loc_id = rsLoc.getInt("Location_ID");
                    }
                    stmt.setInt(1, loc_id);

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (pSel.getFirstName().isEmpty() && pSel.getLastName().isEmpty() && pSel.getLocation().isEmpty() && pSel.getBirthD().getValue()==null){
                try {
                    stmt = conn.prepareStatement("SELECT Name,ID FROM PROFILE");

                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        int id_user = rs.getInt("ID");
                        String name = rs.getString("Name");
                        if (pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String line = id_user + "  " + name;
                            items.add(line);
                        } else if (!pSel.getEducation().isEmpty() && pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getEducation().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE SOUNDEX(InstituteName)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int edu_id = rs2.getInt("Education_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,edu_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        } else if (pSel.getEducation().isEmpty() && !pSel.getManagers().isEmpty()) {
                            String[] arrOfStr = pSel.getManagers().split(",");
                            for (String a : arrOfStr){
                                PreparedStatement stmt2 = null;
                                ResultSet rs2 = null;
                                stmt2 = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE SOUNDEX(description)=SOUNDEX(?)");
                                stmt2.setString(1,a);
                                rs2 = stmt2.executeQuery();
                                if (rs2.next()) {
                                    int wor_id = rs2.getInt("Work_ID");
                                    stmt2 = null;
                                    rs2 = null;
                                    stmt2 = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt2.setInt(1,wor_id);
                                    stmt2.setInt(2,id_user);
                                    rs2 = stmt2.executeQuery();
                                    if (rs2.next()) {
                                        String line = id_user + "  " + name;
                                        items.add(line);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        profList.setItems(items);
        profList.setCellFactory(param -> new XCell(p_pane, myID, conn));
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
                        controller.initData("id", myID, conn);

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
