package sample.searchMediaControllers;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.FxmlLoader;

        import java.net.URL;
        import java.util.ResourceBundle;

public class SearchLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleSearchLinkButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/search_links_list");
        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}