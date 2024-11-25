package com.restaurant.monitors;

import com.restaurant.domain.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class KitchenMonitor {
    private final ReentrantLock lock;
    private final Condition orderAvailable;
    private final OrderBuffer orderBuffer;
    private final OrderBuffer readyOrdersBuffer;

    public KitchenMonitor(int numChefs) {
        this.lock = new ReentrantLock();
        this.orderAvailable = lock.newCondition();
        this.orderBuffer = new OrderBuffer();
        this.readyOrdersBuffer = new OrderBuffer();
    }

    public void addOrder(Order order) {
        lock.lock();
        try {
            orderBuffer.addOrder(order);
            orderAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

    public Order getOrderToCook() throws InterruptedException {
        lock.lock();
        try {
            while (orderBuffer.isEmpty()) {
                orderAvailable.await();
            }
            return orderBuffer.getNextOrder();
        } finally {
            lock.unlock();
        }
    }

    public void orderReady(Order order) {
        lock.lock();
        try {
            order.setState(Order.OrderState.LISTO);
            readyOrdersBuffer.addOrder(order);
        } finally {
            lock.unlock();
        }
    }

    public Order checkReadyOrder(Customer customer) {
        lock.lock();
        try {
            return readyOrdersBuffer.getOrderForCustomer(customer);
        } finally {
            lock.unlock();
        }
    }
}