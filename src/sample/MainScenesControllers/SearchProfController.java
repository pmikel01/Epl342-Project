package sample.MainScenesControllers;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import sample.Objects.ProfSelection;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.Connection;
        import java.util.ResourceBundle;

public class SearchProfController implements Initializable {
    @FXML
    private AnchorPane p_pane ;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField location;

    @FXML
    private TextField education;

    @FXML
    private TextField managers;

    @FXML
    private DatePicker bd;

    @FXML
    private CheckBox advancedSearch;

    @FXML
    private RadioButton edu;

    @FXML
    private RadioButton man;

    @FXML
    private Label error_l;

    private String myID;
    private Connection conn;

    public void initData(String myID, Connection conn) {
        this.myID = myID;
        this.conn = conn;
    }

    @FXML
    private void handleSearchButton(ActionEvent event) throws IOException {
        if (advancedSearch.isSelected()) {
            if (edu.isSelected() && education.getText().isEmpty()) {
                error_l.setTextFill(Color.RED);
            } else if (man.isSelected() && managers.getText().isEmpty()){
                error_l.setTextFill(Color.RED);
            } else if (edu.isSelected()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/profiles_list.fxml"));
                Pane showProfParent = loader.load();

                //access the controller and call a method
                ShowProfListController controller = loader.getController();

                //create query
                ProfSelection prof = new ProfSelection(firstName.getText(),lastName.getText(),location.getText(), education.getText(),"",bd);
                controller.initData(myID, conn, prof);

                p_pane.getChildren().setAll(showProfParent);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../MediaLists/profiles_list.fxml"));
                Pane showProfParent = loader.load();

                //access the controller and call a method
                ShowProfListController controller = loader.getController();

                //create query
                ProfSelection prof = new ProfSelection(firstName.getText(),lastName.getText(),location.getText(), "",managers.getText(),bd);
                controller.initData(myID, conn, prof);

                p_pane.getChildren().setAll(showProfParent);
            }
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/profiles_list.fxml"));
            Pane showProfParent = loader.load();

            //access the controller and call a method
            ShowProfListController controller = loader.getController();

            //create query
            ProfSelection prof = new ProfSelection(firstName.getText(),lastName.getText(),location.getText(), "","",bd);
            controller.initData(myID, conn, prof);

            p_pane.getChildren().setAll(showProfParent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
