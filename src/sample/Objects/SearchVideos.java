package sample.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class SearchVideos {
    private SimpleStringProperty message, description, id;
    private Integer length,likes;

    public SearchVideos(String id, String message, String description, Integer length, Integer likes) {
        this.id = new SimpleStringProperty(id);
        this.message = new SimpleStringProperty(message);
        this.description = new SimpleStringProperty(description);
        this.length = length;
        this.likes = likes;
    }

    public String getId() {
        return id.get();
    }

    public String getMessage() {
        return message.get();
    }

    public String getDescription() {
        return description.get();
    }

    public Integer getLength() {
        return length;
    }

    public Integer getLikes() {
        return likes;
    }

}
