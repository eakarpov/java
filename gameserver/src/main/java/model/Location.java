package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Location {
    private static final Logger log = LogManager.getLogger(Player.class);
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "x='" + x + '\'' +
                "y='" + y + '\'' +
                '}';
    }
}
