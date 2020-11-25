package sample.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class SearchEvents {
    private SimpleStringProperty name, venue, location, id;
    Date date ;

    public SearchEvents(String id, String name, String location, String venue, Date date) {
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

    public Date getDate() {
        return date;
    }

}
