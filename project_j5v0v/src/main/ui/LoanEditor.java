package ui;

import model.Book;
import model.EventLog;
import model.Event;
import model.Film;
import model.Loan;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;



// Class to create the GUI
public class LoanEditor extends JFrame implements ActionListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/loans.json";
    private List<Loan> loanList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: initializes empty list of loans and initializes JSON reader and writer
    public LoanEditor() {
        super("Loan Tracker App");
        initializeGraphics();
        loanList = new ArrayList<>();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    //EFFECTS: initializes the frame to be changed in the GUI
    //MODIFIES: this
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString() + "\n");
                }
                System.exit(0);
            }
        });
        setLocationRelativeTo(this);
        setVisible(true);
        loadMenu();
    }

    //EFFECTS: displays menu with options to load data or start empty
    //MODIFIES: this
    private void loadMenu() {
        JButton loadButton = new JButton("Load Previous Data");
        JButton newButton = new JButton("New File");
        loadButton.setBounds((LoanEditor.WIDTH / 2) - 100,
                (LoanEditor.HEIGHT / 2) - 55,
                200,
                50);
        loadButton.setActionCommand("loadData");
        loadButton.addActionListener(this);
        newButton.setBounds((LoanEditor.WIDTH / 2) - 100,
                (LoanEditor.HEIGHT / 2) + 5,
                200,
                50);
        newButton.setActionCommand("newData");
        newButton.addActionListener(this);
        add(loadButton);
        add(newButton);
    }

    //EFFECTS: Handles button presses and relevant events
    //MODIFIES: this
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("loadData")) {
            loadList();
            getContentPane().removeAll();
            repaint();
            displayLoans();
        } else if (e.getActionCommand().equals("newData")) {
            getContentPane().removeAll();
            repaint();
            displayLoans();
        } else if (e.getActionCommand().equals("save")) {
            saveList();
        } else if (e.getActionCommand().equals("chart")) {
            createChart();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads data from file
    private void loadList() {
        try {
            this.loanList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: Displays all loans and relevant information and options to change
    //MODIFIES: this
    @SuppressWarnings("methodlength")
    private void displayLoans() {
        JPanel panel = new JPanel();
        JTable table = new JTable();
        table.setModel(new LoanTableModel(loanList));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    displayLoanFrame(row);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row >= 0 && row < loanList.size()) {
                        loanList.remove(row);
                    }
                    displayLoans();
                }
            }
        });
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));
        panel.add(scrollPane);
        JButton saveButton = new JButton("Save data");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        JButton chartButton = new JButton("Current statistics");
        chartButton.setActionCommand("chart");
        chartButton.addActionListener(this);

        panel.add(new JLabel("Left click to select loan and right click to delete or"
                + " enter information in the following fields to add a new item or"
                + " or save data or see your borrowing statistics"));
        panel.add(new JLabel("Library name: "));
        JTextField libraryField = new JTextField("", 20);
        panel.add(libraryField);
        panel.add(new JLabel("How many days till due: "));
        JTextField dateField = new JTextField("", 20);
        panel.add(dateField);
        JButton newDateButton = new JButton("Add new loan");
        newDateButton.addActionListener(e -> {
            loanList.add(new Loan(libraryField.getText(),
                    LocalDate.now().plusDays(Integer.parseInt(dateField.getText()))));
            displayLoans();
            setVisible(true);
        });
        panel.add(newDateButton);
        panel.add(saveButton);
        panel.add(chartButton);
        add(panel);
        setVisible(true);
    }

    //EFFECTS: Creates a new frame for the loan being looked at
    private void displayLoanFrame(int row) {
        Loan loan = loanList.get(row);
        JFrame frame = new JFrame("Loan from: " + loan.getLibrary());
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        displayLoan(frame, loan);
    }

    //EFFECTS: Fills in loan related information and options to change loan
    @SuppressWarnings("methodlength")
    private void displayLoan(JFrame frame, Loan loan) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTable table = new JTable();
        table.setModel(new ItemTableModel(loan.getItemsLoaned()));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    loan.removeItem(loan.getItemsLoaned().get(row).getTitle());
                    displayLoan(frame, loan);
                }
            }
        });
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));
        panel.add(scrollPane);
        JPanel buttonPanel = new JPanel();
        JPanel subpanel = new JPanel();
        subpanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 4));
        JTextField titleField = new JTextField("", 20);
        JTextField authorField = new JTextField("", 20);
        JTextField directorField = new JTextField("", 20);
        JTextField producerField = new JTextField("", 20);
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(e -> {
            loan.addItem(new Book(titleField.getText(), authorField.getText()));
            displayLoan(frame, loan);
            frame.setVisible(true);
        });
        JButton addFilmButton = new JButton("Add Film");
        addFilmButton.addActionListener(f -> {
            loan.addItem(new Film(titleField.getText(), directorField.getText(), producerField.getText()));
            displayLoan(frame, loan);
            frame.setVisible(true);
        });
        subpanel.add(new JLabel("Double click an item to delete it "
                + "or fill in the relevant information to add a new book or film"
                + " or change the return status of the loan or change its due date"));
        subpanel.add(new JLabel("Title: "));
        subpanel.add(titleField);
        subpanel.add(new JLabel("Author: "));
        subpanel.add(authorField);
        subpanel.add(new JLabel("Director: "));
        subpanel.add(directorField);
        subpanel.add(new JLabel("Producer: "));
        subpanel.add(producerField);
        subpanel.add(addBookButton);
        subpanel.add(addFilmButton);
        buttonPanel.add(subpanel);
        panel.add(buttonPanel);

        JPanel subpanel2 = new JPanel();
        JButton returnedButton = new JButton("Change Return Status");
        returnedButton.addActionListener(g -> {
            loan.toggleReturned();
            displayLoan(frame, loan);
            frame.setVisible(true);
        });
        subpanel2.add(new JLabel("Returned: " + loan.getReturned().toString()));
        subpanel2.add(returnedButton);
        panel.add(subpanel2);

        JPanel subpanel3 = new JPanel();
        subpanel3.add(new JLabel("Due on: " + loan.getDueDate().toString()));
        subpanel3.add(new JLabel("How many days till due: "));
        JTextField dateField = new JTextField("", 20);
        subpanel3.add(dateField);
        JButton newDateButton = new JButton("Change Due Date");
        newDateButton.addActionListener(h -> {
            loan.changeDueDate(LocalDate.now().plusDays(Integer.parseInt(dateField.getText())));
            displayLoan(frame, loan);
            frame.setVisible(true);
        });
        subpanel3.add(newDateButton);
        panel.add(subpanel3);

        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: saves current data to JSON
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(loanList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: Generates chart to show loan statistics in new window
    @SuppressWarnings("methodlength")
    private void createChart() {
        JFrame f = new JFrame();
        f.setSize(400, 300);
        f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
        double[] values = new double[3];
        String[] names = new String[3];
        Month currentMonth = LocalDate.now().getMonth();
        int counter1 = 0;
        for (Loan loan:loanList) {
            if (loan.getLoanDate().getMonth() == currentMonth) {
                counter1 += 1;
            }
        }
        values[0] = counter1;
        names[0] = "Loans taken this month";

        int counter2 = 0;
        for (Loan loan:loanList) {
            if (loan.getDueDate().getMonth() == currentMonth) {
                counter2 += 1;
            }
        }
        values[1] = counter2;
        names[1] = "Loans due this month";

        int counter3 = 0;
        for (Loan loan:loanList) {
            if (loan.getReturned()) {
                counter3 += 1;
            }
        }
        values[2] = counter3;
        names[2] = "Loans Returned";

        f.getContentPane().add(new ChartPanel(values, names, "Details for this month"));

        f.setVisible(true);
    }

    //EFFECTS: main method to run the GUI
    public static void main(String[] args) {
        new LoanEditor();
    }
}
