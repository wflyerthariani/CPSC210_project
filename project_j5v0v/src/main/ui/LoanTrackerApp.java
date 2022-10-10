package ui;

import model.Book;
import model.Film;
import model.Item;
import model.Loan;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Loan Tracker application
public class LoanTrackerApp {
    private static final String JSON_STORE = "./data/loans.json";
    private List<Loan> loanList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tracker application and constructs list of loans
    public LoanTrackerApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loanList = new ArrayList<>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runLoanTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLoanTracker() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayLoans();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays loans to user
    private void displayLoans() {
        System.out.println("\nMake a selection:");
        int counter = 1;
        for (Loan loan: this.loanList) {
            String line = counter + " -> Loan borrowed from " + loan.getLibrary()
                    + " on " + loan.getLoanDate().toString();
            System.out.println(line);
            counter++;
        }
        System.out.println("a -> add loan");
        System.out.println("q -> quit");
        System.out.println("l -> load save");
        System.out.println("s -> save\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.matches("\\d+")) {
            int value = Integer.parseInt(command);
            if (value > this.loanList.size() || value == 0) {
                System.out.println("\nInvalid command\n");
            } else {
                value -= 1;
                Loan loan = this.loanList.get(value);
                displayLoan(loan);
            }
        } else if (command.equals("a")) {
            addLoan();
        } else if (command.equals("s")) {
            saveLoans();
        } else if (command.equals("l")) {
            loadList();
        } else {
            System.out.println("\nInvalid command\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new loan to the list
    private void addLoan() {
        System.out.println("What is the name of the library?\n");
        String library = input.next();
        System.out.println("How many days until it is due?\n");
        String days = input.next();
        this.loanList.add(new Loan(library, LocalDate.now().plusDays(Integer.parseInt(days))));
    }

    //EFFECTS: displays relevant information about loan to the user
    @SuppressWarnings("methodlength")
    private void displayLoan(Loan loan) {
        System.out.println("\nLibrary: " + loan.getLibrary());
        System.out.println("Date of loan: " + loan.getLoanDate().toString());
        System.out.println("Date due: " + loan.getDueDate().toString());
        System.out.println("Returned: " + loan.getReturned().toString());
        if (!loan.getReturned()) {
            if (loan.dueDays() >= 0) {
                System.out.println("Due in: " + loan.dueDays() + " days");
            } else {
                System.out.println("Overdue");
            }
        }
        System.out.println("\n\nItems borrowed");
        for (Item item : loan.getItemsLoaned()) {
            System.out.println(item.getDetails());
            System.out.println("\n");
        }
        System.out.println("\nMake a selection");
        System.out.println("a -> add new item");
        System.out.println("r -> remove item");
        System.out.println("c -> change return status");
        System.out.println("d -> change due date");
        System.out.println("b -> go back to menu");
        String loanOption = input.next();
        handleOption(loan, loanOption);
    }

    // MODIFIES: this
    // EFFECTS: processes user input for loan options
    @SuppressWarnings("methodlength")
    private void handleOption(Loan loan, String loanOption) {
        switch (loanOption) {
            case "a":
                addNewItem(loan);
                break;
            case "r":
                removeLoanItem(loan);
                break;
            case "c":
                loan.toggleReturned();
                displayLoan(loan);
                break;
            case "d":
                System.out.println("How many days until it is due?\n");
                String days = input.next();
                loan.changeDueDate(LocalDate.now().plusDays(Integer.parseInt(days)));
                displayLoan(loan);
                break;
            case "b":
                System.out.println("Returning to menu");
                break;
            default:
                System.out.println("Invalid input");
                displayLoan(loan);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new Item to a given loan
    private void addNewItem(Loan loan) {
        System.out.println("Would you like to add a book (b) or film (f)?");
        String choice = input.next();
        if (choice.equals("b")) {
            System.out.println("Enter the title");
            String title = input.next();
            System.out.println("Enter the author");
            String author = input.next();
            loan.addItem(new Book(title, author));
            displayLoan(loan);
        } else if (choice.equals("f")) {
            System.out.println("Enter the title");
            String title = input.next();
            System.out.println("Enter the director");
            String director = input.next();
            System.out.println("Enter the producer");
            String producer = input.next();
            loan.addItem(new Film(title, director, producer));
            displayLoan(loan);
        } else {
            System.out.println("Invalid command");
            addNewItem(loan);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an item from a given loan
    private void removeLoanItem(Loan loan) {
        System.out.println("Type in the title of the item to remove");
        String choice = input.next();
        loan.removeItem(choice);
        displayLoan(loan);
    }

    // EFFECTS: saves the list of loans to file
    private void saveLoans() {
        try {
            jsonWriter.open();
            jsonWriter.write(loanList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from file
    private void loadList() {
        try {
            loanList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
