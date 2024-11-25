package com.restaurant.configs;

import javafx.geometry.Point2D;
import java.util.Map;
import java.util.HashMap;

public class GameConfig {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int NUM_TABLES = 20;
    public static final int NUM_WAITERS = 2;
    public static final int NUM_CHEFS = 3;

    public static final Point2D ENTRANCE_POSITION = new Point2D(50, 360);
    public static final Point2D RECEPTIONIST_POSITION = new Point2D(150, 360);
    public static final Point2D KITCHEN_POSITION = new Point2D(1000, 50);  // Cocina arriba a la derecha
    public static final Point2D WAITER_REST_POSITION = new Point2D(800, 100);

    private static final Map<Integer, Point2D> TABLE_POSITIONS = new HashMap<>();
    static {
        // Primera fila
        TABLE_POSITIONS.put(0, new Point2D(300, 100));
        TABLE_POSITIONS.put(1, new Point2D(400, 100));
        TABLE_POSITIONS.put(2, new Point2D(500, 100));
        TABLE_POSITIONS.put(3, new Point2D(600, 100));
        TABLE_POSITIONS.put(4, new Point2D(700, 100));

        // Segunda fila
        TABLE_POSITIONS.put(5, new Point2D(300, 250));
        TABLE_POSITIONS.put(6, new Point2D(400, 250));
        TABLE_POSITIONS.put(7, new Point2D(500, 250));
        TABLE_POSITIONS.put(8, new Point2D(600, 250));
        TABLE_POSITIONS.put(9, new Point2D(700, 250));

        // Tercera fila
        TABLE_POSITIONS.put(10, new Point2D(300, 400));
        TABLE_POSITIONS.put(11, new Point2D(400, 400));
        TABLE_POSITIONS.put(12, new Point2D(500, 400));
        TABLE_POSITIONS.put(13, new Point2D(600, 400));
        TABLE_POSITIONS.put(14, new Point2D(700, 400));

        // Cuarta fila
        TABLE_POSITIONS.put(15, new Point2D(300, 550));
        TABLE_POSITIONS.put(16, new Point2D(400, 550));
        TABLE_POSITIONS.put(17, new Point2D(500, 550));
        TABLE_POSITIONS.put(18, new Point2D(600, 550));
        TABLE_POSITIONS.put(19, new Point2D(700, 550));
    }

    public static Point2D getTablePosition(int tableId) {
        return TABLE_POSITIONS.get(tableId);
    }

    private static final Map<Integer, Point2D> CHEF_POSITIONS = new HashMap<>();
    static {
        CHEF_POSITIONS.put(0, new Point2D(1000, 120));  // Primer cocinero
        CHEF_POSITIONS.put(1, new Point2D(1080, 120));  // Segundo cocinero
        CHEF_POSITIONS.put(2, new Point2D(1160, 120));  // Tercer cocinero
    }

    public static Point2D getChefPosition(int chefId) {
        return CHEF_POSITIONS.get(chefId);
    }

    public static final double COOKING_TIME = 5.0;
    public static final double EATING_TIME = 10.0;
    public static final double ORDER_TIME = 3.0;
}