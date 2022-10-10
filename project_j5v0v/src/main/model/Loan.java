package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

//Represents a loan of items from a library
public class Loan implements Writable {
    private ArrayList<Item> itemsLoaned;
    private String library;
    private LocalDate dueDate;
    private LocalDate loanDate;
    private Boolean isReturned;

    /*
     * REQUIRES: library has a non-zero length
     *           dueDate is after loanDate
     * EFFECTS: library name of loan is set to library
     *          due date for loan is set to dueDate
     *          itemsLoaned is set to empty list
     *          loanDate is set to the current date
     *          isReturned is set to false
     *          isOverdue is set to false
     */
    public Loan(String library, LocalDate dueDate) {
        this.itemsLoaned = new ArrayList<>();
        this.library = library;
        this.dueDate = dueDate;
        this.loanDate = LocalDate.now();
        this.isReturned = false;
    }

    //Getter methods
    public Boolean getReturned() {
        return isReturned;
    }

    public ArrayList<Item> getItemsLoaned() {
        return itemsLoaned;
    }

    public String getLibrary() {
        return library;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    /*
     * MODIFIES: this
     * EFFECTS: switches the value of isReturned
     *          between true and false
     */
    public void toggleReturned() {
        this.isReturned = !this.isReturned;
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes the due date to a new date
     */
    public void changeDueDate(LocalDate date) {
        this.dueDate = date;
    }

    /*
     * MODIFIES: this
     * EFFECTS: changes the loan date to a new date
     */
    public void changeLoanDate(LocalDate date) {
        this.loanDate = date;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a new loaned item to itemsLoaned
     */
    public void addItem(Item item) {
        this.itemsLoaned.add(item);
        EventLog.getInstance().logEvent(new Event("Added "
                + item.getTitle()
                + " to loan taken on "
                + loanDate.toString()));
    }

    /*
     * REQUIRES: title is the title of an item
     *           in the list
     * MODIFIES: this
     * EFFECTS: removes a loaned item from itemsLoaned
     *          if the item matches the given title
     */
    public void removeItem(String title) {
        Item selectedItem = null;
        for (Item item: this.itemsLoaned) {
            if (item.getTitle().equals(title)) {
                selectedItem = item;
            }
        }
        this.itemsLoaned.remove(selectedItem);
        EventLog.getInstance().logEvent(new Event("Removed "
                + title
                + " from loan taken on "
                + loanDate.toString()));
    }

    /*
     * EFFECTS: outputs the number of days until due date
     *          or returns -1 if overdue
     */
    public int dueDays() {
        if (this.loanDate.isAfter(this.dueDate)) {
            return -1;
        } else {
            return (int) ChronoUnit.DAYS.between(this.loanDate, this.dueDate);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("library", this.library);
        json.put("dueDate", this.dueDate);
        json.put("loanDate", this.loanDate);
        json.put("isReturned", this.isReturned);
        json.put("itemsLoaned", this.itemsToJson());
        return json;
    }

    // EFFECTS: outputs items loaned as Json
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : itemsLoaned) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
}
