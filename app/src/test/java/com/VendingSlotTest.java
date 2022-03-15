package com.vendingmachine;

import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.Item;
import com.vendingmachine.api.ItemImpl;

import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.ItemNotAvailableException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

public class VendingSlotTest
{
   VendingSlot UUT_ = new VendingSlotImpl("B12", 45);
   
   public void setupSlot(String description, int cost) throws IncorrectCostException
   {
      // Create and load an item
      Item item = new ItemImpl(description, cost);
      UUT_.loadItem(item);
   }
   
   @Test
   public void testGetters()
   {     
      assertEquals(UUT_.getCode(), "B12");
      assertEquals(UUT_.getCost(),  45);
      assertEquals(UUT_.getItems().isEmpty(), true); // initialized to empty
   }
   
   @Test
   public void testLoadItem() throws IncorrectCostException
   {
      // Create and load an item
      Item item = new ItemImpl("Famous Amos", 45);
      UUT_.loadItem(item);
      
      // Shouldn't be empty anymore
      List<Item> contents = UUT_.getItems();
      assertEquals(contents.isEmpty(), false);
      assertEquals(contents.contains(item), true);
   }
   
   @Test(expected = IncorrectCostException.class)
   public void testLoadItemMismatchedPrices() throws IncorrectCostException
   {      
      // Create and load an item
      Item item = new ItemImpl("Famous Amos", 50);
      UUT_.loadItem(item);
   }
   
   @Test
   public void testDispenseNonexistentItem() throws IncorrectCostException, ItemNotAvailableException
   {
      setupSlot("3 Muskateers", 45);
      
      // Dispense from the slot
      Item rcvItem = UUT_.dispenseItem();
      
      assertEquals(rcvItem.getCost(), 45);
      assertEquals(rcvItem.getDescription(), "3 Muskateers");
   }
   
   @Test(expected = ItemNotAvailableException.class)
   public void testDispenseItem() throws IncorrectCostException, ItemNotAvailableException
   {
      // Dispense from the slot
      Item rcvItem = UUT_.dispenseItem();
   }
   
   @Test
   public void testGetItemsEmpty()
   {
      List<Item> contents = UUT_.getItems();
      
      assertEquals(contents.isEmpty(), true);
   }
   
   @Test
   public void testGetItemsNotEmpty() throws IncorrectCostException
   {
      setupSlot("Twix", 45);
      
      List<Item> contents = UUT_.getItems();
      
      assertEquals(contents.isEmpty(), false);
   }
}
