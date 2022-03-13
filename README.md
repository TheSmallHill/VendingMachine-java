## Vending Machine Problem ##
Enclosed is a Gradle project.

The goal is to implement a vending machine as described by the interfaces in the project (see the Javadocs in the interface files).

### The tasks: ###

* Create a Git repository to track your changes to the project.
* Create classes that implement the interfaces.
* The method `VendingMachine.observeInsertedMoney` (commented out) uses RxJava's `Observable` class. Add the dependency for RxJava to the project and implement `observeInsertedMoney`. Have it emit the coins inserted into the machine by the customer. It should also emit an empty list when change is dispensed/the customer completes their order.
* Add unit tests for methods in your `VendingMachine` implementation, including `VendingMachine.observeInsertedMoney`.
