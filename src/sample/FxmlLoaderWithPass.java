package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FxmlLoaderWithPass {
    private Pane view;

    public Pane getPage(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fileName+".fxml"));
        view = loader.load();
        return view;
    }
}
