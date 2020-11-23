package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfileController implements Initializable {

    private ProfSelection pSel;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> profList;

    public void initData(ProfSelection profile) {
        pSel = profile;
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Profile");
        Pane pane2 = new Pane();
        Button button2 = new Button("Delete");

        public XCell() {
            super();

            hbox.getChildren().addAll(label, pane, button, pane2, button2);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
            HBox.setHgrow(pane2, Priority.ALWAYS);
            button2.setOnAction(event -> getListView().getItems().remove(getItem()));
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
        profList.setItems(items);
        items.add("First Prof");
        items.add("Second Prof");
        items.add("Third Prof");
        profList.setCellFactory(param -> new XCell());
    }
}
