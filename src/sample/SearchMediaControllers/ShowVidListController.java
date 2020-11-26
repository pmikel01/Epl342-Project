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
import sample.MainScenesControllers.ShowProfController;
import sample.MediaControllers.EditVideoController;
import sample.MediaControllers.ShowCommentsController;
import sample.MediaControllers.ShowVideoController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchAlbums;
import sample.Objects.SearchVideos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowVidListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchVideos videos;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String id;
    private String myID;

    public void initData(SearchVideos videos, String id, String myID) {
        this.videos = videos;
        this.id = id;
        this.myID = myID;

        listV.setItems(items);
        //loop
        items.add("video name");

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowVidListController.MyVideoCell(p_pane, myID, id));
        } else {
            listV.setCellFactory(param -> new ShowVidListController.VideoCell(p_pane, myID, id));
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

    static class MyVideoCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Video");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");
        Pane pane3 = new Pane();
        Button button3 = new Button("Edit Video");
        Pane pane4 = new Pane();
        Button button4 = new Button("Delete Video");


        public MyVideoCell(AnchorPane p_pane, String myID, String id) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            button4.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2, pane3, button3, pane4, button4);
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
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../Media/edit_video.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EditVideoController controller = loader.getController();

                        //create query
                        controller.initData("video id", myID);

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
        loader.setLocation(getClass().getResource("../SearchMedia/search_videos.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        SearchVidController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}