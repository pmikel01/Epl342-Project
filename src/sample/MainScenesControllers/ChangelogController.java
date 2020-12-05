package sample.MainScenesControllers;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.Spinner;
        import javafx.scene.control.SpinnerValueFactory;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.Main.FxmlLoader;
        import sample.MediaListsControllers.ChangeLogListController;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.util.ResourceBundle;

public class ChangelogController implements Initializable {

    ObservableList<String> mediaList = FXCollections.observableArrayList("Albums", "Pictures", "Videos", "Events", "Links");

    @FXML
    private ComboBox<String> mediaBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> changeC;

    @FXML
    private Spinner<Integer> allChangeC;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleShowAllButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/changelog_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        ChangeLogListController controller = loader.getController();

        //create query
        controller.initData("all", myID, conn, allChangeC.getValue());

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleShowSelectionButton() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/changelog_list.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        ChangeLogListController controller = loader.getController();

        String selection = mediaBox.getValue();

        //create query
        //combobox selection
        controller.initData(selection, myID, conn, changeC.getValue());

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> changeCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,1);
        this.changeC.setValueFactory(changeCount);
        SpinnerValueFactory<Integer> allChangeCount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,999,1);
        this.allChangeC.setValueFactory(allChangeCount);

        mediaBox.setValue("Albums");
        mediaBox.setItems(mediaList);
    }
}