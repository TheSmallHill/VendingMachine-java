package com.vendingmachine.controller;

// Import all classes in the package
import com.vendingmachine.api.*;
import com.vendingmachine.exception.*;

// Java imports
import java.util.List;
import java.util.ArrayList;

// React imports
import io.reactivex.rxjava3.core.Observable;

public class Controller implements VendingMachine<ItemImpl>
{
   public Controller()
   {
      // Intentionally empty
   }

   @Override
   public void configureMachineWithVendingSlots(List<VendingSlot<ItemImpl>> vendingSlots)
   {
   
   }
   
   @Override
   public void loadMachineWithItem(ItemImpl item, String vendingSlotCode)
      throws VendingSlotNotFoundException, IncorrectCostException
   {
      
   }
   
   @Override
   public void insertCoin(Coin coin)
   {
   
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
      List<Coin> insertedMoney = new ArrayList<Coin>();
      
      Observable<List<Coin>> observableMoney = Observable.just(insertedMoney);
      return observableMoney;
   }
}
