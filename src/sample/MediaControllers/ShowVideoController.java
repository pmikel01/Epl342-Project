package sample.MediaControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import sample.MediaListsControllers.EditMediaListController;
import sample.MediaListsControllers.MediaListController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowVideoController implements Initializable {

    @FXML
    private AnchorPane p_pane;

    @FXML
    private MediaView playVideo;

    MediaPlayer mediaPlayer;

    private String id;
    private String video_id;
    private String myID;

    public void initData(String id, String myID, String video_id) {
        this.id = id;
        this.video_id = video_id;
        this.myID = myID;
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

    @FXML
    private void handleBackButton() throws IOException {
        if (myID.equals(id)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/edit_videos_list.fxml"));
            Pane showProfParent = null;
            showProfParent = loader.load();
            //access the controller and call a method
            EditMediaListController controller = loader.getController();

            //create query
            controller.initData("video", myID);

            p_pane.getChildren().setAll(showProfParent);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../MediaLists/videos_list.fxml"));
            Pane view = loader.load();

            //access the controller and call a method
            MediaListController controller = loader.getController();

            //create query
            controller.initData("video",id, myID);

            p_pane.getChildren().setAll(view);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String Vurl = "file:///home/pmikel01/Downloads/video1.mp4";
        Media media = new Media(Vurl);
        mediaPlayer = new MediaPlayer(media);
        playVideo.setMediaPlayer(mediaPlayer);
    }
}
