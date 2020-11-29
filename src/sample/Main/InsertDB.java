package sample.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InsertDB {
    private static final String SQL_INSERT_LOC = "INSERT INTO [dbo].LOCATION (Name) VALUES (?)";
    private static final String SQL_INSERT_USER = "INSERT INTO [dbo].PROFILE (Username,Password,FirstName,LastName,Birthday,Email,Website,Gender,Verified,Birthplace,LivesIn,Link) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_INTEREST = "INSERT INTO [dbo].INTERESTS (Name) VALUES (?)";
    private static final String SQL_INSERT_USER_INTEREST = "INSERT INTO [dbo].PROFILES_INTERESTS (INTERESTS_ID,USER_ID) VALUES (?,?)";
    private static final String SQL_INSERT_FRIENDS = "INSERT INTO [dbo].FRIENDS (USER_ID,FRIEND_ID) VALUES (?,?)";

    public static void insertData(Connection conn){
//        insertLocations(conn);
        insertUsers(conn);
//        insertInterests(conn);
//        insertFriends(conn);
//        insertUserInterests(conn);
    }
    private static void insertUsers(Connection conn){
        int i = 1;
        File file = new File("users.txt");
        Scanner read;
        try {
            read = new Scanner(file);
            read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split("\t");
                PreparedStatement preparedStatement;
                //Username,Password,FirstName,LastName,Birthday,Email,Website,Gender,Verified,Birthplace,LivesIn,Link
                try {
                    preparedStatement = conn.prepareStatement(SQL_INSERT_USER);
                    String username = "username"+i;
                    preparedStatement.setString(1, username);
                    String pass = "password"+i;
                    preparedStatement.setString(2, pass);
                    preparedStatement.setString(3, data[1]);
                    preparedStatement.setString(4, data[2]);
                    java.sql.Date date=null;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date parsed = format.parse(data[6]);
                        date = new java.sql.Date(parsed.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    preparedStatement.setDate(5, date);
                    String email = "user"+i+"@socialFace.com";
                    preparedStatement.setString(6, email);
                    String web = "www.socialFace.com/username"+i;
                    preparedStatement.setString(7, web);
                    preparedStatement.setBoolean(8,Boolean.parseBoolean(data[3]));
                    preparedStatement.setBoolean(9,false);
                    preparedStatement.setInt(10, Integer.parseInt(data[4]));
                    preparedStatement.setInt(11, Integer.parseInt(data[5]));
                    preparedStatement.setNull(12, Types.INTEGER);
                    preparedStatement.executeUpdate();
                    i++;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Prepared Statement error Users .");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File for user not found");
        }
    }

    private static void insertLocations(Connection conn){
        File file = new File("cities.txt");
        Scanner read;
        try {
            read = new Scanner(file);
            read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split("\t");
                PreparedStatement preparedStatement;
                try {
                    preparedStatement = conn.prepareStatement(SQL_INSERT_LOC);
                    preparedStatement.setString(1, data[1]);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Prepared Statement error Location .");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File for locations not found");
        }
    }

    private static void insertInterests(Connection conn){

    }
    private static void insertFriends(Connection conn){

    }
    private static void insertUserInterests(Connection conn){

    }

}
