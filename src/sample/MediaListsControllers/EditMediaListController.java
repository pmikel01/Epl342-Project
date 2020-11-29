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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import sample.MainScenesControllers.ShowProfController;
import sample.MediaControllers.*;
import sample.SearchMediaControllers.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditMediaListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String choose;
    private String myID;
    private Connection conn;

    public void initData(String choose, String myID, Connection conn) {
        this.choose = choose;
        this.myID = myID;
        this.conn = conn;

        if (choose.equals("album")) {
            listV.setItems(items);
            //loop
            items.add("album name");
            listV.setCellFactory(param -> new EditMediaListController.AlbumCell(p_pane, myID, conn));
        } else if (choose.equals("picture")) {
            listV.setItems(items);
            //loop
            items.add("picture name");
            listV.setCellFactory(param -> new EditMediaListController.PictureCell(p_pane, myID, conn));
        } else if (choose.equals("video")) {
            listV.setItems(items);
            //loop
            items.add("video name");
            listV.setCellFactory(param -> new EditMediaListController.VideoCell(p_pane, myID, conn));
        } else if (choose.equals("event")) {
            listV.setItems(items);
            //loop
            items.add("event name");
            listV.setCellFactory(param -> new EditMediaListController.EventCell(p_pane, myID, conn));
        } else if (choose.equals("link")) {
            listV.setItems(items);
            //loop
            items.add("link name");
            listV.setCellFactory(param -> new EditMediaListController.LinkCell(p_pane, myID, conn));
        } else if (choose.equals("interest")) {
            listV.setItems(items);
            //loop
            items.add("interest name");
            listV.setCellFactory(param -> new EditMediaListController.InterestCell(p_pane, myID, conn));
        } else if (choose.equals("quote")) {
            listV.setItems(items);
            //loop
            items.add("quote name");
            listV.setCellFactory(param -> new EditMediaListController.QuoteCell(p_pane, myID, conn));
        } else if (choose.equals("work")) {
            listV.setItems(items);
            //loop
            items.add("work name");
            listV.setCellFactory(param -> new EditMediaListController.WorkCell(p_pane, myID, conn));
        } else if (choose.equals("education")) {
            listV.setItems(items);
            //loop
            items.add("education name");
            listV.setCellFactory(param -> new EditMediaListController.EducationCell(p_pane, myID, conn));
        } else if (choose.equals("friend")) {
            listV.setItems(items);
            //loop
            items.add("friend name");
            listV.setCellFactory(param -> new EditMediaListController.FriendCell(p_pane, myID, conn));
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
        controller.initData(myID, myID, conn);

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
        controller.initData(myID, myID, conn);

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
        controller.initData(myID, myID, conn);

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
        controller.initData(myID, myID, conn);

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
        controller.initData(myID, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddAlbumButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_album.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddAlbumController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddEventButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_event.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddEventController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddLinkButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_link.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddLinkController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddPhotoButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_photo.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddPhotoController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleAddVideoButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Media/add_video.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        AddVideoController controller = loader.getController();

        //create query
        controller.initData(myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    public void pressAddQuoteButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Quote");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Quote: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @FXML
    public void pressAddWorkButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Manager");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Manager: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @FXML
    public void pressAddEducationButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Education");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Education: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @FXML
    public void pressAddInterestsButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Interest");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Interest: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    static class AlbumCell extends ListCell<String> {
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

        public AlbumCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        controller.initData(myID, myID, "album id", conn);

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
                        controller.initData(myID, myID, "album", "album id", conn);

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
                        controller.initData("album id", myID, conn);

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

    static class PictureCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Picture");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Picture");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Picture");

        public PictureCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        controller.initData(myID, myID, "picture id", conn);

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
                        controller.initData("photo id", myID, conn);

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

    static class VideoCell extends ListCell<String> {
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


        public VideoCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        controller.initData(myID, myID, "video id", conn);

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
                        controller.initData(myID, myID, "video", "video id", conn);

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
                        controller.initData("video id", myID, conn);

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

    static class EventCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Event");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Event");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Event");

        public EventCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        loader.setLocation(getClass().getResource("../Media/show_event.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowEventController controller = loader.getController();

                        //create query
                        controller.initData(myID, myID, "event id", conn);

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
                        loader.setLocation(getClass().getResource("../Media/edit_event.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EditEventController controller = loader.getController();

                        //create query
                        controller.initData("event id", myID, conn);

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

    static class LinkCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Link");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Link");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Link");

        public LinkCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        loader.setLocation(getClass().getResource("../Media/show_link.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowLinkController controller = loader.getController();

                        //create query
                        controller.initData(myID, myID, "link id", conn);

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
                        loader.setLocation(getClass().getResource("../Media/edit_link.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        EditLinkController controller = loader.getController();

                        //create query
                        controller.initData("link id", myID, conn);

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

    static class FriendCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Friend");
        Pane pane2 = new Pane();
        Button button2 = new Button("Remove Friend");

        public FriendCell(AnchorPane p_pane, String myID, Connection conn) {
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
                        loader.setLocation(getClass().getResource("../MainScenes/profile.fxml"));
                        Pane showProfParent = null;
                        showProfParent = loader.load();
                        //access the controller and call a method
                        ShowProfController controller = loader.getController();

                        //create query
                        controller.initData(myID, myID, conn);

                        p_pane.getChildren().setAll(showProfParent);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
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

    static class EducationCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Edit");
        Pane pane2 = new Pane();
        Button button2 = new Button("Delete");

        public EducationCell(AnchorPane p_pane, String myID, Connection conn) {
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
                    //edit
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
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

    static class WorkCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Edit");
        Pane pane2 = new Pane();
        Button button2 = new Button("Delete");

        public WorkCell(AnchorPane p_pane, String myID, Connection conn) {
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
                    //edit
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
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

    static class InterestCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Edit");
        Pane pane2 = new Pane();
        Button button2 = new Button("Delete");

        public InterestCell(AnchorPane p_pane, String myID, Connection conn) {
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
                    //edit
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
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

    static class QuoteCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Edit");
        Pane pane2 = new Pane();
        Button button2 = new Button("Delete");

        public QuoteCell(AnchorPane p_pane, String myID, Connection conn) {
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
                    //edit
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}