package sample.MainScenesControllers;

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
import sample.Objects.ProfSelection;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfListController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private ProfSelection pSel;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> profList;

    String myID;

    public void initData(ProfSelection profile, String myID) {
        pSel = profile;
        this.myID = myID;

        profList.setItems(items);
        //loop
        items.add("903458   Pantelis Mikelli");
        profList.setCellFactory(param -> new XCell(p_pane, myID));
    }

    public AnchorPane getP_pane() {
        return p_pane;
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Profile");

        public XCell(AnchorPane p_pane, String myID) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
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
                        controller.initData("id", myID);

                        p_pane.getChildren().setAll(showProfParent);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}