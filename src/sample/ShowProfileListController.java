package sample;

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

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowProfileController implements Initializable {

    @FXML
    public AnchorPane p_pane;

    private ProfSelection pSel;

    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    ListView<String> profList;

    public void initData(ProfSelection profile) {
        pSel = profile;

        profList.setItems(items);
        //loop
        items.add("903458   Pantelis Mikelli");
        profList.setCellFactory(param -> new XCell(p_pane));
    }

    public AnchorPane getP_pane() {
        return p_pane;
    }

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("");
        Pane pane = new Pane();
        Button button = new Button("Show Profile");

        public XCell(AnchorPane p_pa) {
            super();

            button.setCursor(Cursor.HAND);
            hbox.getChildren().addAll(label, pane, button);
            hbox.setAlignment(Pos.CENTER);
            HBox.setHgrow(pane, Priority.ALWAYS);
//            button.setOnAction(event -> getListView().getItems().remove(getItem()));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
//                    FxmlLoader object = new FxmlLoader();
//                    Pane view = object.getPage("scenes/profile");
//                    p_pa.getChildren().setAll(view);
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("lists/profiles_list.fxml"));
                    Pane showProfParent = null;
                    try {
                        showProfParent = loader.load();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    //access the controller and call a method
                    ShowProfileController controller = loader.getController();

                    //create query
                    ProfSelection prof = new ProfSelection("Pantelis","sd", "df", "gh", "fg",new Date());
                    controller.initData(prof);

                    p_pa.getChildren().setAll(showProfParent);
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
