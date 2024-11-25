package com.restaurant.domain;

import javafx.geometry.Point2D;
import com.restaurant.configs.GameConfig;

public class Table {
    private final int id;
    private boolean occupied;
    private Customer currentCustomer;
    private final Point2D position;

    public Table(int id) {
        this.id = id;
        this.occupied = false;
        this.position = GameConfig.getTablePosition(id);
    }

    public int getId() {
        return id;
    }

    public Point2D getPosition() {
        return position;
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