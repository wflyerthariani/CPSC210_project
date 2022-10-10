package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {
    private Loan testLoan;
    private LocalDate testDate;
    private LocalDate today;
    private Book testBook;
    private Film testFilm;

    @BeforeEach
    void runBefore() {
        testDate = LocalDate.now().plusDays(1);
        today = LocalDate.now();
        testLoan = new Loan("UBC library", testDate);
        testBook = new Book("the book", "the author");
        testFilm = new Film("the film", "the director", "the producer");
    }

    @Test
    void testInit() {
        assertEquals("UBC library", testLoan.getLibrary());
        assertTrue(testLoan.getItemsLoaned().isEmpty());
        assertEquals(0, ChronoUnit.DAYS.between(today, testLoan.getLoanDate()));
        assertEquals(testDate, testLoan.getDueDate());
        assertFalse(testLoan.getReturned());
    }

    @Test
    void testToggleReturned() {
        testLoan.toggleReturned();
        assertTrue(testLoan.getReturned());
        testLoan.toggleReturned();
        assertFalse(testLoan.getReturned());
    }

    @Test
    void testChangeDueDate() {
        testLoan.changeDueDate(testDate.plusDays(2));
        assertEquals(testDate.plusDays(2), testLoan.getDueDate());
    }

    @Test
    void testAddItemEmpty() {
        testLoan.addItem(testBook);
        ArrayList<Item> comparisonList = new ArrayList<>();
        comparisonList.add(testBook);
        assertEquals(comparisonList, testLoan.getItemsLoaned());
    }

    @Test
    void testAddItemExisting() {
        testLoan.addItem(testBook);
        testLoan.addItem(testFilm);
        List<Item> comparisonList = new ArrayList<>();
        comparisonList.add(testBook);
        comparisonList.add(testFilm);
        assertEquals(comparisonList, testLoan.getItemsLoaned());
    }

    @Test
    void testRemoveItem() {
        testLoan.addItem(testBook);
        testLoan.addItem(testFilm);
        List<Item> comparisonList = new ArrayList<>();
        comparisonList.add(testFilm);
        testLoan.removeItem("the book");
        assertEquals(comparisonList, testLoan.getItemsLoaned());
    }

    @Test
    void testDueDaysNotOverdue(){
        assertEquals(1, testLoan.dueDays());
    }

    @Test
    void testDueDaysOverdue(){
        testLoan.changeDueDate(today.minusDays(2));
        assertEquals(-1, testLoan.dueDays());
    }

    @Test
    void testChangeLoanDate(){
        testLoan.changeLoanDate(testDate.plusDays(1));
        assertEquals(testDate.plusDays(1), testLoan.getLoanDate());
    }
}