package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a film that can be loaned
public class Film extends Item {
    private final String director;
    private final String producer;

    /*
     * REQUIRES: title, director, producer have a non-zero length
     * EFFECTS: title of film is set to title
     *          director of film is set to director
     *          producer of film is set to producer
     */
    public Film(String title, String director, String producer) {
        super(title);
        this.director = director;
        this.producer = producer;
        this.itemType = "Film";
    }

    //Getter methods
    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    /*
     * EFFECTS: returns the relevant information for the
     *          item being borrowed, including title, producer, director
     */
    @Override
    public String getDetails() {
        return "title: "
                + this.title
                + "\n"
                + "director: "
                + this.director
                + "\n"
                + "producer: "
                + this.producer
                + "\n";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("director", this.director);
        json.put("producer", this.producer);
        return json;
    }
}
