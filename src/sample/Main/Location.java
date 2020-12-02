package sample.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {
    private static final String SQL_INSERT_LOC = "INSERT INTO [dbo].LOCATION (Name) VALUES (?)";

    public static int getLocID(Connection conn, String loc) {
        PreparedStatement stmtLoc = null;
        ResultSet rsLoc = null;
        int locID = 0;
        try {
            stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
            stmtLoc.setString(1, loc);
            rsLoc = stmtLoc.executeQuery();
            if (rsLoc.next()) {
                locID = rsLoc.getInt("Location_ID");
            } else {
                try {
                    stmtLoc = null;
                    stmtLoc = conn.prepareStatement(SQL_INSERT_LOC);
                    stmtLoc.setString(1, loc);
                    stmtLoc.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("error in statement location");
                }
                stmtLoc.executeUpdate();

                stmtLoc = null;
                rsLoc = null;
                try {
                    stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE Name=?");
                    stmtLoc.setString(1, loc);
                    rsLoc = stmtLoc.executeQuery();
                    if (rsLoc.next()) {
                        locID = rsLoc.getInt("Location_ID");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return locID;
    }
}
