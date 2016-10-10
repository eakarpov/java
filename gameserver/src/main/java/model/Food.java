package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.FOOD_MASS;
import static model.GameConstants.FOOD_RADIUS;

public class Food {

    @NotNull
    private static final Logger log = LogManager.getLogger(Food.class);
    @NotNull
    private Color color;
    @NotNull
    private Location location;
    private final int mass = FOOD_MASS;
    private final double radius = FOOD_RADIUS;

    public Food(@NotNull Color color, @NotNull Location location) {
        this.color = color;
        this.location = location;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "color=" + color +
                ", location=" + location +
                ", mass=" + mass +
                ", radius=" + radius +
                '}';
    }
}
