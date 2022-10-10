package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents an item that can be loaned
public class Item implements Writable {
    protected String title;
    protected String itemType;

    /*
     * REQUIRES: title has a non-zero length
     * EFFECTS: title of item is set to title
     */
    public Item(String title) {
        this.title = title;
        this.itemType = "Item";
    }

    //Getter methods
    public String getTitle() {
        return title;
    }

    public String getItemType() {
        return itemType;
    }

    /*
     * EFFECTS: returns the relevant information for the
     *          item being borrowed, in this case just the title
     */
    public String getDetails() {
        return "title: "
                + this.title
                + "\n";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        return json;
    }
}

