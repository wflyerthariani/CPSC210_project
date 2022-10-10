package persistence;

import model.Book;
import model.Film;
import model.Item;
import model.Loan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads loan list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads list of loans from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Loan> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return addLoans(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // MODIFIES: loanList
    // EFFECTS: parses loans from JSON object and adds them to loanList
    private List<Loan> addLoans(JSONObject jsonObject) {
        List<Loan> loans = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("loans");
        for (Object json : jsonArray) {
            JSONObject nextLoan = (JSONObject) json;
            loans.add(parseLoan(nextLoan));
        }
        return loans;
    }

    // EFFECTS: parses loan from JSON object and returns it
    private Loan parseLoan(JSONObject jsonObject) {
        String library = jsonObject.getString("library");
        LocalDate dueDate = LocalDate.parse(jsonObject.getString("dueDate"));
        Loan loan = new Loan(library, dueDate);
        loan.changeLoanDate(LocalDate.parse(jsonObject.getString("loanDate")));
        boolean isReturned = jsonObject.getBoolean("isReturned");
        if (isReturned) {
            loan.toggleReturned();
        }
        addItems(loan, jsonObject);
        return loan;
    }

    // MODIFIES: loan
    // EFFECTS: parses items from JSON object and adds them to loan
    private void addItems(Loan loan, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("itemsLoaned");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            if (nextItem.length() == 2) {
                addBook(loan, nextItem);
            } else {
                addFilm(loan, nextItem);
            }
        }
    }

    // MODIFIES: loan
    // EFFECTS: parses book from JSON object and adds it to loan
    private void addBook(Loan loan, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        Item item = new Book(title, author);
        loan.addItem(item);
    }

    // MODIFIES: loan
    // EFFECTS: parses film from JSON object and adds it to loan
    private void addFilm(Loan loan, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String director = jsonObject.getString("director");
        String producer = jsonObject.getString("producer");
        Item item = new Film(title, director, producer);
        loan.addItem(item);
    }
}
