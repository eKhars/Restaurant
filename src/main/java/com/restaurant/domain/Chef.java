package com.restaurant.domain;

public class Chef {
    private final int id;
    private Order currentOrder;
    private ChefState state;

    public enum ChefState {
        IDLE,
        COOKING
    }

    public Chef(int id) {
        this.id = id;
        this.state = ChefState.IDLE;
    }

    public synchronized boolean isAvailable() {
        return currentOrder == null && state == ChefState.IDLE;
    }

    public int getId() {
        return id;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    public ChefState getState() {
        return state;
    }

    public void setState(ChefState state) {
        this.state = state;
    }
}