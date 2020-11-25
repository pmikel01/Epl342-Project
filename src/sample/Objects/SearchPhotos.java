package sample.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class SearchPhotos {
    private SimpleStringProperty height, width, id;
    private Integer likes;

    public SearchPhotos(String id, String height, String width, Integer likes) {
        this.id = new SimpleStringProperty(id);
        this.height = new SimpleStringProperty(height);
        this.width = new SimpleStringProperty(width);
        this.likes = likes;
    }

    public String getHeight() {
        return height.get();
    }

    public String getWidth() {
        return width.get();
    }

    public String getId() {
        return id.get();
    }

    public Integer getLikes() {
        return likes;
    }
}
