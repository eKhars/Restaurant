package com.restaurant;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.restaurant.configs.GameConfig;
import com.restaurant.factories.RestaurantEntityFactory;
import com.restaurant.monitors.RestaurantMonitor;
import com.restaurant.monitors.KitchenMonitor;
import com.restaurant.utils.PoissonDistribution;
import javafx.application.Platform;
import javafx.scene.paint.Color;
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
        customerArrivalDistribution = new PoissonDistribution(1.0); // Lambda = 1 cliente por minuto en promedio

        // Spawn elementos estáticos
        FXGL.spawn("background");
        FXGL.spawn("kitchen");
        FXGL.spawn("receptionist");

        // Spawn chefs y meseros
        for (int i = 0; i < GameConfig.NUM_CHEFS; i++) {
            FXGL.spawn("chef", new SpawnData().put("id", i));
        }

        for (int i = 0; i < GameConfig.NUM_WAITERS; i++) {
            FXGL.spawn("waiter", new SpawnData().put("id", i));
        }

        for (int i = 0; i < GameConfig.NUM_TABLES; i++) {
            FXGL.spawn("table", new SpawnData().put("id", i));
        }

        // Iniciar generación de clientes
        startCustomerGeneration();
    }

    private void startCustomerGeneration() {
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            if (customerArrivalDistribution.nextInt() > 0) {
                Platform.runLater(() -> {
                    FXGL.spawn("customer");
                    customerCount++;
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