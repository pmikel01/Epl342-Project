package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RegisterController {
    ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female", "Other");

    @FXML
    public Button closeButton;

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public Button signInButton;

    @FXML
    public void pressSignInButtonButton(ActionEvent event) {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private ComboBox genderBox;

    @FXML
    private void initialize() {
        genderBox.setValue("Male");
        genderBox.setItems(genderList);
    }

}
