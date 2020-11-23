package sample;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Date;
        import java.util.ResourceBundle;

public class SearchProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("lists/profiles_list.fxml"));
        Pane showProfParent = loader.load();

        //access the controller and call a method
        ShowProfileController controller = loader.getController();

        //create query
        ProfSelection prof = new ProfSelection("as","sd", "df", "gh", "fg",new Date());
        controller.initData(prof);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
