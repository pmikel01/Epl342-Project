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
import sample.MediaControllers.ShowLinkController;
import sample.MediaListsControllers.MediaListController;
import sample.Objects.SearchLinks;
import sample.Objects.SearchPhotos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowLinkListController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    SearchLinks links;

    @FXML
    private ListView<String> listV ;

    private ObservableList<String> items = FXCollections.observableArrayList();

    public void initData(SearchLinks links) {
        this.links = links;

        listV.setItems(items);
        //loop
        items.add("link name");
        listV.setCellFactory(param -> new ShowLinkListController.LinkCell(p_pane));
    }

    static class LinkCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Link");

        public LinkCell(AnchorPane p_pane) {
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
                        controller.initData("link id");

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

//    @FXML
//    private void handleSearchAlbumButton() {
//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("../MediaLists/search_albums_list");
//        p_pane.getChildren().setAll(view);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}