package com.vendingmachine;

// Import all classes in the package
import com.vendingmachine.api.*;
import com.vendingmachine.exception.*;

// Java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

// React imports
import io.reactivex.rxjava3.core.Observable;

public class VendingMachineImpl implements VendingMachine<ItemImpl>
{
   private Map<String, VendingSlot<ItemImpl>> slots_;

   private List<Coin> insertedCoins_;
   private int changeDueCents_;

   public VendingMachineImpl()
   {
      // Member variable initialization (there is no money in the machine and no items in any slots)
      slots_ = new TreeMap<String, VendingSlot<ItemImpl>>();
      insertedCoins_ = new ArrayList<Coin>();
      
      changeDueCents_ = 0;  
   }

   @Override
   public void configureMachineWithVendingSlots(List<VendingSlot<ItemImpl>> vendingSlots)
   {
      // Configuring the machine erases all slots
      slots_.clear();
   
      // Setup new slots
      for (VendingSlot<ItemImpl> slot : vendingSlots)
      {
         // If there were other things to validate other than the code, we would do it here and AND all the
         // validation statuses together.
         
         final String code = slot.getCode();
         if (ValidateSlotCode(code))
         {
            slots_.put(code, slot);
         }
      }
   }
   
   @Override
   public void loadMachineWithItem(ItemImpl item, String vendingSlotCode)
      throws VendingSlotNotFoundException, IncorrectCostException
   {
      // This can throw if the slot doesn't exist. An invalid slot code just returns null.
      VendingSlot<ItemImpl> slot = getSlot(vendingSlotCode);
                  
      if (item.getCost() != slot.getCost())
      {
         throw new IncorrectCostException();
      }
   }
   
   @Override
   public void insertCoin(Coin coin)
   {
      insertedCoins_.add(coin);
   }
      
   @Override
   public ItemImpl vendItem(String vendingSlotCode)
      throws InsufficientFundsException, ItemNotAvailableException, VendingSlotNotFoundException
   {
      ItemImpl returnItem = new ItemImpl("Placeholder", 42);
      
      return returnItem;
   }
   
   @Override
   public List<Coin> returnChange()
   {
      List<Coin> returnChange = new ArrayList<Coin>();
      
      return returnChange;
   }
   
   @Override
   public Observable<List<Coin>> observeInsertedMoney()
   {
      Observable<List<Coin>> observableCoins = Observable.just(insertedCoins_);
      return observableCoins;
   }
   
   private VendingSlot<ItemImpl> getSlot(String slotCode)
      throws VendingSlotNotFoundException
   {
      VendingSlot<ItemImpl> returnSlot = null;
      if (ValidateSlotCode(slotCode))
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
   
   private void ValidateItemPrice(int price)
      throws IncorrectCostException
   {
      if (price <= 0)
      {
         throw new IncorrectCostException();
      }
   }
   
   private boolean ValidateSlotCode(String slotCode)
   {
      // Just make sure the slot code comes in in the form we expect by checking with a simple regex.
      return Pattern.matches("([a-bA-B][0-9]+)", slotCode);
   }
}
