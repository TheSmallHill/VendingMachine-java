package com.example.vendingmachine.api;

public class ItemImpl implements Item {

    private final String description;
    private final int cost;

    public ItemImpl(String description, int cost) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }

        this.description = description;
        this.cost = cost;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCost() {
        return cost;
    }
}
