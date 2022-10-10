package persistence;

import model.Loan;
import model.Film;
import model.Book;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    //Test to check if a Loan's information is valid
    protected void checkLoan(String library,
                             LocalDate dueDate,
                             LocalDate loanDate,
                             Boolean isReturned,
                             int length,
                             Loan loan) {
        assertEquals(library, loan.getLibrary());
        assertEquals(dueDate, loan.getDueDate());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals(isReturned, loan.getReturned());
        assertEquals(length, loan.getItemsLoaned().size());
    }

    //Test to check if a Book's information is valid
    protected void checkBook(String title,
                             String author,
                             Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    //Test to check if a Film's information is valid
    protected void checkFilm(String title,
                             String director,
                             String producer,
                             Film film) {
        assertEquals(title, film.getTitle());
        assertEquals(director, film.getDirector());
        assertEquals(producer, film.getProducer());
    }
}
