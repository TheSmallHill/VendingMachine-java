package com.vendingmachine.api;

import com.vendingmachine.exception.IncorrectCostException;
import com.vendingmachine.exception.ItemNotAvailableException;

import java.util.List;

public interface VendingSlot<ItemType extends Item>
{
    /**
     * Gets the vending slot code. For instance "B2".
     *
     * @return the vending slot code.
     */
    String getCode();

    /**
     * Gets the vending slot cost.
     *
     * @return the vending slot cost.
     */
    int getCost();

    /**
     * Indicates a service technician is loading an item to the vending slot. Items are loaded
     * first-in-last-out.
     *
     * @param item the item to load.
     * @throws IncorrectCostException thrown if an item is placed into a vending slot with an incorrect cost.
     */
    void loadItem(ItemType item) throws IncorrectCostException;

    /**
     * Dispense an item for vending.
     *
     * @return an item.
     * @throws ItemNotAvailableException
     */
    ItemType dispenseItem() throws ItemNotAvailableException;

    /**
     * Gets the items currently in the slot.
     *
     * @return list of items in Vending Slot.
     */
    List<ItemType> getItems();
}
