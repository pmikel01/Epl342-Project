package sample.MainScenesControllers;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import sample.Objects.ProfSelection;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Date;
        import java.util.ResourceBundle;

public class SearchProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    public String myID;

    public void initData(String myID) {
        this.myID = myID;
    }

    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../MediaLists/profiles_list.fxml"));
        Pane showProfParent = loader.load();

        //access the controller and call a method
        ShowProfListController controller = loader.getController();

        //create query
        ProfSelection prof = new ProfSelection("Pantelis","sd", "df", "gh", "fg",new Date());
        controller.initData(prof,myID);

        p_pane.getChildren().setAll(showProfParent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
