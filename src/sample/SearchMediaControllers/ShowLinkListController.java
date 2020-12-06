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
import sample.MediaControllers.EditLinkController;
import sample.MediaControllers.ShowLinkController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchLinks;
import sample.Objects.SearchPhotos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowLinkListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchLinks links;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String id;
    private String myID;
    private Connection conn;

    public void initData(SearchLinks links, String id, String myID, Connection conn) {
        this.links = links;
        this.id = id;
        this.myID = myID;
        this.conn = conn;

        if (!links.getName().isEmpty() && !links.getLink().isEmpty() && !links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(URL)=SOUNDEX(?) AND SOUNDEX(Caption)=SOUNDEX(?)");
                stmt.setString(1, links.getName());
                stmt.setString(2, links.getLink());
                stmt.setString(3, links.getCaption());
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
        } else if (links.getName().isEmpty() && !links.getLink().isEmpty() && !links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(URL)=SOUNDEX(?) AND SOUNDEX(Caption)=SOUNDEX(?)");
                stmt.setString(1, links.getLink());
                stmt.setString(2, links.getCaption());
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
        } else if (!links.getName().isEmpty() && links.getLink().isEmpty() && !links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(Caption)=SOUNDEX(?)");
                stmt.setString(1, links.getName());
                stmt.setString(2, links.getCaption());
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
        } else if (!links.getName().isEmpty() && !links.getLink().isEmpty() && links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(Name)=SOUNDEX(?) AND SOUNDEX(URL)=SOUNDEX(?)");
                stmt.setString(1, links.getName());
                stmt.setString(2, links.getLink());
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
        } else if (links.getName().isEmpty() && links.getLink().isEmpty() && !links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(Caption)=SOUNDEX(?)");
                stmt.setString(1, links.getCaption());
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
        } else if (!links.getName().isEmpty() && links.getLink().isEmpty() && links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(Name)=SOUNDEX(?)");
                stmt.setString(1, links.getName());
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
        } else if (links.getName().isEmpty() && !links.getLink().isEmpty() && links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK WHERE SOUNDEX(URL)=SOUNDEX(?)");
                stmt.setString(1, links.getLink());
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
        } else if (links.getName().isEmpty() && links.getLink().isEmpty() && links.getCaption().isEmpty()) {
            items = FXCollections.observableArrayList();
            PreparedStatement stmt=null;
            ResultSet rs=null;
            try {
                stmt = conn.prepareStatement("SELECT Link_ID,Name FROM LINK");
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
        }

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowLinkListController.MyLinkCell(p_pane, myID, id, conn));
        } else {
            listV.setCellFactory(param -> new ShowLinkListController.LinkCell(p_pane, myID, id, conn));
        }
    }

    public static String firstWord(String input) {
        return input.split(" ")[0];
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
                        controller.initData(id, myID,  firstWord(getItem()), conn);

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

    static class MyLinkCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Link");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Link");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Link");

        public MyLinkCell(AnchorPane p_pane, String myID, String id, Connection conn) {
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
                        controller.initData(id, myID,  firstWord(getItem()), conn);

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
                        controller.initData( firstWord(getItem()), myID, conn);

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
        loader.setLocation(getClass().getResource("../SearchMedia/search_links.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        SearchLinkController controller = loader.getController();

        //create query
        controller.initData(id, myID, conn);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}