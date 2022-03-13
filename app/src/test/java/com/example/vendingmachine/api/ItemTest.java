package com.example.vendingmachine.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test
    public void testGetters() {
        Item item = new ItemImpl("Butterfinger", 125);
        assertEquals(125, item.getCost());
        assertEquals("Butterfinger", item.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalDescription() {
        new ItemImpl(null, 125);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCost() {
        new ItemImpl("Butterfinger", -1);
    }
}
