package com.restaurant.domain;

public class Order {
    private final int id;
    private final Customer customer;
    private OrderState state;
    private final long creationTime;

    public enum OrderState {
        EN_PROCESO,
        COCINANDO,
        LISTO
    }

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.state = OrderState.EN_PROCESO;
        this.creationTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public long getCreationTime() {
        return creationTime;
    }
}