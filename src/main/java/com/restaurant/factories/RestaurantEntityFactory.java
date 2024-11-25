package com.restaurant.factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.Texture;
import com.restaurant.components.*;
import com.restaurant.configs.GameConfig;
import com.restaurant.domain.*;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class RestaurantEntityFactory implements EntityFactory {
    private static final String[] CUSTOMER_SPRITES = {
            "sam.png", "alex.png", "jodi.png", "haley.png", "harvey.png", "penny.png"
    };
    private static final int customerCounter = 0;

    @Spawns("table")
    public Entity spawnTable(SpawnData data) {
        int id = data.get("id");
        Table table = new Table(id);
        Texture texture = FXGL.texture("mesa.png");
        texture.setFitWidth(60);
        texture.setFitHeight(60);

        return FXGL.entityBuilder(data)
                .type(EntityType.TABLE)
                .at(GameConfig.getTablePosition(id))
                .viewWithBBox(texture)
                .build();
    }

    @Spawns("customer")
    public Entity spawnCustomer(SpawnData data) {
        String spriteName = CUSTOMER_SPRITES[customerCounter % CUSTOMER_SPRITES.length];
        Customer customer = new Customer(customerCounter, "Customer" + customerCounter);
        Texture texture = FXGL.texture(spriteName);
        texture.setFitWidth(50);
        texture.setFitHeight(50);

        return FXGL.entityBuilder(data)
                .type(EntityType.CUSTOMER)
                .with(new CustomerComponent(customer, texture, GameConfig.ENTRANCE_POSITION))
                .build();
    }

    @Spawns("background")
    public Entity spawnBackground(SpawnData data) {
        Texture background = FXGL.texture("fondo.png");
        background.setFitWidth(GameConfig.WINDOW_WIDTH);
        background.setFitHeight(GameConfig.WINDOW_HEIGHT);

        return FXGL.entityBuilder(data)
                .view(background)
                .zIndex(-1)
                .build();
    }

    @Spawns("waiter")
    public Entity spawnWaiter(SpawnData data) {
        int id = data.get("id");
        Waiter waiter = new Waiter(id);
        Texture texture = FXGL.texture("mesero.png");
        texture.setFitWidth(50);
        texture.setFitHeight(50);

        return FXGL.entityBuilder(data)
                .type(EntityType.WAITER)
                .with(new WaiterComponent(waiter, texture, GameConfig.WAITER_REST_POSITION))
                .build();
    }

    @Spawns("chef")
    public Entity spawnChef(SpawnData data) {
        int id = data.get("id");
        Chef chef = new Chef(id);
        Texture texture = FXGL.texture("chef" + (id + 1) + ".png");
        texture.setFitWidth(50);
        texture.setFitHeight(50);

        return FXGL.entityBuilder(data)
                .type(EntityType.CHEF)
                .with(new ChefComponent(chef, texture, GameConfig.getChefPosition(id)))
                .build();
    }

    @Spawns("kitchen")
    public Entity spawnKitchen(SpawnData data) {
        Texture texture = FXGL.texture("cocina2.png");
        texture.setFitWidth(400);  // Ajusta este valor según necesites
        texture.setFitHeight(200); // Ajusta este valor según necesites

        return FXGL.entityBuilder(data)
                .at(GameConfig.KITCHEN_POSITION)
                .view(texture)
                .build();
    }

    @Spawns("receptionist")
    public Entity spawnReceptionist(SpawnData data) {
        Texture texture = FXGL.texture("recepcionista.png");
        texture.setFitWidth(120);
        texture.setFitHeight(120);

        return FXGL.entityBuilder(data)
                .at(GameConfig.RECEPTIONIST_POSITION)
                .view(texture)
                .build();
    }
}