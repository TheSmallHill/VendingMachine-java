package com.vendingmachine;

import com.vendingmachine.api.VendingMachine;
import com.vendingmachine.api.Item;
import com.vendingmachine.api.ItemImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

public class VendingMachineTest
{
   public VendingMachine UUT_ = new VendingMachineImpl();

   @Test
   public void testLoadMachine()
   {
      // Make some slots we want to add into the machine
      List<VendingSlot<Item>> slotsToConfigure = new ArrayList<VendingSlot<Item>>();
      for (int i = 1; i <= 5; i++)
      {
         VendingSlot<Item> slot = new VendingSlot<Item>("C" + i, i * 5);
         slotsToConfigure.add(slot);
      }
      
      
   }

   @Test
   public void testLoadMachineInvalidSlot()
   {
      
      
      
      
   }
}
