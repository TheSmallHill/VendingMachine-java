package com.example.vendingmachine.api;

import com.example.vendingmachine.exception.IncorrectCostException;
import com.example.vendingmachine.exception.InsufficientFundsException;
import com.example.vendingmachine.exception.ItemNotAvailableException;
import com.example.vendingmachine.exception.VendingSlotNotFoundException;

import java.util.List;

/**
 * Describes the functions of a vending machine. The machine can be configured with multiple vending slots
 * for vending different items. Each vending slot can be loaded with products for sale. Each slot has a
 * fixed price and can only be loaded with a product of the correct price. All prices are in cents. The
 * vending machine can accept money from a customer and then vend one or more products. The customer's
 * change is only returned if explicitly requested.
 * <p>
 * Tasks:
 * <ol>
 * <li>Create concrete implementations of the VendingMachine, VendingSlot, and Item interfaces. Replace all
 * the mock classes in the VendingMachineTest with your concrete implementations. Run existing tests. Make
 * sure they pass.</li>
 * <li>Implement the following additional unit tests:</li>
 * <ul>
 * <li>Test ItemNotAvailableException is thrown when vending an item from an empty slot.</li>
 * <li>Test inserting money, vending an item, inserting more money, and successfully vending a second
 * product.</li>
 * </ul>
 * <li>Implement a hasCustomerMoney() method using RxJava. It should return an Observable boolean. It will
 * emit true when vending machine has any amount of customer money loaded for vending. Write a test to
 * confirm that it works as intended.</li>
 * </ol>
 *
 * @param <ItemType> the implementation of {@link Item} this vending machine uses.
 */
public interface VendingMachine<ItemType extends Item>
{
    /**
     * Configures the machine's vending slots. Calling this will clear any existing configuration.
     *
     * @param vendingSlots the vending slots.
     */
    void configureMachineWithVendingSlots(List<VendingSlot<ItemType>> vendingSlots);

    /**
     * Indicates a service technician is loading an item to be vended in the provided vending slot code.
     *
     * @param item the item to vend.
     * @param vendingSlotCode the vending slot code.
     * @throws VendingSlotNotFoundException thrown if the vending slot codes doesn't match any configured
     *                                      vending slots.
     * @throws IncorrectCostException       thrown if an item is placed into a vending slot with an
     *                                      incorrect cost.
     */
    void loadMachineWithItem(ItemType item, String vendingSlotCode)
            throws VendingSlotNotFoundException, IncorrectCostException;

    /**
     * Indicates a customer has inserted money for vending.
     *
     * @param coin the money inserted.
     */
    void insertCoin(Coin coin);

    /**
     * Indicates a customer has requested to vend an item
     *
     * @param vendingSlotCode the vending slot code
     * @return the vended item
     * @throws InsufficientFundsException thrown if the customer has not inserted enough money to purchase
     *                                    the item.
     * @throws ItemNotAvailableException  thrown if the vending slot does not have an item to vend.
     */
    ItemType vendItem(String vendingSlotCode) throws InsufficientFundsException, ItemNotAvailableException,
            VendingSlotNotFoundException;

    /**
     * Indicates the vending machine should return the customer's change.
     *
     * @return the money returned.
     */
    List<Coin> returnChange();

    /**
     * Emits the coins currently inserted in the machine.
     *
     * @return observable of the coins currently inserted in the machine.
     */
    // TODO: Implement this using RxJava...
    //Observable<List<Coin>> observeInsertedMoney();
}
