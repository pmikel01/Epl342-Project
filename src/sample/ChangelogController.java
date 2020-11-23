package sample;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.Spinner;
        import javafx.scene.control.SpinnerValueFactory;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.FxmlLoader;

        import java.net.URL;
        import java.util.ResourceBundle;

public class ChangelogController implements Initializable {

    ObservableList<String> mediaList = FXCollections.observableArrayList("Albums", "Pictures", "Videos", "Events", "Links", "Friends");

    @FXML
    private ComboBox<String> mediaBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private Spinner<Integer> changeC;

    @FXML
    private Spinner<Integer> allChangeC;

    @FXML
    private void handleShowAllButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/changelog_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleShowSelectionButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/changelog_list");
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