package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import static model.GameConstants.INITIAL_CELL_MASS;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {

    @NotNull
    private static final Logger log = LogManager.getLogger(Player.class);
    @NotNull
    private String name;
    @NotNull
    private Set<Cell> cells = new HashSet<>(GameConstants.MAX_CELLS);
    @NotNull
    private int cellsEaten;
    private int foodEaten;
    private long initialTime;
    private int mass;
    private int score = 16;

    /**
     * Create new Player
     *
     * @param name visible name
     */
    public Player(@NotNull String name) {
        this.name = name;
        this.mass = INITIAL_CELL_MASS;
        this.foodEaten = 0;
        this.cellsEaten = 0;
        this.initialTime = System.currentTimeMillis();
        Cell startingCell = new Cell(Color.BLUE, new Location(23, 4353));
        this.cells.add(startingCell);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cells=" + cells +
                ", mass=" + mass +
                ", foodEaten=" + foodEaten +
                ", cellsEaten=" + cellsEaten +
                ", initialTime=" + initialTime +
                ", score=" + score +
                '}';
    }
}
