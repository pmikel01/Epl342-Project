package sample;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;

        import java.net.URL;
        import java.util.ResourceBundle;

public class SearchProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleSearchButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/profiles_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
