package com.restaurant.monitors;

import com.restaurant.domain.*;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RestaurantMonitor {
    private final List<Table> tables;
    private final List<Waiter> waiters;
    private final Receptionist receptionist;
    private final Queue<Customer> waitingCustomers;
    private final ReentrantLock lock;
    private final Condition tableAvailable;
    private final Condition receptionistAvailable;
    private final Condition waiterAvailable;

    public RestaurantMonitor(int numTables, int numWaiters) {
        this.tables = new ArrayList<>();
        this.waiters = new ArrayList<>();
        this.receptionist = new Receptionist();
        this.waitingCustomers = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.tableAvailable = lock.newCondition();
        this.receptionistAvailable = lock.newCondition();
        this.waiterAvailable = lock.newCondition();

        initializeTables(numTables);
        initializeWaiters(numWaiters);
    }

    private void initializeTables(int numTables) {
        for (int i = 0; i < numTables; i++) {
            tables.add(new Table(i));
        }
    }

    private void initializeWaiters(int numWaiters) {
        for (int i = 0; i < numWaiters; i++) {
            waiters.add(new Waiter(i));
        }
    }

    public void customerArrives(Customer customer) throws InterruptedException {
        lock.lock();
        try {
            while (receptionist.isBusy()) {
                receptionistAvailable.await();
            }
            receptionist.attend(customer);

            if (!isTableAvailable()) {
                customer.setState(Customer.CustomerState.WAITING_FOR_TABLE);
                waitingCustomers.offer(customer);
                receptionist.release();
                receptionistAvailable.signal();

                while (customer.getState() == Customer.CustomerState.WAITING_FOR_TABLE) {
                    tableAvailable.await();
                }
            } else {
                assignTableToCustomer(customer);
                receptionist.release();
                receptionistAvailable.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void requestWaiter(Customer customer) throws InterruptedException {
        lock.lock();
        try {
            while (!isWaiterAvailable()) {
                waiterAvailable.await();
            }
            assignWaiterToCustomer(customer);
        } finally {
            lock.unlock();
        }
    }

    private void assignWaiterToCustomer(Customer customer) {
        Waiter waiter = getAvailableWaiter();
        if (waiter != null) {
            waiter.setCurrentCustomer(customer);
            waiter.setState(Waiter.WaiterState.TAKING_ORDER);
        }
    }

    public void customerLeaves(Customer customer) {
        lock.lock();
        try {
            Table table = customer.getTable();
            if (table != null) {
                table.release();

                if (!waitingCustomers.isEmpty()) {
                    Customer waitingCustomer = waitingCustomers.poll();
                    assignTableToCustomer(waitingCustomer);
                }
                tableAvailable.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    private void assignTableToCustomer(Customer customer) {
        Table table = getAvailableTable();
        if (table != null) {
            table.occupy(customer);
            customer.setTable(table);
            customer.setState(Customer.CustomerState.WAITING_FOR_WAITER);
        }
    }

    private boolean isTableAvailable() {
        return tables.stream().anyMatch(table -> !table.isOccupied());
    }

    private Table getAvailableTable() {
        return tables.stream()
                .filter(table -> !table.isOccupied())
                .findFirst()
                .orElse(null);
    }

    private boolean isWaiterAvailable() {
        return waiters.stream().anyMatch(Waiter::isAvailable);
    }

    private Waiter getAvailableWaiter() {
        return waiters.stream()
                .filter(Waiter::isAvailable)
                .findFirst()
                .orElse(null);
    }

    public int getQueueSize() {
        lock.lock();
        try {
            return waitingCustomers.size();
        } finally {
            lock.unlock();
        }
    }

    public void releaseWaiter(Waiter waiter) {
        lock.lock();
        try {
            waiter.setState(Waiter.WaiterState.IDLE);
            waiter.setCurrentCustomer(null);
            waiterAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}