package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    //Tests that an exception occurs when a non-existent file is attempted to be read
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Loan> loanList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    //Tests that the reader accurately reads an empty list of loans
    @Test
    void testReaderEmptyList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyList.json");
        try {
            List<Loan> loanList = reader.read();
            assertEquals(0, loanList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    //Tests that the reader accurately reads a list of empty loans
    @Test
    void testReaderEmptyLoans() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLoans.json");
        try {
            List<Loan> loanList = reader.read();
            assertEquals(2, loanList.size());
            checkLoan("test1",
                    LocalDate.parse("2021-04-05"),
                    LocalDate.parse("2023-04-03"),
                    false,
                    0,
                    loanList.get(0));
            checkLoan("test2",
                    LocalDate.parse("2022-03-05"),
                    LocalDate.parse("2022-03-03"),
                    true,
                    0,
                    loanList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    //Tests that the reader accurately reads a list of regular loans and their items
    @Test
    @SuppressWarnings("methodlength")
    void testReaderLoans() {
        JsonReader reader = new JsonReader("./data/testReaderLoans.json");
        try {
            List<Loan> loanList = reader.read();
            assertEquals(2, loanList.size());
            checkLoan("test1",
                    LocalDate.parse("2021-04-05"),
                    LocalDate.parse("2023-04-03"),
                    false,
                    2,
                    loanList.get(0));
            checkBook("title",
                    "author",
                    (Book) loanList.get(0).getItemsLoaned().get(0));
            checkFilm("titre",
                    "director",
                    "producer",
                    (Film) loanList.get(0).getItemsLoaned().get(1));
            checkLoan("test2",
                    LocalDate.parse("2022-03-05"),
                    LocalDate.parse("2022-03-03"),
                    true,
                    0,
                    loanList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}