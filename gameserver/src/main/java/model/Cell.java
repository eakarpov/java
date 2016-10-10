package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.INITIAL_CELL_MASS;
import static model.GameConstants.INITIAL_CELL_RADIUS;
import static model.GameConstants.INITIAL_CELL_SPEED;

public class Cell {

    @NotNull
    private static final Logger log = LogManager.getLogger(Cell.class);
    @NotNull
    private Color color;
    @NotNull
    private Location location;
    private int mass = INITIAL_CELL_MASS;
    // not sure whether speed is needed as a field
    private double speed = INITIAL_CELL_SPEED;
    private double radius = INITIAL_CELL_RADIUS;

    public Cell(@NotNull Color color, @NotNull Location location) {
        this.color = color;
        this.location = location;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "color=" + color +
                ", location=" + location +
                ", mass=" + mass +
                ", speed=" + speed +
                ", speed=" + radius +
                '}';
    }
}
