package com.restaurant.domain;

public class Customer {
    private final int id;
    private final String name;
    private Table table;
    private Order currentOrder;
    private CustomerState state;

    public enum CustomerState {
        WAITING_FOR_TABLE,
        WAITING_FOR_WAITER,
        ORDERING,
        WAITING_FOR_FOOD,
        EATING,
        LEAVING
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.state = CustomerState.WAITING_FOR_TABLE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }
}