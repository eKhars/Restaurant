package com.restaurant.domain;

public class Table {
    private final int id;
    private boolean occupied;
    private Customer currentCustomer;

    public Table(int id) {
        this.id = id;
        this.occupied = false;
    }

    public synchronized boolean isOccupied() {
        return occupied;
    }

    public synchronized void occupy(Customer customer) {
        this.occupied = true;
        this.currentCustomer = customer;
    }

    public synchronized void release() {
        this.occupied = false;
        this.currentCustomer = null;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
}