package ui;

import java.io.FileNotFoundException;

//Represents the code that is run to execute the program
public class Main {
    //Runs the LoanTrackerApp otherwise returns an exception
    public static void main(String[] args) {
        try {
            new LoanTrackerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
