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
import sample.MediaControllers.ShowEventController;
import sample.Objects.SearchEvents;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class EventSearchController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String choose;
    private String myID;
    private Connection conn;

    private SearchEvents events;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listV ;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;

        listV.setItems(items);
        //loop
        items.add("Pantelis Mikelli");
        listV.setCellFactory(param -> new EventSearchController.XCell(p_pane, myID, events, conn));
    }

    public void initData(SearchEvents events, String myID) {
        this.events = events;
        this.myID = myID;

        listV.setItems(items);
        //loop
        items.add("Pantelis Mikelli");
        listV.setCellFactory(param -> new EventSearchController.XCell(p_pane, myID, events, conn));
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Event");
        Pane pane2 = new Pane();
        Button button2 = new Button("Going");
        Pane pane3 = new Pane();
        Button button3 = new Button("Interested");
        Pane pane4 = new Pane();
        Button button4 = new Button("Not Going");

        public XCell(AnchorPane p_pane, String myID, SearchEvents events, Connection conn) {
            super();

            button.setCursor(Cursor.HAND);
            button2.setCursor(Cursor.HAND);
            button3.setCursor(Cursor.HAND);
            button4.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button, pane2, button2, pane3, button3, pane4, button4);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(5);
            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
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
                        controller.initData(myID, myID, "event id", events, conn);

                        p_pane.getChildren().setAll(view);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    //message
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    //message
                }
            });
            button4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    //message
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
