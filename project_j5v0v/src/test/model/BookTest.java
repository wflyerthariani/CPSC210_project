package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Book testBook;

    @BeforeEach
    void runBefore() {
        testBook = new Book("a book", "the author");
    }

    @Test
    void testInit() {
        assertEquals("a book", testBook.getTitle());
        assertEquals("the author", testBook.getAuthor());
    }

    @Test
    void testGetDetails() {
        assertEquals("title: a book\n"
                + "author: the author\n", testBook.getDetails());
    }
}
