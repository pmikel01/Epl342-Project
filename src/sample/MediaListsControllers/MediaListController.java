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
        import sample.Main.FxmlLoader;
        import sample.MainScenesControllers.ShowProfController;
        import sample.MediaControllers.*;
        import sample.SearchMediaControllers.*;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ResourceBundle;

public class MediaListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String choose;
    private String id;
    private String myID;
    private Connection conn;

    public void initData(String choose, String id, String myID, Connection conn) {
        this.choose = choose;
        this.id = id;
        this.myID = myID;
        this.conn = conn;

        if (choose.equals("album")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title FROM ALBUM WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int album_id = rs.getInt("Album_ID");
                    String title = rs.getString("Title");
                    String line = album_id + "  " + title;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new AlbumCell(p_pane, myID, id, conn));
        } else if (choose.equals("picture")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID FROM PICTURE WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");
                    items.add(pic_id+"");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new PictureCell(p_pane, myID, id, conn));
        } else if (choose.equals("video")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Vid_ID,Title FROM VIDEO WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int video_id = rs.getInt("Vid_ID");
                    String title = rs.getString("Title");
                    String line = video_id + "  " + title;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new VideoCell(p_pane, myID, id, conn));
        } else if (choose.equals("event")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name FROM EVENT WHERE Creator=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int event_id = rs.getInt("Event_ID");
                    String name = rs.getString("Name");
                    String line = event_id + "  " + name;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new EventCell(p_pane, myID, id, conn));
        } else if (choose.equals("link")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int link_id = rs.getInt("Link_ID");
                    String name = rs.getString("Name");
                    String line = link_id + "  " + name;
                    items.add(line);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new LinkCell(p_pane, myID, id, conn));
        } else if (choose.equals("interest")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT INTERESTS_ID FROM PROFILES_INTERESTS WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int interest_id = rs.getInt("INTERESTS_ID");

                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT Name FROM INTERESTS WHERE Interest_ID=?");
                    stmt2.setInt(1, interest_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String interest_text = rs2.getString("Name");
                        items.add(interest_text);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("quote")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT QUOTES_ID FROM PROFILES_QUOTES WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int quote_id = rs.getInt("QUOTES_ID");

                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT QuoteText FROM QUOTES WHERE Quote_ID=?");
                    stmt2.setInt(1, quote_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String quote_text = rs2.getString("QuoteText");
                        items.add(quote_text);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("work")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT WORK_ID FROM PROFILES_WORK WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int work_id = rs.getInt("WORK_ID");

                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT Description FROM WORK WHERE Work_ID=?");
                    stmt2.setInt(1, work_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String work_text = rs2.getString("Description");
                        items.add(work_text);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
        } else if (choose.equals("education")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT EDUCATION_ID FROM PROFILES_EDUCATION WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int education_id = rs.getInt("EDUCATION_ID");

                    PreparedStatement stmt2=null;
                    ResultSet rs2=null;
                    stmt2 = conn.prepareStatement("SELECT InstituteName FROM EDUCATION WHERE Education_ID=?");
                    stmt2.setInt(1, education_id);
                    rs2 = stmt2.executeQuery();
                    if (rs2.next()) {
                        String education_text = rs2.getString("InstituteName");
                        items.add(education_text);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
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
        controller.initData(id, myID, conn);

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
        controller.initData(id, myID, conn);

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
        controller.initData(id, myID, conn);

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
        controller.initData(id, myID, conn);

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
        controller.initData(id, myID, conn);

        p_pane.getChildren().setAll(view);
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
                        controller.initData(id, myID, firstWord(getItem()), conn);

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
                        controller.initData(id, myID, "album", firstWord(getItem()), conn);

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

    static class VideoCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Video");
        Pane pane2 = new Pane();
        Button button2 = new Button("Show Comments");

        public VideoCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                        loader.setLocation(getClass().getResource("../MediaLists/comments.fxml"));
                        Pane view = null;
                        view = loader.load();
                        //access the controller and call a method
                        ShowCommentsController controller = loader.getController();

                        //create query
                        controller.initData(id, myID, "video", firstWord(getItem()), conn);

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

        public EventCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                        controller.initData(id, myID, firstWord(getItem()), conn);

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

        public LinkCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                        controller.initData(id, myID, firstWord(getItem()), conn);

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
        controller.initData(id, myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}