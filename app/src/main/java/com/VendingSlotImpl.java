package com.vendingmachine;

import com.vendingmachine.api.VendingSlot;
import com.vendingmachine.api.Item;

import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.ItemNotAvailableException;

import java.util.List;
import java.util.Stack;

public class VendingSlotImpl implements VendingSlot<Item>
{
   final String code_;
   final int cost_;

   Stack<Item> contents_;

   public VendingSlotImpl(String code, int cost)
   {
      code_ = code;
      cost_ = cost;
      
      contents_ = new Stack<Item>();
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
   public void loadItem(Item item) throws IncorrectCostException
   {
      final int cost = item.getCost();
      //if (item.getCost() != cost_)
      //{
      //   throw new IncorrectCostException();
      //}
      
      contents_.push(item);
   }
   
   @Override
   public Item dispenseItem()
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
   public Stack<Item> getItems()
   {
      return contents_;
   }
}
