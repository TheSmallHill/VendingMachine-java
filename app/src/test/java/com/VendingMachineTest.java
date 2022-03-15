package com.vendingmachine;

import com.vendingmachine.api.Coin;
import com.vendingmachine.api.VendingMachine;
import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.Item;
import com.vendingmachine.api.ItemImpl;

import com.vendingmachine.exception.VendingSlotNotFoundException;
import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.InsufficientFundsException;
import com.vendingmachine.exception.ItemNotAvailableException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class VendingMachineTest
{
   public VendingMachine UUT_ = new VendingMachineImpl();

   public void setupMachine(boolean initialItem)
      throws VendingSlotNotFoundException, IncorrectCostException
   {
      // Make some slots we want to add into the machine
      List<VendingSlot<Item>> slotsToConfigure = new ArrayList<VendingSlot<Item>>();
      VendingSlot<Item> slot = new VendingSlotImpl("C4", 100);
      slotsToConfigure.add(slot);
      
      // Add the slots to the machine
      UUT_.configureMachineWithVendingSlots(slotsToConfigure);
      
      if (initialItem)
      {
         Item kitKat = new ItemImpl("Kit-Kat", 100);
         UUT_.loadMachineWithItem(kitKat, "C4");
      }
   }

   @Test
   public void testLoadMachine()
   {
      // Make some slots we want to add into the machine
      List<VendingSlot<Item>> slotsToConfigure = new ArrayList<VendingSlot<Item>>();
      for (int i = 1; i <= 5; i++)
      {
         VendingSlot<Item> slot = new VendingSlotImpl("C" + i, i * 5);
         slotsToConfigure.add(slot);
      }
      
      UUT_.configureMachineWithVendingSlots(slotsToConfigure);
   }

   @Test
   public void testLoadMachineInvalidSlots()
   {
      // Make some slots we want to add into the machine
      List<VendingSlot<Item>> slotsToConfigure = new ArrayList<VendingSlot<Item>>();
      for (int i = 1; i <= 5; i++)
      {
         // Slot code is incorrect format so nothing should make it into the machine
         VendingSlot slot = new VendingSlotImpl(i + "C", i * 5);
         slotsToConfigure.add(slot);
      }
      
      UUT_.configureMachineWithVendingSlots(slotsToConfigure);
   }
   
   @Test
   public void testLoadItems()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      UUT_.loadMachineWithItem(new ItemImpl("Milky Way", 100), "C4");
   }
   
   @Test(expected = VendingSlotNotFoundException.class)
   public void testLoadItemBadSlot()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      UUT_.loadMachineWithItem(new ItemImpl("Heath", 100), "B1000");
   }
   
   @Test(expected = IncorrectCostException.class)
   public void testLoadItemBadCost()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);    
      
      UUT_.loadMachineWithItem(new ItemImpl("Skittles", 5), "C4");  
   }
   
   @Test
   public void testInsertCoin()
   {
      UUT_.insertCoin(Coin.NICKEL);
      UUT_.insertCoin(Coin.DIME);
      UUT_.insertCoin(Coin.QUARTER);
   }
   
   @Test
   public void testVendItem()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(true);
      
      for (int i = 0; i < 4; i++)
      {
         UUT_.insertCoin(Coin.QUARTER);
      }
      
      Item item = UUT_.vendItem("C4");
      
      assertEquals(item.getCost(), 100);
      assertEquals(item.getDescription(), "Kit-Kat"); 
   }
   
   @Test(expected = InsufficientFundsException.class)
   public void testVendItemInsufficientFunds()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(true);
      
      for (int i = 0; i < 3; i++)
      {
         UUT_.insertCoin(Coin.QUARTER);
      }
      
      Item item = UUT_.vendItem("C4");
   }
   
   @Test(expected = ItemNotAvailableException.class)
   public void testVendNonexistentItem()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      Item item = UUT_.vendItem("C4");
   }
   
   @Test(expected = VendingSlotNotFoundException.class)
   public void testVendItemSlotNotFound()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      Item item = UUT_.vendItem("D4");
   }
   
   @Test
   public void testReturnChange()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(true);
      
      for (int i = 0; i < 4; i++)
      {
         UUT_.insertCoin(Coin.QUARTER);
      }
      UUT_.insertCoin(Coin.DIME);
      UUT_.insertCoin(Coin.DIME);
      
      Item item = UUT_.vendItem("C4");
      
      List<Coin> change = UUT_.returnChange();
   }
   
   @Test
   public void testReturnNoChange()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(true);
      
      for (int i = 0; i < 4; i++)
      {
         UUT_.insertCoin(Coin.QUARTER);
      }
      
      Item item = UUT_.vendItem("C4");
      
      List<Coin> change = UUT_.returnChange();
   }
   
   @Test
   public void testObserveInsertedMoney()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      for (int i = 0; i < 4; i++)
      {
         UUT_.insertCoin(Coin.QUARTER);
      }
      
      Observable<List<Coin>> observableCoins = UUT_.observeInsertedMoney();
   }
   
   @Test
   public void testObserveInsertedMoneyNone()
      throws VendingSlotNotFoundException, IncorrectCostException, ItemNotAvailableException, InsufficientFundsException
   {
      setupMachine(false);
      
      Observable<List<Coin>> observableCoins = UUT_.observeInsertedMoney();
   }
}
