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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditMediaListController implements Initializable {

    private static final String SQL_INSERT_QUOTE = "INSERT INTO [dbo].QUOTES (QuoteText) VALUES (?)";
    private static final String SQL_INSERT_PROF_QUOTE = "INSERT INTO [dbo].PROFILES_QUOTES (QUOTES_ID,USER_ID) VALUES (?,?)";
    private static final String SQL_INSERT_WORK = "INSERT INTO [dbo].WORK (Description) VALUES (?)";
    private static final String SQL_INSERT_PROF_WORK = "INSERT INTO [dbo].PROFILES_WORK (WORK_ID,USER_ID) VALUES (?,?)";
    private static final String SQL_INSERT_EDUCATION = "INSERT INTO [dbo].EDUCATION (InstituteName) VALUES (?)";
    private static final String SQL_INSERT_PROF_EDUCATION = "INSERT INTO [dbo].PROFILES_EDUCATION (EDUCATION_ID,USER_ID) VALUES (?,?)";
    private static final String SQL_INSERT_INTEREST = "INSERT INTO [dbo].INTERESTS (Name) VALUES (?)";
    private static final String SQL_INSERT_PROF_INTEREST = "INSERT INTO [dbo].PROFILES_INTERESTS (INTERESTS_ID,USER_ID) VALUES (?,?)";


    private static final String SQL_UPDATE_ALBUM = "UPDATE ALBUM SET Album_ID=?,Title=?,Description=?,Privacy=?,Count=?,User_ID=?,Taken=?,ChangeLog=? WHERE ID=?";
    private static final String SQL_UPDATE_PICTURE = "UPDATE PICTURE SET Pic_ID=?,Source=?,Height=?,Width=?,User_ID=?,Link=?,Likes=?,Taken=?,ChangeLog=? WHERE ID=?";
    private static final String SQL_UPDATE_VIDEO = "UPDATE VIDEO SET Vid_ID=?,Title=?,Description=?,Length=?,Likes=?,User_ID=? WHERE ID=?";
    private static final String SQL_UPDATE_EVENT = "UPDATE EVENT SET Event_ID=?,Name=?,Description=?,StartTime=?,EndTime=?,Privacy=?,Venue=?,Location=?,Creator=?,ChangeLog=? WHERE ID=?";
    private static final String SQL_UPDATE_LINK = "UPDATE LINK SET Link_ID=?,URL=?,Name=?,Caption=?,Description=?,Message=?,User_ID=?,ChangeLog=? WHERE ID=?";

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
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Album_ID,Title FROM ALBUM WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.AlbumCell(p_pane, myID, conn));
        } else if (choose.equals("picture")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Pic_ID FROM PICTURE WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pic_id = rs.getInt("Pic_ID");
                    items.add(pic_id+"");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            listV.setItems(items);
            listV.setCellFactory(param -> new EditMediaListController.PictureCell(p_pane, myID, conn));
        } else if (choose.equals("video")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Vid_ID,Title FROM VIDEO WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.VideoCell(p_pane, myID, conn));
        } else if (choose.equals("event")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Event_ID,Name FROM EVENT WHERE Creator=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.EventCell(p_pane, myID, conn));
        } else if (choose.equals("link")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.LinkCell(p_pane, myID, conn));
        } else if (choose.equals("interest")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT INTERESTS_ID FROM PROFILES_INTERESTS WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.InterestCell(p_pane, myID, conn));
        } else if (choose.equals("quote")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT QUOTES_ID FROM PROFILES_QUOTES WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.QuoteCell(p_pane, myID, conn));
        } else if (choose.equals("work")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT WORK_ID FROM PROFILES_WORK WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.WorkCell(p_pane, myID, conn));
        } else if (choose.equals("education")) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT EDUCATION_ID FROM PROFILES_EDUCATION WHERE USER_ID=?");
                stmt.setInt(1, Integer.parseInt(myID));
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
            listV.setCellFactory(param -> new EditMediaListController.EducationCell(p_pane, myID, conn));
        } else if (choose.equals("friend")) {
            items = FXCollections.observableArrayList();
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
        if(input.getText() != null && input.getText().toString().length() != 0) {
            String quote = input.getText();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            int quote_id=0;
            try {
                stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                stmt.setString(1,quote);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    quote_id = rs.getInt("Quote_ID");

                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_QUOTES WHERE QUOTES_ID=? AND USER_ID=?");
                    stmt.setInt(1, quote_id);
                    stmt.setInt(2, Integer.parseInt(myID));
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_QUOTE);
                        stmt.setInt(1, quote_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                } else {
                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement(SQL_INSERT_QUOTE);
                    stmt.setString(1, quote);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                    stmt.setString(1,quote);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        quote_id = rs.getInt("Quote_ID");

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_QUOTE);
                        stmt.setInt(1, quote_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            initData("quote",myID,conn);
        }
    }

    //    WORK (Description)
//    PROFILES_WORK (WORK_ID,USER_ID)
//    EDUCATION (InstituteName)
//    PROFILES_EDUCATION (EDUCATION_ID,USER_ID)
//    INTERESTS (Name)
//    PROFILES_INTERESTS (INTERESTS_ID,USER_ID)

    @FXML
    public void pressAddWorkButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Manager");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Manager: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0) {
            String work = input.getText();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            int work_id=0;
            try {
                stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                stmt.setString(1,work);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    work_id = rs.getInt("Work_ID");

                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                    stmt.setInt(1, work_id);
                    stmt.setInt(2, Integer.parseInt(myID));
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_WORK);
                        stmt.setInt(1, work_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                } else {
                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement(SQL_INSERT_WORK);
                    stmt.setString(1, work);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                    stmt.setString(1,work);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        work_id = rs.getInt("Work_ID");

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_WORK);
                        stmt.setInt(1, work_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            initData("work",myID,conn);
        }
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
        if(input.getText() != null && input.getText().toString().length() != 0) {
            String education = input.getText();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            int education_id=0;
            try {
                stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                stmt.setString(1,education);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    education_id = rs.getInt("Education_ID");

                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                    stmt.setInt(1, education_id);
                    stmt.setInt(2, Integer.parseInt(myID));
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_EDUCATION);
                        stmt.setInt(1, education_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                } else {
                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement(SQL_INSERT_EDUCATION);
                    stmt.setString(1, education);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                    stmt.setString(1,education);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        education_id = rs.getInt("Education_ID");

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_EDUCATION);
                        stmt.setInt(1, education_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            initData("education",myID,conn);
        }
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
        if(input.getText() != null && input.getText().toString().length() != 0) {
            String interest = input.getText();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            int interest_id=0;
            try {
                stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                stmt.setString(1,interest);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    interest_id = rs.getInt("Interest_ID");

                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_INTERESTS WHERE INTERESTS_ID=? AND USER_ID=?");
                    stmt.setInt(1, interest_id);
                    stmt.setInt(2, Integer.parseInt(myID));
                    rs = stmt.executeQuery();
                    if (!rs.next()) {
                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_INTEREST);
                        stmt.setInt(1, interest_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                } else {
                    stmt=null;
                    rs=null;
                    stmt = conn.prepareStatement(SQL_INSERT_INTEREST);
                    stmt.setString(1, interest);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                    stmt.setString(1,interest);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        interest_id = rs.getInt("Interest_ID");

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement(SQL_INSERT_PROF_INTEREST);
                        stmt.setInt(1, interest_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            initData("interest",myID,conn);
        }
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

    class EducationCell extends ListCell<String> {
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
                    TextInputDialog textIn = new TextInputDialog();
                    textIn.setTitle("Edit Education");
                    textIn.setHeaderText(null);
                    textIn.setGraphic(null);

                    textIn.getDialogPane().setContentText("Education: ");
                    Optional<String> result = textIn.showAndWait();
                    TextField input = textIn.getEditor();
                    if(input.getText() != null && input.getText().toString().length() != 0) {
                        String education = getItem().toLowerCase();
                        String new_education = input.getText().toLowerCase();

                        if (!education.equals(new_education)) {
                            PreparedStatement stmt=null;
                            ResultSet rs=null;
                            int new_education_id=0;
                            try {
                                stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");

                                stmt.setString(1,new_education);
                                rs = stmt.executeQuery();
                                if (rs.next()) {
                                    new_education_id = rs.getInt("Education_ID");

                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                    stmt.setInt(1, new_education_id);
                                    stmt.setInt(2, Integer.parseInt(myID));
                                    rs = stmt.executeQuery();
                                    if (!rs.next()) {
                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_EDUCATION);
                                        stmt.setInt(1, new_education_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int education_id=0;
                                        if (rs.next()) {
                                            education_id = rs.getInt("Education_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                        stmt.setInt(1, education_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                } else {
                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement(SQL_INSERT_EDUCATION);
                                    stmt.setString(1, input.getText());
                                    stmt.executeUpdate();

                                    stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                                    stmt.setString(1,input.getText());
                                    rs = stmt.executeQuery();
                                    if (rs.next()) {
                                        new_education_id = rs.getInt("Education_ID");

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_EDUCATION);
                                        stmt.setInt(1, new_education_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int work_id=0;
                                        if (rs.next()) {
                                            work_id = rs.getInt("Education_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                                        stmt.setInt(1, work_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                }
                                EditMediaListController.this.initData("education",myID,conn);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt=null;
                    ResultSet rs=null;
                    try {
                        stmt = conn.prepareStatement("SELECT Education_ID FROM EDUCATION WHERE InstituteName=?");
                        stmt.setString(1,getItem());
                        rs = stmt.executeQuery();
                        int education_id=0;
                        if (rs.next()) {
                            education_id = rs.getInt("Education_ID");
                        }

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement("DELETE FROM PROFILES_EDUCATION WHERE EDUCATION_ID=? AND USER_ID=?");
                        stmt.setInt(1, education_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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

    class WorkCell extends ListCell<String> {
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
                    TextInputDialog textIn = new TextInputDialog();
                    textIn.setTitle("Edit Work");
                    textIn.setHeaderText(null);
                    textIn.setGraphic(null);

                    textIn.getDialogPane().setContentText("Work: ");
                    Optional<String> result = textIn.showAndWait();
                    TextField input = textIn.getEditor();
                    if(input.getText() != null && input.getText().toString().length() != 0) {
                        String work = getItem().toLowerCase();
                        String new_work = input.getText().toLowerCase();

                        if (!work.equals(new_work)) {
                            PreparedStatement stmt=null;
                            ResultSet rs=null;
                            int new_work_id=0;
                            try {
                                stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");

                                stmt.setString(1,new_work);
                                rs = stmt.executeQuery();
                                if (rs.next()) {
                                    new_work_id = rs.getInt("Work_ID");

                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                    stmt.setInt(1, new_work_id);
                                    stmt.setInt(2, Integer.parseInt(myID));
                                    rs = stmt.executeQuery();
                                    if (!rs.next()) {
                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_WORK);
                                        stmt.setInt(1, new_work_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int interest_id=0;
                                        if (rs.next()) {
                                            interest_id = rs.getInt("Work_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                        stmt.setInt(1, interest_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                } else {
                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement(SQL_INSERT_WORK);
                                    stmt.setString(1, input.getText());
                                    stmt.executeUpdate();

                                    stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                                    stmt.setString(1,input.getText());
                                    rs = stmt.executeQuery();
                                    if (rs.next()) {
                                        new_work_id = rs.getInt("Work_ID");

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_WORK);
                                        stmt.setInt(1, new_work_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int work_id=0;
                                        if (rs.next()) {
                                            work_id = rs.getInt("Work_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                                        stmt.setInt(1, work_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                }
                                EditMediaListController.this.initData("work",myID,conn);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt=null;
                    ResultSet rs=null;
                    try {
                        stmt = conn.prepareStatement("SELECT Work_ID FROM WORK WHERE Description=?");
                        stmt.setString(1,getItem());
                        rs = stmt.executeQuery();
                        int work_id=0;
                        if (rs.next()) {
                            work_id = rs.getInt("Work_ID");
                        }

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement("DELETE FROM PROFILES_WORK WHERE WORK_ID=? AND USER_ID=?");
                        stmt.setInt(1, work_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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

    class InterestCell extends ListCell<String> {
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
                    TextInputDialog textIn = new TextInputDialog();
                    textIn.setTitle("Edit Interest");
                    textIn.setHeaderText(null);
                    textIn.setGraphic(null);

                    textIn.getDialogPane().setContentText("Interest: ");
                    Optional<String> result = textIn.showAndWait();
                    TextField input = textIn.getEditor();
                    if(input.getText() != null && input.getText().toString().length() != 0) {
                        String interest = getItem().toLowerCase();
                        String new_interest = input.getText().toLowerCase();

                        if (!interest.equals(new_interest)) {
                            PreparedStatement stmt=null;
                            ResultSet rs=null;
                            int new_interest_id=0;
                            try {
                                stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");

                                stmt.setString(1,new_interest);
                                rs = stmt.executeQuery();
                                if (rs.next()) {
                                    new_interest_id = rs.getInt("Interest_ID");

                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_INTERESTS WHERE INTERESTS_ID=? AND USER_ID=?");
                                    stmt.setInt(1, new_interest_id);
                                    stmt.setInt(2, Integer.parseInt(myID));
                                    rs = stmt.executeQuery();
                                    if (!rs.next()) {
                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_INTEREST);
                                        stmt.setInt(1, new_interest_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int interest_id=0;
                                        if (rs.next()) {
                                            interest_id = rs.getInt("Interest_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_INTERESTS WHERE INTERESTS_ID=? AND USER_ID=?");
                                        stmt.setInt(1, interest_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                } else {
                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement(SQL_INSERT_INTEREST);
                                    stmt.setString(1, input.getText());
                                    stmt.executeUpdate();

                                    stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                                    stmt.setString(1,input.getText());
                                    rs = stmt.executeQuery();
                                    if (rs.next()) {
                                        new_interest_id = rs.getInt("Interest_ID");

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_INTEREST);
                                        stmt.setInt(1, new_interest_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int interest_id=0;
                                        if (rs.next()) {
                                            interest_id = rs.getInt("Interest_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_INTERESTS WHERE INTERESTS_ID=? AND USER_ID=?");
                                        stmt.setInt(1, interest_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                }
                                EditMediaListController.this.initData("interest",myID,conn);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt=null;
                    ResultSet rs=null;
                    try {
                        stmt = conn.prepareStatement("SELECT Interest_ID FROM INTERESTS WHERE Name=?");
                        stmt.setString(1,getItem());
                        rs = stmt.executeQuery();
                        int interest_id=0;
                        if (rs.next()) {
                            interest_id = rs.getInt("Interest_ID");
                        }

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement("DELETE FROM PROFILES_INTERESTS WHERE INTERESTS_ID=? AND USER_ID=?");
                        stmt.setInt(1, interest_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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

    class QuoteCell extends ListCell<String> {
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
                    TextInputDialog textIn = new TextInputDialog();
                    textIn.setTitle("Edit Quote");
                    textIn.setHeaderText(null);
                    textIn.setGraphic(null);

                    textIn.getDialogPane().setContentText("Quote: ");
                    Optional<String> result = textIn.showAndWait();
                    TextField input = textIn.getEditor();
                    if(input.getText() != null && input.getText().toString().length() != 0) {
                        String quote = getItem().toLowerCase();
                        String new_quote = input.getText().toLowerCase();

                        if (!quote.equals(new_quote)) {
                            PreparedStatement stmt=null;
                            ResultSet rs=null;
                            int new_quote_id=0;
                            try {
                                stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");

                                stmt.setString(1,new_quote);
                                rs = stmt.executeQuery();
                                if (rs.next()) {
                                    new_quote_id = rs.getInt("Quote_ID");

                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement("SELECT USER_ID FROM PROFILES_QUOTES WHERE QUOTES_ID=? AND USER_ID=?");
                                    stmt.setInt(1, new_quote_id);
                                    stmt.setInt(2, Integer.parseInt(myID));
                                    rs = stmt.executeQuery();
                                    if (!rs.next()) {
                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_QUOTE);
                                        stmt.setInt(1, new_quote_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int quote_id=0;
                                        if (rs.next()) {
                                            quote_id = rs.getInt("Quote_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_QUOTES WHERE QUOTES_ID=? AND USER_ID=?");
                                        stmt.setInt(1, quote_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                } else {
                                    stmt=null;
                                    rs=null;
                                    stmt = conn.prepareStatement(SQL_INSERT_QUOTE);
                                    stmt.setString(1, input.getText());
                                    stmt.executeUpdate();

                                    stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                                    stmt.setString(1,input.getText());
                                    rs = stmt.executeQuery();
                                    if (rs.next()) {
                                        new_quote_id = rs.getInt("Quote_ID");

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement(SQL_INSERT_PROF_QUOTE);
                                        stmt.setInt(1, new_quote_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                                        stmt.setString(1,getItem());
                                        rs = stmt.executeQuery();
                                        int quote_id=0;
                                        if (rs.next()) {
                                            quote_id = rs.getInt("Quote_ID");
                                        }

                                        stmt=null;
                                        rs=null;
                                        stmt = conn.prepareStatement("DELETE FROM PROFILES_QUOTES WHERE QUOTES_ID=? AND USER_ID=?");
                                        stmt.setInt(1, quote_id);
                                        stmt.setInt(2, Integer.parseInt(myID));
                                        stmt.executeUpdate();
                                    }
                                }
                                EditMediaListController.this.initData("quote",myID,conn);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    PreparedStatement stmt=null;
                    ResultSet rs=null;
                    try {
                        stmt = conn.prepareStatement("SELECT Quote_ID FROM QUOTES WHERE QuoteText=?");
                        stmt.setString(1,getItem());
                        rs = stmt.executeQuery();
                        int quote_id=0;
                        if (rs.next()) {
                            quote_id = rs.getInt("Quote_ID");
                        }

                        stmt=null;
                        rs=null;
                        stmt = conn.prepareStatement("DELETE FROM PROFILES_QUOTES WHERE QUOTES_ID=? AND USER_ID=?");
                        stmt.setInt(1, quote_id);
                        stmt.setInt(2, Integer.parseInt(myID));
                        stmt.executeUpdate();

                        getListView().getItems().remove(getItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}