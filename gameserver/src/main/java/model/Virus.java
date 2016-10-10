package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

import static model.GameConstants.INITIAL_VIRUS_MASS;

public class Virus {

    @NotNull
    private static final Logger log = LogManager.getLogger(Virus.class);
    @NotNull
    private Color color = Color.GREEN;
    @NotNull
    private Location location;
    private int mass = INITIAL_VIRUS_MASS;
    private double speed;

    public Virus(@NotNull Location location, double speed) {
        this.speed = speed * 2;
        this.location = location;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Virus{" +
                "color=" + color +
                ", location=" + location +
                ", mass=" + mass +
                ", speed=" + speed +
                '}';
    }
}
