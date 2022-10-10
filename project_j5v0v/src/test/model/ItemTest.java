package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item("testItem");
    }

    @Test
    void testInit() {
        assertEquals("testItem", testItem.getTitle());
    }

    @Test
    void testGetDetails() {
        assertEquals("title: testItem\n", testItem.getDetails());
    }

    @Test
    void testGetItemType() {
        assertEquals("Item", testItem.getItemType());
    }

    @Test
    void testToJson() {
        JSONObject expected = new JSONObject();
        expected.put("title", "testItem");
        assertEquals(expected.toString(), testItem.toJson().toString());
    }
}
