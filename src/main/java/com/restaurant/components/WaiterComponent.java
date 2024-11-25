package com.restaurant.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.restaurant.domain.Waiter;
import javafx.geometry.Point2D;

public class WaiterComponent extends Component {
    private Waiter waiter;
    private Texture texture;
    private Point2D restPosition;
    private Point2D targetPosition;

    public WaiterComponent(Waiter waiter, Texture texture, Point2D restPosition) {
        this.waiter = waiter;
        this.texture = texture;
        this.restPosition = restPosition;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D currentPosition = entity.getPosition();
        Point2D targetPos = (waiter.getState() == Waiter.WaiterState.IDLE) ?
                restPosition : targetPosition;

        if (targetPos != null && currentPosition.distance(targetPos) > 5) {
            Point2D direction = targetPos.subtract(currentPosition).normalize();
            entity.translate(direction.multiply(150 * tpf));
        }
    }

    public void moveToCustomer(Point2D customerPosition) {
        this.targetPosition = customerPosition;
    }

    public void returnToRest() {
        this.targetPosition = restPosition;
    }

    public Waiter getWaiter() {
        return waiter;
    }
}