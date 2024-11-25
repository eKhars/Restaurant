package com.restaurant.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.restaurant.domain.Customer;
import javafx.geometry.Point2D;

public class CustomerComponent extends Component {
    private Customer customer;
    private Texture texture;
    private Point2D startPosition;
    private Point2D tablePosition;

    public CustomerComponent(Customer customer, Texture texture, Point2D startPosition) {
        this.customer = customer;
        this.texture = texture;
        this.startPosition = startPosition;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        switch (customer.getState()) {
            case WAITING_FOR_TABLE:
                moveTowards(startPosition, tpf);
                break;
            case WAITING_FOR_WAITER:
            case ORDERING:
            case WAITING_FOR_FOOD:
            case EATING:
                if (tablePosition != null) {
                    moveTowards(tablePosition, tpf);
                }
                break;
            case LEAVING:
                moveTowards(startPosition, tpf);
                break;
        }
    }

    private void moveTowards(Point2D target, double tpf) {
        Point2D position = entity.getPosition();
        if (position.distance(target) > 5) {
            Point2D direction = target.subtract(position).normalize();
            entity.translate(direction.multiply(100 * tpf));
        }
    }

    public void setTablePosition(Point2D tablePosition) {
        this.tablePosition = tablePosition;
    }

    public Customer getCustomer() {
        return customer;
    }
}