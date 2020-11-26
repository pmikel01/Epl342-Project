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
import sample.MediaControllers.EditEventController;
import sample.MediaControllers.ShowEventController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchEvents;
import sample.Objects.SearchLinks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowEventListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchEvents events;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    private String id;
    private String myID;

    public void initData(SearchEvents events, String id, String myID) {
        this.events = events;
        this.id = id;
        this.myID = myID;

        listV.setItems(items);
        //loop
        items.add("event name");

        if (id.equals(myID)) {
            listV.setCellFactory(param -> new ShowEventListController.MyEventCell(p_pane, myID, id));
        } else {
            listV.setCellFactory(param -> new ShowEventListController.EventCell(p_pane, myID, id));
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

    static class MyEventCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Event");
        Pane pane2 = new Pane();
        Button button2 = new Button("Edit Event");
        Pane pane3 = new Pane();
        Button button3 = new Button("Delete Event");

        public MyEventCell(AnchorPane p_pane, String myID, String id) {
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
                        controller.initData(id, myID, "event id");

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
                        controller.initData("event id", myID);

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
        loader.setLocation(getClass().getResource("../SearchMedia/search_events.fxml"));
        Pane showProfParent = null;
        showProfParent = loader.load();
        //access the controller and call a method
        SearchEventController controller = loader.getController();

        //create query
        controller.initData(id, myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}