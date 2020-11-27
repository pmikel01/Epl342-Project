package sample.MainScenesControllers;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.ComboBox;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.Main.FxmlLoader;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

public class EditInfoController implements Initializable {

    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Other");

    @FXML
    private ComboBox<String> genderBox;

    @FXML
    private AnchorPane p_pane ;

    private String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleUpdateButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MainScenes/my_profile.fxml"));
        Pane view = loader.load();

        //access the controller and call a method
        MyProfController controller = loader.getController();

        //create query
        controller.initData(myID);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderBox.setValue("Male");
        genderBox.setItems(genderList);
    }
}