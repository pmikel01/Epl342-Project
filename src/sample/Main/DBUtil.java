package sample.Main;

import java.sql.*;

public class DBUtil {

    private static boolean dbDriverLoaded = false;
    private static Connection conn = null;

    public static Connection getDBConnection() {
        String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy:1433;databaseName=pmikel01;user=pmikel01;password=B9phmaUP;";

        if (!dbDriverLoaded)
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                dbDriverLoaded = true;
            } catch (ClassNotFoundException e) {
                System.out.println("Cannot load DB driver!");
                return null;
            }

        try {
            if (conn == null)
                conn = DriverManager.getConnection(dbConnString);
            else if (conn.isClosed())
                conn = DriverManager.getConnection(dbConnString);
        } catch (SQLException e) {
            System.out.print("Cannot connect to the DB!\nGot error: ");
            System.out.print(e.getErrorCode());
            System.out.print("\nSQL State: ");
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
