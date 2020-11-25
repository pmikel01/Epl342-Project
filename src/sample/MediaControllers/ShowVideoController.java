package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowVideoController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    private String id;

    @FXML
    private MediaView playVideo;

    MediaPlayer mediaPlayer;

    public void initData(String id) {
        this.id = id;
    }

    @FXML
    private void handleBackButton() {
//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("lists/videos_list");
//        p_pane.getChildren().setAll(view);
    }

    @FXML
    private void handlePlayButton() {
        if (mediaPlayer.getStatus()== MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
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
        String Vurl = "file:///home/pmikel01/Downloads/video1.mp4";
        Media media = new Media(Vurl);
        mediaPlayer = new MediaPlayer(media);
        playVideo.setMediaPlayer(mediaPlayer);
    }
}
