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
import sample.MediaControllers.EditPhotoController;
import sample.MediaControllers.ShowPictureController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchPhotos;
import sample.Objects.SearchVideos;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ShowPicListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchPhotos photos;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String id;
    private String myID;
    private Connection conn;

    public void initData(SearchPhotos photos, String id, String myID, Connection conn) {
        this.photos = photos;
        this.id = id;
        this.myID = myID;
        this.conn = conn;

        if (!photos.getHeight().isEmpty() && !photos.getWidth().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID,Privacy FROM PICTURE WHERE Height=? AND Width=? AND Likes>=?");
                stmt.setInt(1, Integer.parseInt(photos.getHeight()));
                stmt.setInt(2, Integer.parseInt(photos.getWidth()));
                stmt.setInt(3, photos.getLikes());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");

                    if (id.equals(myID)) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, Integer.parseInt(id));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(pic_id+"");

                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==Integer.parseInt(id)) {
                                items.add(pic_id+"");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (photos.getHeight().isEmpty() && !photos.getWidth().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID,Privacy FROM PICTURE WHERE Height=? AND Likes>=?");
                stmt.setInt(1, Integer.parseInt(photos.getWidth()));
                stmt.setInt(2, photos.getLikes());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");
                    if (id.equals(myID)) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, Integer.parseInt(id));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(pic_id+"");

                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==Integer.parseInt(id)) {
                                items.add(pic_id+"");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (!photos.getHeight().isEmpty() && photos.getWidth().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID,Privacy FROM PICTURE WHERE Height=? AND Likes>=?");
                stmt.setInt(1, Integer.parseInt(photos.getHeight()));
                stmt.setInt(2, photos.getLikes());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");
                    if (id.equals(myID)) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, Integer.parseInt(id));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(pic_id+"");

                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==Integer.parseInt(id)) {
                                items.add(pic_id+"");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (photos.getHeight().isEmpty() && photos.getWidth().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID,Privacy FROM PICTURE WHERE Likes>=?");
                stmt.setInt(1, photos.getLikes());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");
                    if (id.equals(myID)) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 1) {
                        items.add(pic_id+"");

                    } else if (rs.getInt("Privacy") == 3) {
                        PreparedStatement stmt2 =null;
                        ResultSet rs2=null;
                        stmt2 = conn.prepareStatement("SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND FRIEND_ID=?");
                        stmt2.setInt(1, Integer.parseInt(myID));
                        stmt2.setInt(2, Integer.parseInt(id));
                        rs2 = stmt2.executeQuery();
                        if (rs2.next()) {
                            items.add(pic_id+"");

                        }
                    } else if (rs.getInt("Privacy") == 4) {
                        ResultSet rs2=null;
                        CallableStatement stmt2 = conn.prepareCall("{call Procedure_Friends_Network_3(?)}");
                        stmt2.setInt(1,Integer.parseInt(myID));
                        rs2 = stmt2.executeQuery();
                        while (rs2.next()) {
                            int possible = rs2.getInt(1);
//                            int possible2 = rs2.getInt(2);
                            if (possible==Integer.parseInt(id)) {
                                items.add(pic_id+"");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        }

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowPicListController.MyPictureCell(p_pane, myID, id, conn));
        } else {
            listV.setCellFactory(param -> new ShowPicListController.PictureCell(p_pane, myID, id, conn));
        }
    }
    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    static class PictureCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Picture");

        public PictureCell(AnchorPane p_pane, String myID, String id, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setSpacing(5);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_photo.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowPictureController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, firstWord(getItem()), conn, 0);

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

    static class MyPictureCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Picture");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Picture");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Picture");

        public MyPictureCell(AnchorPane p_pane, String myID, String id, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2, pane3, button3);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setSpacing(5);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_photo.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowPictureController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, firstWord(getItem()), conn, 0);

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
                        loader.setLocation(getClass().getResource("../Media/edit_photo.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EditPhotoController controller = loader.getController();

                        //create query
                        controller.initData(firstWord(getItem()), myID, conn, 0);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
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
        loader.setLocation(getClass().getResource("../SearchMedia/search_photos.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        SearchPicController controller = loader.getController();

        //create query
        controller.initData(id, myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}