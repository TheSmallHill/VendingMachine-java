package com.vendingmachine;

// Import all classes in the package
import com.vendingmachine.api.VendingMachine;
import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.Item;
import com.vendingmachine.api.ItemImpl;
import com.vendingmachine.api.Coin;

import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.InsufficientFundsException;
import com.vendingmachine.exception.ItemNotAvailableException;
import com.vendingmachine.exception.VendingSlotNotFoundException;

// Java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

// React imports
import io.reactivex.rxjava3.core.Observable;

public class VendingMachineImpl implements VendingMachine<Item>
{
   private Map<String, VendingSlot<Item>> slots_= new TreeMap<String, VendingSlot<Item>>();

   private List<Coin> insertedCoins_ = new ArrayList<Coin>();
   private int changeDueCents_ = 0;

   @Override
   public void configureMachineWithVendingSlots(List<VendingSlot<Item>> vendingSlots)
   {
      // Configuring the machine erases all slots
      slots_.clear();
   
      // Setup new slots
      for (VendingSlot<Item> slot : vendingSlots)
      {
         // If there were other things to validate other than the code, we would do it here and AND all the
         // validation statuses together.
         
         final String code = slot.getCode();
         if (validateSlotCode(code))
         {
            slots_.put(code, slot);
         }
      }
   }
   
   @Override
   public void loadMachineWithItem(Item item, String vendingSlotCode)
      throws VendingSlotNotFoundException, IncorrectCostException
   {
      validateItemPrice(item.getCost());
   
      // This can throw if the slot doesn't exist. An invalid slot code just returns null.
      VendingSlot<Item> slot = getSlot(vendingSlotCode);    
             
      // Can only get past this conditional by supplying a slot code of the correct format and the item is real.
      if ((slot != null) && (item != null))
      {   
         // This can throw if the item cost doesn't match the slot cost.
         slot.loadItem(item);
      }
   }
   
   @Override
   public void insertCoin(Coin coin)
   {
      insertedCoins_.add(coin);
   }
      
   @Override
   public Item vendItem(String vendingSlotCode)
      throws InsufficientFundsException, ItemNotAvailableException, VendingSlotNotFoundException
   {
      Item returnItem = null;

      // Able to throw if there is no slot. Returns null if the vending slot code isn't valid.
      VendingSlot<Item> slot = getSlot(vendingSlotCode);
      
      if (slot != null)
      {
         // Make sure they have enough change loaded to pay for their item.
         int loadedCents = getLoadedCents();
         int slotCost = slot.getCost();
         
         // Throw if we have insufficient funds
         if (loadedCents < slotCost)
         {
            throw new InsufficientFundsException();
         }
         
         // Can throw if there are no items remaining
         returnItem = slot.dispenseItem();
         
         changeDueCents_ = loadedCents - slot.getCost();
         insertedCoins_.clear(); // Transaction "processed" so clear out was loaded so we are ready to dispense change.
      }
      
      return returnItem;
   }
   
   @Override
   public List<Coin> returnChange()
   {
      ArrayList<Coin> returnChange = new ArrayList<Coin>();
      
      // Give back as many quarters as we can
      while (changeDueCents_ - 25 >= 0)
      {
         returnChange.add(Coin.QUARTER);
         changeDueCents_ -= 25;
      }
         
      // Give back as many dimes as we can
      while (changeDueCents_ - 10 >= 0)
      {
         returnChange.add(Coin.DIME);
         changeDueCents_ -= 10;
      }
      
      // Give back as many nickels as we can
      while (changeDueCents_ - 5 >= 0)
      {
         returnChange.add(Coin.NICKEL);
         changeDueCents_ -= 5;
      }
      
      return returnChange;
   }
   
   @Override
   public Observable<List<Coin>> observeInsertedMoney()
   {
      Observable<List<Coin>> observableCoins = Observable.just(insertedCoins_);
      return observableCoins;
   }
   
   private VendingSlot<Item> getSlot(String slotCode)
      throws VendingSlotNotFoundException
   {
      VendingSlot<Item> returnSlot = null;
      if (validateSlotCode(slotCode))
      {
         returnSlot = slots_.get(slotCode);        
      
         // Check that we were able to get the hash and if not, throw an exception.
         if (returnSlot == null)
         {
            throw new VendingSlotNotFoundException();
         }
      }
      
      // Return the slot that was found, if any
      return returnSlot;
   }
   
   private int getLoadedCents()
   {
      // Quickly just sum up the coins and return the value
      int totalCents = 0;
      for (Coin coin : insertedCoins_)
      {
         switch (coin)
         {
            case NICKEL:
               totalCents += 5;
               break;
            case DIME:
               totalCents += 10;
               break;
            case QUARTER:
               totalCents += 25;
               break;
         }
      }
      
      return totalCents;
   }
   
   private void validateItemPrice(int price)
      throws IncorrectCostException
   {
      if (price <= 0)
      {
         throw new IncorrectCostException();
      }
   }
   
   private boolean validateSlotCode(String slotCode)
   {
      // Just make sure the slot code comes in in the form we expect by checking with a simple regex.
      return Pattern.matches("([a-bA-B][0-9]+)", slotCode);
   }
}
