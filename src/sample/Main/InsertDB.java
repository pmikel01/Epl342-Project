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
    private static final String SQL_INSERT_FRIENDS = "INSERT INTO [dbo].FRIENDS (USER_ID,FRIEND_ID) VALUES (?,?)";
    private static final String SQL_INSERT_INTEREST = "INSERT INTO [dbo].INTERESTS (Name) VALUES (?)";
    private static final String SQL_INSERT_USER_INTEREST = "INSERT INTO [dbo].PROFILES_INTERESTS (INTERESTS_ID,USER_ID) VALUES (?,?)";

    public static void insertData(Connection conn){
        insertLocations(conn);
        insertUsers(conn);
        insertFriends(conn);
        insertInterests(conn);
        insertUserInterests(conn);
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
                    System.err.println("error in statement location");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File not found");
        }
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
                    Boolean gender = null;
                    gender = data[3].equals("1");
                    preparedStatement.setBoolean(8,gender);
                    preparedStatement.setBoolean(9,false);
                    preparedStatement.setInt(10, Integer.parseInt(data[4]));
                    preparedStatement.setInt(11, Integer.parseInt(data[5]));
                    preparedStatement.setNull(12, Types.INTEGER);
                    preparedStatement.executeUpdate();
                    i++;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("error in statement users");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File not found");
        }
    }

    private static void insertFriends(Connection conn){
        File file = new File("friends.txt");
        Scanner read;
        try {
            read = new Scanner(file);
            read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split("\t");
                PreparedStatement preparedStatement;
                try {
                    if (!data[0].equals(data[1])) {
                        preparedStatement = conn.prepareStatement(SQL_INSERT_FRIENDS);
                        preparedStatement.setInt(1, Integer.parseInt(data[0]));
                        preparedStatement.setInt(2, Integer.parseInt(data[1]));
                        preparedStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("error in statement friends");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File not found");
        }
    }

    private static void insertInterests(Connection conn){
        File file = new File("interests.txt");
        Scanner read;
        try {
            read = new Scanner(file);
            read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split("\t");
                PreparedStatement preparedStatement;
                try {
                    preparedStatement = conn.prepareStatement(SQL_INSERT_INTEREST);
                    preparedStatement.setString(1, data[1]);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("error in statement interests");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File not found");
        }
    }

    private static void insertUserInterests(Connection conn){
        File file = new File("user_interests.txt");
        Scanner read;
        try {
            read = new Scanner(file);
            read.nextLine();
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] data = line.split("\t");
                PreparedStatement preparedStatement;
                try {
                    preparedStatement = conn.prepareStatement(SQL_INSERT_USER_INTEREST);
                    preparedStatement.setInt(1, Integer.parseInt(data[1]));
                    preparedStatement.setInt(2, Integer.parseInt(data[0]));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("error in statement user interests");
                }
            }
        } catch (FileNotFoundException e1) {
            System.err.println("File not found");
        }
    }

}
