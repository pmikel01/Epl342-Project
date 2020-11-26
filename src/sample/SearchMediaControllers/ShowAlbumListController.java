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

    public void initData(SearchAlbums album, String id, String myID) {
        this.album = album;
        this.id = id;
        this.myID = myID;

        listV.setItems(items);
        //loop
        items.add("album name");

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowAlbumListController.MyAlbumCell(p_pane, myID, id));
        } else {
            listV.setCellFactory(param -> new ShowAlbumListController.AlbumCell(p_pane, myID, id));
        }
    }

    static class AlbumCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Album");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");

        public AlbumCell(AnchorPane p_pane, String myID, String id) {
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
                        controller.initData(id,myID, "album id");

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
                        controller.initData(id,myID, "album", "album id");

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

        public MyAlbumCell(AnchorPane p_pane, String myID, String id) {
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
                        controller.initData(id,myID, "album id");

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
                        controller.initData(id, myID, "album", "album id");

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
                        controller.initData("album id", myID);

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
        controller.initData(id, myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}