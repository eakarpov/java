package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.GameConstants.INITIAL_FOODS_AMOUNT;
import static model.GameConstants.INITIAL_VIRUSES_AMOUNT;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class SinglePlayerMatchMaker implements MatchMaker {
    @NotNull
    private final Logger log = LogManager.getLogger(SinglePlayerMatchMaker.class);
    @NotNull
    private final List<GameSession> activeGameSessions = new ArrayList<>();

    /**
     * Creates new GameSession for single player
     *
     * @param player single player
     */
    @Override
    public void joinGame(@NotNull Player player) {
        GameSession newGameSession = createNewGame();
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        if (log.isInfoEnabled()) {
            log.info(player + " joined " + newGameSession);
        }
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }

    /**
     * TODO HOMEWORK 1. Implement new game creation. Instantiate GameSession state
     * Log every game instance creation
     *
     * @return new GameSession
     */
    @NotNull
    private GameSession createNewGame() {

        return new GameSession() {
            @Override
            public void join(@NotNull Player player) {
                List<Food> foods = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < INITIAL_FOODS_AMOUNT; i++) {
                    foods.add(new Food(Color.ORANGE,
                            new Location(random.nextInt(Field.BORDER_RIGHT), random.nextInt(Field.BORDER_TOP))));
                }

                List<Virus> viruses = new ArrayList<>();
                for (int i = 0; i < INITIAL_VIRUSES_AMOUNT; i++) {
                    viruses.add(new Virus(
                            new Location(random.nextInt(Field.BORDER_RIGHT), random.nextInt(Field.BORDER_TOP)), 0.0D));
                }

                Field field = new Field(player, foods, viruses);
            }
        };

    }
}