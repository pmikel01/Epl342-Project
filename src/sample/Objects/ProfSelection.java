package sample.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Date;

public class ProfSelection {
    private SimpleStringProperty firstName, lastName, location, education, managers;
    DatePicker birthD ;

    public ProfSelection(String firstName, String lastName, String location, String education, String managers, DatePicker birthD) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.location = new SimpleStringProperty(location);
        this.education = new SimpleStringProperty(education);
        this.managers = new SimpleStringProperty(managers);
        this.birthD = birthD;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getEducation() {
        return education.get();
    }

    public String getManagers() {
        return managers.get();
    }

    public DatePicker getBirthD() {
        return birthD;
    }
}
