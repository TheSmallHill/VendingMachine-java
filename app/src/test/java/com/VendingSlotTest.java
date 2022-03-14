package com.vendingmachine;

import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.Item;
import com.vendingmachine.api.ItemImpl;

import com.vendingmachine.exception.IncorrectCostException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class VendingSlotTest
{
   @Test
   public void testGetters()
   {
      VendingSlot UUT = new VendingSlotImpl("B12", 45);
      
      assertEquals(UUT.getCode(), "B12");
      assertEquals(UUT.getCost(),  45);
      assertEquals(UUT.getItems().isEmpty(), true); // initialized to empty
   }
   
   @Test
   public void testLoadItem()
   {
      VendingSlot UUT = new VendingSlotImpl("B12", 45);
      
      // Create and load an item
      Item item = new ItemImpl("Famous Amos", 45);
      UUT.loadItem(item);
   }
}
