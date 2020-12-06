package sample.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

import java.util.Date;

public class SearchEvents {
    private SimpleStringProperty name, venue, location, id;
    DatePicker date ;

    public SearchEvents(String id, String name, String location, String venue, DatePicker date) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.venue = new SimpleStringProperty(venue);
        this.date = date;
    }

    public String getName() {
        return name.get();
    }

    public String getVenue() {
        return venue.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getId() {
        return id.get();
    }

    public DatePicker getDate() {
        return date;
    }

}
