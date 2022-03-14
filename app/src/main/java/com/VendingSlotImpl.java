package com.vendingmachine;

import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.ItemImpl;

import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.ItemNotAvailableException;

import java.util.List;
import java.util.Stack;

public class VendingSlotImpl implements VendingSlot<ItemImpl>
{
   final String code_;
   final int cost_;

   Stack<ItemImpl> contents_;

   public VendingSlotImpl(String code, int cost)
   {
      code_ = code;
      cost_ = cost;
      
      contents_ = new Stack<ItemImpl>();
   }
   
   @Override
   public String getCode()
   {
      return code_;
   }
   
   @Override
   public int getCost()
   {
      return cost_;
   }
   
   @Override
   public void loadItem(ItemImpl item)
      throws IncorrectCostException
   {
      if (item.getCost() != getCost())
      {
         throw new IncorrectCostException();
      }
      
      contents_.push(item);
   }
   
   @Override
   public ItemImpl dispenseItem()
      throws ItemNotAvailableException
   {
      if (contents_.empty())
      {
         throw new ItemNotAvailableException();
      }
      
      // Dispense the next available item
      return contents_.pop();
   }
   
   @Override
   public Stack<ItemImpl> getItems()
   {
      return contents_;
   }
}
