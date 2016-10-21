package matchmaker;


import model.GameSession;
import model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MatchMaker implements IMatchMaker {

    private List<GameSession> sessions;

    public MatchMaker() {
        sessions = new ArrayList<>();
    }

    @NotNull
    public GameSession joinGame(@NotNull Player player) {
        for(GameSession session: sessions) {
            if (!session.isFull()) {
                session.join(player);
                return session;
            }
        }
        GameSession session = new GameSession();
        session.join(player);
        sessions.add(session);
        return session;
    }

    public boolean leaveGame(@NotNull Player player) {
        for (GameSession session: sessions) {
            if (session.containsPlayer(player)) {
                session.leave(player.getId());
                return true;
            }
        }
        return false;
    }

    /**
     * @return Currently open game sessions
     */
    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return this.sessions;
    }
}
