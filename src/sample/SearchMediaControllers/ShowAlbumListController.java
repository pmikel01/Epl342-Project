package sample.SearchMediaControllers;

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
import sample.Main.FxmlLoader;
import sample.Main.MainAppController;
import sample.MediaControllers.EditAlbumController;
import sample.MediaControllers.ShowAlbumController;
import sample.MediaControllers.ShowCommentsController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchAlbums;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowAlbumListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchAlbums album;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String id;
    private String myID;
    private Connection conn;

    public void initData(SearchAlbums album, String id, String myID, Connection conn) {
        this.album = album;
        this.id = id;
        this.myID = myID;
        this.conn = conn;

        PreparedStatement stmt=null;
        ResultSet rs=null;

        if (!album.getName().isEmpty() && !album.getLocation().isEmpty() && !album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,album.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE SOUNDEX(Title)=SOUNDEX(?) AND SOUNDEX(Desciption)=SOUNDEX(?) AND Taken=?");
                stmt.setString(1,album.getName());
                stmt.setString(2, album.getDescription());
                stmt.setInt(3, loc_id);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (album.getName().isEmpty() && !album.getLocation().isEmpty() && !album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,album.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE AND SOUNDEX(Desciption)=SOUNDEX(?) AND Taken=?");
                stmt.setString(1, album.getDescription());
                stmt.setInt(2, loc_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!album.getName().isEmpty() && album.getLocation().isEmpty() && !album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE SOUNDEX(Title)=SOUNDEX(?) AND SOUNDEX(Desciption)=SOUNDEX(?)");
                stmt.setString(1,album.getName());
                stmt.setString(2, album.getDescription());

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!album.getName().isEmpty() && !album.getLocation().isEmpty() && album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,album.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE SOUNDEX(Title)=SOUNDEX(?) AND Taken=?");
                stmt.setString(1,album.getName());
                stmt.setInt(2, loc_id);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (album.getName().isEmpty() && album.getLocation().isEmpty() && !album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE SOUNDEX(Desciption)=SOUNDEX(?)");
                stmt.setString(1, album.getDescription());

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!album.getName().isEmpty() && album.getLocation().isEmpty() && album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE SOUNDEX(Title)=SOUNDEX(?)");
                stmt.setString(1,album.getName());

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (album.getName().isEmpty() && !album.getLocation().isEmpty() && album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                int loc_id = 0 ;
                PreparedStatement stmtLoc=null;
                ResultSet rsLoc=null;
                stmtLoc = conn.prepareStatement("SELECT Location_ID FROM LOCATION WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmtLoc.setString(1,album.getLocation());
                rsLoc = stmtLoc.executeQuery();
                if (rsLoc.next()) {
                    loc_id = rsLoc.getInt("Location_ID");
                }

                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM WHERE AND Taken=?");
                stmt.setInt(1, loc_id);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (album.getName().isEmpty() && album.getLocation().isEmpty() && album.getDescription().isEmpty()) {
            items = FXCollections.observableArrayList();
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title,Privacy FROM ALBUM");

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    //open(1) closed(2) friend(3) network(4)
                    if (rs.getInt("Privacy") == 1) {
                        String title = rs.getString("Title");
                        String line = album_id + "  " + title;
                        items.add(line);
                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt.setInt(1, Integer.parseInt(myID));
                        stmt.setInt(2, Integer.parseInt(id));
                        rs = stmt.executeQuery();
                        if (rs2.next()) {
                            String title = rs.getString("Title");
                            String line = album_id + "  " + title;
                            items.add(line);
                        }
                    } else if (rs.getInt("Privacy") == 4) {

                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowAlbumListController.MyAlbumCell(p_pane, myID, id, conn));
        } else {
            listV.setCellFactory(param -> new ShowAlbumListController.AlbumCell(p_pane, myID, id, conn));
        }
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    static class AlbumCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Album");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");

        public AlbumCell(AnchorPane p_pane, String myID, String id, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_album.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowAlbumController controller = loader.getController();

                        //create query
                        controller.initData(id,myID, firstWord(getItem()), conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../MediaLists/comments.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowCommentsController controller = loader.getController();

                        //create query
                        controller.initData(id,myID, "album",  firstWord(getItem()), conn);

                        p_pane.getChildren().setAll(view);
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

    static class MyAlbumCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Album");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");
        Pane pane3 = new Pane();
        Button button3 = new Button("Edit Album");
        Pane pane4 = new Pane();
        Button button4 = new Button("Delete Album");

        public MyAlbumCell(AnchorPane p_pane, String myID, String id, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            button4.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2, pane3, button3, pane4, button4);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_album.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowAlbumController controller = loader.getController();

                        //create query
                        controller.initData(id,myID,  firstWord(getItem()), conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../MediaLists/comments.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowCommentsController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, "album",  firstWord(getItem()), conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/edit_album.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EditAlbumController controller = loader.getController();

                        //create query
                        controller.initData( firstWord(getItem()), myID, conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    //Delete from database
                    getListView().getItems().remove(getItem());
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
        loader.setLocation(getClass().getResource("../SearchMedia/search_albums.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        SearchAlbumController controller = loader.getController();

        //create query
        controller.initData(id, myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}