package com.restaurant;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.restaurant.configs.GameConfig;
import com.restaurant.factories.RestaurantEntityFactory;
import com.restaurant.monitors.RestaurantMonitor;
import com.restaurant.monitors.KitchenMonitor;
import com.restaurant.components.CustomerComponent;
import com.restaurant.utils.PoissonDistribution;
import javafx.application.Platform;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RestaurantApp extends GameApplication {
    private RestaurantMonitor restaurantMonitor;
    private KitchenMonitor kitchenMonitor;
    private PoissonDistribution customerArrivalDistribution;
    private ScheduledExecutorService executorService;
    private int customerCount = 0;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(GameConfig.WINDOW_WIDTH);
        settings.setHeight(GameConfig.WINDOW_HEIGHT);
        settings.setTitle("Restaurante Simulador");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new RestaurantEntityFactory());
        restaurantMonitor = new RestaurantMonitor(GameConfig.NUM_TABLES, GameConfig.NUM_WAITERS);
        kitchenMonitor = new KitchenMonitor(GameConfig.NUM_CHEFS);
        customerArrivalDistribution = new PoissonDistribution(1.0);

        spawnInitialEntities();
        startCustomerGeneration();
    }

    private void spawnInitialEntities() {
        FXGL.spawn("background");
        FXGL.spawn("kitchen");
        FXGL.spawn("receptionist");

        for (int i = 0; i < GameConfig.NUM_CHEFS; i++) {
            FXGL.spawn("chef", new SpawnData().put("id", i));
        }

        for (int i = 0; i < GameConfig.NUM_WAITERS; i++) {
            FXGL.spawn("waiter", new SpawnData().put("id", i));
        }

        for (int i = 0; i < GameConfig.NUM_TABLES; i++) {
            FXGL.spawn("table", new SpawnData().put("id", i));
        }
    }

    private void startCustomerGeneration() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            if (customerArrivalDistribution.nextInt() > 0) {
                Platform.runLater(() -> {
                    Entity customerEntity = FXGL.spawn("customer", new SpawnData().put("id", customerCount++));
                    CustomerComponent customerComponent = customerEntity.getComponent(CustomerComponent.class);

                    // Iniciar el proceso de asignación de mesa en un hilo separado
                    new Thread(() -> {
                        try {
                            restaurantMonitor.customerArrives(customerComponent.getCustomer());
                            // Cuando se asigna una mesa, actualizar la posición del cliente
                            Platform.runLater(() -> {
                                int tableId = customerComponent.getCustomer().getTable().getId();
                                customerComponent.setTablePosition(GameConfig.getTablePosition(tableId));
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onUpdate(double tpf) {
        // Actualizar estado del juego
    }

    public static void main(String[] args) {
        launch(args);
    }

}