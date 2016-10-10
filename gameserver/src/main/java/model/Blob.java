package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.BLOB_MASS;
import static model.GameConstants.BLOB_RADIUS;

public class Blob {

    @NotNull
    private static final Logger log = LogManager.getLogger(Blob.class);
    @NotNull
    private Location location;
    @NotNull
    private Color color;
    private double speed;
    private final int mass = BLOB_MASS;
    private final double radius = BLOB_RADIUS;

    public Blob(@NotNull Location location, @NotNull Color color, double speed) {
        this.location = location;
        this.color = color;
        this.speed = speed;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Blob{" +
                "location=" + location +
                ", color=" + color +
                ", speed=" + speed +
                ", mass=" + mass +
                ", radius=" + radius +
                '}';
    }
}
