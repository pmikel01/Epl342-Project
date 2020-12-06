package sample.SearchMediaControllers;

        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.Main.FxmlLoader;
        import sample.MediaListsControllers.EditMediaListController;
        import sample.MediaListsControllers.MediaListController;
        import sample.Objects.SearchAlbums;
        import sample.Objects.SearchEvents;
        import sample.Objects.SearchLinks;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.util.Date;
        import java.util.ResourceBundle;

public class SearchLinkController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField name;

    @FXML
    private TextField link;

    @FXML
    private TextField caption;

    private String id;
    private String myID;
    private Connection conn;

    public void initData(String id, String myID, Connection conn) {
        this.id = id;
        this.myID = myID;
        this.conn = conn;
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
        SearchLinks link2 = new SearchLinks("",name.getText(), link.getText(),caption.getText());
        controller.initData(link2, id, myID, conn);

        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_links_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            SearchAlbums album = new SearchAlbums("","", "", "");
            controller.initData("link", myID, conn);

            p_pane.getChildren().setAll(view);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/links_list.fxml"));
            Pane view = null;
            view = loader.load();
            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            SearchAlbums album = new SearchAlbums("","", "", "");
            controller.initData("link", id, myID, conn);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}