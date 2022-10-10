package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a book that can be loaned
public class Book extends Item {
    private final String author;

    /*
     * REQUIRES: title, author have a non-zero length
     * EFFECTS: title of book is set to title
     *          author of book is set to author
     */
    public Book(String title, String author) {
        super(title);
        this.author = author;
        this.itemType = "Book";
    }

    //Getter methods
    public String getAuthor() {
        return author;
    }

    /*
     * EFFECTS: returns the relevant information for the
     *          item being borrowed, including title and author
     */
    @Override
    public String getDetails() {
        return "title: "
                + this.title
                + "\n"
                + "author: "
                + this.author
                + "\n";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("author", this.author);
        return json;
    }
}
