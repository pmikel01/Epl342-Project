package sample.SearchMediaControllers;

        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.Main.FxmlLoader;
        import sample.Objects.SearchEvents;
        import sample.Objects.SearchLinks;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Date;
        import java.util.ResourceBundle;

public class SearchLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    private String id;

    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleSearchLinkButton() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/search_links_list.fxml"));
        Pane view = null;
        view = loader.load();
        //access the controller and call a method
        ShowLinkListController controller = loader.getController();

        //create query
        SearchLinks link = new SearchLinks("","", "", "");
        controller.initData(link);

        p_pane.getChildren().setAll(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}