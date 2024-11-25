package com.restaurant.domain;

public class Receptionist {
    private boolean busy;
    private Customer currentCustomer;

    public Receptionist() {
        this.busy = false;
    }

    public synchronized boolean isBusy() {
        return busy;
    }

    public synchronized void attend(Customer customer) {
        this.busy = true;
        this.currentCustomer = customer;
    }

    public synchronized void release() {
        this.busy = false;
        this.currentCustomer = null;
    }
}