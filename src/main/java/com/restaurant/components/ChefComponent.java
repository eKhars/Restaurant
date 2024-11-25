package com.restaurant.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.restaurant.domain.Chef;
import javafx.geometry.Point2D;

public class ChefComponent extends Component {
    private Chef chef;
    private Texture texture;
    private Point2D workPosition;
    private boolean isCooking;

    public ChefComponent(Chef chef, Texture texture, Point2D workPosition) {
        this.chef = chef;
        this.texture = texture;
        this.workPosition = workPosition;
        this.isCooking = false;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.setPosition(workPosition);
    }

    @Override
    public void onUpdate(double tpf) {
        if (isCooking) {
            texture.setRotate(texture.getRotate() + 90 * tpf);
        } else {
            texture.setRotate(0);
        }
    }

    public void startCooking() {
        isCooking = true;
        chef.setState(Chef.ChefState.COOKING);
    }

    public void stopCooking() {
        isCooking = false;
        chef.setState(Chef.ChefState.IDLE);
    }

    public Chef getChef() {
        return chef;
    }
}