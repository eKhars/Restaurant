package com.restaurant.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.restaurant.domain.Customer;
import com.restaurant.configs.GameConfig;
import javafx.geometry.Point2D;

public class CustomerComponent extends Component {
    private Customer customer;
    private Texture texture;
    private Point2D startPosition;
    private Point2D tablePosition;
    private Point2D receptionPosition;
    private Point2D queuePosition;
    private static final double MOVEMENT_SPEED = 100;
    private boolean hasReachedReception = false;

    public CustomerComponent(Customer customer, Texture texture, Point2D startPosition) {
        this.customer = customer;
        this.texture = texture;
        this.startPosition = startPosition;
        this.receptionPosition = GameConfig.RECEPTIONIST_POSITION.add(50, 0);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.setPosition(startPosition);
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D currentPosition = entity.getPosition();
        Point2D targetPosition = getTargetPosition();

        if (targetPosition != null && currentPosition.distance(targetPosition) > 5) {
            Point2D direction = targetPosition.subtract(currentPosition).normalize();
            entity.translate(direction.multiply(MOVEMENT_SPEED * tpf));
        }
    }

    private Point2D getTargetPosition() {
        switch (customer.getState()) {
            case WAITING_FOR_TABLE:
                if (!hasReachedReception) {
                    if (entity.getPosition().distance(receptionPosition) < 5) {
                        hasReachedReception = true;
                    }
                    return receptionPosition;
                }
                return queuePosition;
            case WAITING_FOR_WAITER:
            case ORDERING:
            case WAITING_FOR_FOOD:
            case EATING:
                return tablePosition;
            case LEAVING:
                return startPosition;
            default:
                return null;
        }
    }

    public void setTablePosition(Point2D tablePosition) {
        this.tablePosition = tablePosition;
    }

    public void setQueuePosition(Point2D position) {
        this.queuePosition = position;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void reset() {
        hasReachedReception = false;
    }
}