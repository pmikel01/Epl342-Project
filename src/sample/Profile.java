package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Profile {
    private SimpleStringProperty id;

    public Profile(String id) {
        this.id = new SimpleStringProperty(id);
    }

    public String getFirstName() {
        return id.get();
    }
}
