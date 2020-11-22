package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class QewiController implements Initializable {

    @FXML
    public void pressAddQuoteButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Quote");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Quote: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
            if(input.getText() != null && input.getText().toString().length() != 0)
                System.out.println("correct");
            else
                System.out.println("");
    }

    @FXML
    public void pressAddWorkButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Manager");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Manager: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @FXML
    public void pressAddEducationButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Education");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Education: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @FXML
    public void pressAddInterestsButton(ActionEvent event) {
        TextInputDialog textIn = new TextInputDialog();
        textIn.setTitle("New Interest");
        textIn.setHeaderText(null);
        textIn.setGraphic(null);

        textIn.getDialogPane().setContentText("Interest: ");
        Optional<String> result = textIn.showAndWait();
        TextField input = textIn.getEditor();
        if(input.getText() != null && input.getText().toString().length() != 0)
            System.out.println("correct");
        else
            System.out.println("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
