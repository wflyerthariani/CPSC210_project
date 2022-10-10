package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {
    private Film testFilm;

    @BeforeEach
    void runBefore() {
        testFilm = new Film("a film",
                "the director",
                "the producer");
    }

    @Test
    void testInit() {
        assertEquals("a film", testFilm.getTitle());
        assertEquals("the director", testFilm.getDirector());
        assertEquals("the producer", testFilm.getProducer());
    }

    @Test
    void testGetDetails() {
        assertEquals("title: a film\n"
                + "director: the director\n"
                + "producer: the producer\n", testFilm.getDetails());
    }
}
