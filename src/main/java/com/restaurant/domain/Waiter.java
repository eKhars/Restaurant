package com.restaurant.domain;

public class Waiter {
    private final int id;
    private Customer currentCustomer;
    private WaiterState state;

    public enum WaiterState {
        IDLE,
        TAKING_ORDER,
        DELIVERING_FOOD
    }

    public Waiter(int id) {
        this.id = id;
        this.state = WaiterState.IDLE;
    }

    public synchronized boolean isAvailable() {
        return currentCustomer == null && state == WaiterState.IDLE;
    }

    public WaiterState getState() {
        return state;
    }

    public void setState(WaiterState state) {
        this.state = state;
    }

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }
}