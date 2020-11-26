package sample.MediaListsControllers;

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
import sample.MainScenesControllers.ShowProfController;
import sample.MediaControllers.*;
import sample.SearchMediaControllers.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeLogListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String choose;
    private String id;
    private String myID;

    public void initData(String choose, String id, String myID) {
        this.choose = choose;
        this.id = id;
        this.myID = myID;

        if (choose.equals("album")) {
            listV.setItems(items);
            //loop
            items.add("album name");
            listV.setCellFactory(param -> new AlbumCell(p_pane, myID, id));
        } else if (choose.equals("picture")) {
            listV.setItems(items);
            //loop
            items.add("picture name");
            listV.setCellFactory(param -> new PictureCell(p_pane, myID, id));
        } else if (choose.equals("video")) {
            listV.setItems(items);
            //loop
            items.add("video name");
            listV.setCellFactory(param -> new VideoCell(p_pane, myID, id));
        } else if (choose.equals("event")) {
            listV.setItems(items);
            //loop
            items.add("event name");
            listV.setCellFactory(param -> new EventCell(p_pane, myID, id));
        } else if (choose.equals("link")) {
            listV.setItems(items);
            //loop
            items.add("link name");
            listV.setCellFactory(param -> new LinkCell(p_pane, myID, id));
        } else if (choose.equals("interest")) {
            listV.setItems(items);
            //loop
            items.add("interest name");
        } else if (choose.equals("quote")) {
            listV.setItems(items);
            //loop
            items.add("quote name");
        } else if (choose.equals("work")) {
            listV.setItems(items);
            //loop
            items.add("work name");
        } else if (choose.equals("education")) {
            listV.setItems(items);
            //loop
            items.add("education name");
        }
    }

    @FXML
    private void handleSearchAlbumButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../SearchMedia/search_albums.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchAlbumController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchEventButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../SearchMedia/search_events.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchEventController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchLinkButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../SearchMedia/search_links.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchLinkController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchPhotoButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../SearchMedia/search_photos.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchPicController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleSearchVideoButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../SearchMedia/search_videos.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        SearchVidController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(view);
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
                        controller.initData(id, myID, "album id");

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
                        controller.initData(id, myID, "album", "album ID");

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

    static class PictureCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Picture");

        public PictureCell(AnchorPane p_pane, String myID, String id) {
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
                        controller.initData(id, myID, "picture id");

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

    static class VideoCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Video");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");

        public VideoCell(AnchorPane p_pane, String myID, String id) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
            hbox.setSpacing(5);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/show_video.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowVideoController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, "video id");

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
                        controller.initData(id, myID, "video", "video id");

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

    static class EventCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Event");

        public EventCell(AnchorPane p_pane, String myID, String id) {
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
                        loader.setLocation(getClass().getResource("../Media/show_event.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowEventController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, "event id");

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

    static class LinkCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Link");

        public LinkCell(AnchorPane p_pane, String myID, String id) {
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
                        loader.setLocation(getClass().getResource("../Media/show_link.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowLinkController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, "link id");

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

    @FXML
    private void handleBackButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/profile.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        ShowProfController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}