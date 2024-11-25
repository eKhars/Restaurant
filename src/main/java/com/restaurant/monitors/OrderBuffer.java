package com.restaurant.monitors;

import com.restaurant.domain.*;
import java.util.Queue;
import java.util.LinkedList;

public class OrderBuffer {
    private final Queue<Order> orders;

    public OrderBuffer() {
        this.orders = new LinkedList<>();
    }

    public synchronized void addOrder(Order order) {
        orders.offer(order);
    }

    public synchronized Order getNextOrder() {
        return orders.poll();
    }

    public synchronized Order getOrderForCustomer(Customer customer) {
        return orders.stream()
                .filter(order -> order.getCustomer().equals(customer))
                .findFirst()
                .orElse(null);
    }

    public synchronized boolean isEmpty() {
        return orders.isEmpty();
    }

    public synchronized int size() {
        return orders.size();
    }
}