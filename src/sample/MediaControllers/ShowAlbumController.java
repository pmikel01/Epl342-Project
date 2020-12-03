package sample.MediaControllers;

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
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;
import sample.SearchMediaControllers.ShowEventListController;
import sample.SearchMediaControllers.ShowPicListController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowAlbumController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;
    private String myID;
    private String album_id;
    private Connection conn;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();


    public void initData(String id, String myID, String album_id, Connection conn) {
        this.id = id;
        this.album_id = album_id;
        this.myID = myID;
        this.conn = conn;

        items = FXCollections.observableArrayList();
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement("SELECT PICTURE_ID FROM PICTURES_ALBUMS WHERE ALBUM_ID=?");
            stmt.setInt(1, Integer.parseInt(album_id));
            rs = stmt.executeQuery();
            while (rs.next()) {
                int pic_id = rs.getInt("PICTURE_ID");
                items.add("Picture: " + pic_id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        stmt = null;
        rs = null;
        try {
            stmt = conn.prepareStatement("SELECT VIDEO_ID FROM VIDEOS_ALBUMS WHERE ALBUM_ID=?");
            stmt.setInt(1, Integer.parseInt(album_id));
            rs = stmt.executeQuery();
            while (rs.next()) {
                int video_id = rs.getInt("VIDEO_ID");
                items.add("Video: " + video_id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        listV.setItems(items);

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowAlbumController.MyAlbumCell(p_pane, myID, id, conn));
        } else {
            listV.setCellFactory(param -> new ShowAlbumController.AlbumCell(p_pane, myID, id, conn));
        }
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
    }

    public static String secondWord(String input) {
        return input.split(" ")[1];
    }

    class AlbumCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Media");

        public AlbumCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                        controller.initData(id, myID, "picture id", conn, Integer.parseInt(ShowAlbumController.this.album_id));

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

    class MyAlbumCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Media");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete");

        public MyAlbumCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                    if (firstWord(getItem()).equals("Picture:")) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../Media/show_photo.fxml"));
                            Pane view = null;
                            view = loader.load();
                            //access the controller and call a method
                            ShowPictureController controller = loader.getController();

                            //create query
                            controller.initData(id, myID, secondWord(getItem()), conn, Integer.parseInt(ShowAlbumController.this.album_id));

                            p_pane.getChildren().setAll(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../Media/show_video.fxml"));
                            Pane view = null;
                            view = loader.load();
                            //access the controller and call a method
                            ShowVideoController controller = loader.getController();

                            //create query
                            controller.initData(id, myID, secondWord(getItem()), conn, Integer.parseInt(ShowAlbumController.this.album_id));

                            p_pane.getChildren().setAll(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (firstWord(getItem()).equals("Picture:")) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../Media/edit_photo.fxml"));
                            Pane view = null;
                            view = loader.load();
                            //access the controller and call a method
                            EditPhotoController controller = loader.getController();

                            //create query
                            controller.initData(secondWord(getItem()), myID, conn, Integer.parseInt(album_id));

                            p_pane.getChildren().setAll(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../Media/edit_video.fxml"));
                            Pane view = null;
                            view = loader.load();
                            //access the controller and call a method
                            EditVideoController controller = loader.getController();

                            //create query
                            controller.initData(secondWord(getItem()), myID, conn, Integer.parseInt(album_id));

                            p_pane.getChildren().setAll(view);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (firstWord(getItem()).equals("Picture:")) {
                        PreparedStatement stmt=null;
                        ResultSet rs=null;
                        try {
                            stmt = conn.prepareStatement("DELETE FROM PICTURES_ALBUMS WHERE PICTURE_ID=?");
                            stmt.setInt(1, Integer.parseInt(secondWord(getItem())));
                            stmt.executeUpdate();

                            stmt = conn.prepareStatement("DELETE FROM PICTURE WHERE Pic_ID=?");
                            stmt.setInt(1, Integer.parseInt(secondWord(getItem())));
                            stmt.executeUpdate();

                            getListView().getItems().remove(getItem());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        PreparedStatement stmt=null;
                        ResultSet rs=null;
                        try {
                            stmt = conn.prepareStatement("DELETE FROM VIDEOS_ALBUMS WHERE VIDEO_ID=?");
                            stmt.setInt(1, Integer.parseInt(secondWord(getItem())));
                            stmt.executeUpdate();

                            stmt = conn.prepareStatement("DELETE FROM VIDEO WHERE Vid_ID=?");
                            stmt.setInt(1, Integer.parseInt(secondWord(getItem())));
                            stmt.executeUpdate();

                            getListView().getItems().remove(getItem());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
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

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_albums_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("album", myID, conn);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/albums_list.fxml"));
            Pane view = loader.load();

            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            controller.initData("album",id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
