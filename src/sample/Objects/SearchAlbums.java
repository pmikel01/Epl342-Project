package sample.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class SearchAlbums {
    private SimpleStringProperty name, description, location, id;

    public SearchAlbums(String id, String name, String location, String description) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.description = new SimpleStringProperty(description);
    }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getId() {
        return id.get();
    }
}
