package sample.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName) {
        try {
            URL fileUrl = Main.class.getResource(fileName + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("FXML not found");
            }
            new FXMLLoader();
            view = FXMLLoader.load(fileUrl);
        }catch (Exception e) {
            System.out.println("No page error");
        }
        return view;
    }
}
