package sample;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.FxmlLoader;

        import java.net.URL;
        import java.util.ResourceBundle;

public class EditInfoController implements Initializable {

    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Other");

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleUpdateButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("scenes/my_profile");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderBox.setValue("Male");
        genderBox.setItems(genderList);
    }
}