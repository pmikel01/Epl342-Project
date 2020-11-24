package sample.ShowMedia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.FxmlLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowVideoController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    ObservableList<String> privacyList = FXCollections.observableArrayList("OPEN", "CLOSED", "FRIEND", "NETWORK");

    private String id;

    @FXML
    private Button play;

    @FXML
    private Button pause;

    @FXML
    private Button stop;

    @FXML
    private MediaView playVideo;

    MediaPlayer mediaPlayer;

    @FXML
    private ComboBox<String> privacyBox;


    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleBackButton() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("lists/videos_list");
        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePlayButton() {
        mediaPlayer.play();
    }

    @FXML
    private void handlePauseButton() {
        mediaPlayer.pause();
    }

    @FXML
    private void handleStopButton() {
        mediaPlayer.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media videoFile = new Media(
                "file:///E:/Users/Dennis/....Channel%20Trailer.mp4");

        mediaPlayer = new MediaPlayer(videoFile);
        mediaPlayer.setVolume(0.1);

        playVideo.setMediaPlayer(mediaPlayer);


    }
}
