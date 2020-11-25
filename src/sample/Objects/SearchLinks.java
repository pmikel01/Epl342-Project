package sample.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class SearchLinks {
    private SimpleStringProperty name, link, caption, id;

    public SearchLinks(String id, String name, String link, String caption) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.link = new SimpleStringProperty(link);
        this.caption = new SimpleStringProperty(caption);

    }

    public String getName() {
        return name.get();
    }

    public String getCaption() {
        return caption.get();
    }

    public String getLink() {
        return link.get();
    }

    public String getId() {
        return id.get();
    }
}
