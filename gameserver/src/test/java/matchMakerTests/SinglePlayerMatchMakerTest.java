package matchMakerTests;

import matchmaker.IMatchMaker;
import matchmaker.SinglePlayerMatchMaker;
import model.Player;
import model.User;
import org.junit.Test;

import org.junit.Assert;

import static sun.audio.AudioPlayer.player;

/**
 * @author Alpi
 */
public class SinglePlayerMatchMakerTest {
  @Test
  public void testSinglePlayerGameSessionCreated() {
    IMatchMaker singlePlayerMatchMaker = new SinglePlayerMatchMaker();
    User user = new User("Arkady", "1234");
    Player player = new Player(user.getUserName(), user);
    singlePlayerMatchMaker.joinGame(player);

    Assert.assertEquals(1, singlePlayerMatchMaker.getActiveGameSessions().size());
    Assert.assertNotNull(singlePlayerMatchMaker.getActiveGameSessions().get(0));
  }
}
