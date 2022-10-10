package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    //Tests that an exception occurs when an invalid file name is passed
    @Test
    void testWriterInvalidFile() {
        try {
            List<Loan> loans = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    //Tests that an empty list of loans is written correctly
    @Test
    void testWriterEmptyList() {
        try {
            List<Loan> loans = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(loans);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            loans = reader.read();
            assertEquals(0, loans.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    //Tests that it can write a list of empty loans
    @Test
    @SuppressWarnings("methodlength")
    void testWriterEmptyLoans() {
        try {
            List<Loan> loans = new ArrayList<>();
            Loan loan1 = new Loan("test1", LocalDate.parse("2021-04-05"));
            loan1.changeLoanDate(LocalDate.parse("2023-04-03"));
            Loan loan2 = new Loan("test2", LocalDate.parse("2022-03-05"));
            loan2.changeLoanDate(LocalDate.parse("2022-03-03"));
            loan2.toggleReturned();
            loans.add(loan1);
            loans.add(loan2);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLoans.json");
            writer.open();
            writer.write(loans);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLoans.json");
            loans = reader.read();
            assertEquals(2, loans.size());
            checkLoan("test1",
                    LocalDate.parse("2021-04-05"),
                    LocalDate.parse("2023-04-03"),
                    false,
                    0,
                    loans.get(0));
            checkLoan("test2",
                    LocalDate.parse("2022-03-05"),
                    LocalDate.parse("2022-03-03"),
                    true,
                    0,
                    loans.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    //Tests that it can write a list of regular loans
    @Test
    @SuppressWarnings("methodlength")
    void testWriterLoans() {
        try {
            List<Loan> loans = new ArrayList<>();
            Loan loan1 = new Loan("test1", LocalDate.parse("2021-04-05"));
            loan1.changeLoanDate(LocalDate.parse("2023-04-03"));
            Book book = new Book("title", "author");
            Film film = new Film("titre", "director", "producer");
            loan1.addItem(book);
            loan1.addItem(film);
            Loan loan2 = new Loan("test2", LocalDate.parse("2022-03-05"));
            loan2.changeLoanDate(LocalDate.parse("2022-03-03"));
            loan2.toggleReturned();
            loans.add(loan1);
            loans.add(loan2);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLoans.json");
            writer.open();
            writer.write(loans);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLoans.json");
            loans = reader.read();
            assertEquals(2, loans.size());
            checkLoan("test1",
                    LocalDate.parse("2021-04-05"),
                    LocalDate.parse("2023-04-03"),
                    false,
                    2,
                    loans.get(0));
            checkBook("title",
                    "author",
                    (Book) loans.get(0).getItemsLoaned().get(0));
            checkFilm("titre",
                    "director",
                    "producer",
                    (Film) loans.get(0).getItemsLoaned().get(1));
            checkLoan("test2",
                    LocalDate.parse("2022-03-05"),
                    LocalDate.parse("2022-03-03"),
                    true,
                    0,
                    loans.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
