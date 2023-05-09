import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("TestPlayer");
    }

    @Test
    public void testGetName() {
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    public void testGetDiceSet() {
        assertNotNull(player.getDiceSet());
    }

    @Test
    public void testGetScoreCard() {
        assertNotNull(player.getScoreCard());
    }

    @Test
    public void testRollDice() {
        player.rollDice();
        assertNotNull(player.getDiceSet().getDiceCounts());
    }

    @Test
    public void testLockDie() {
        player.lockDie(0);
        assertTrue(player.getDiceSet().getDie(0).isLocked());
    }

    @Test
    public void testUnlockDie() {
        player.unlockDie(0);
        assertFalse(player.getDiceSet().getDie(0).isLocked());
    }

    @Test
    public void testToggleDieLock() {
        player.toggleDieLock(0);
        assertTrue(player.getDiceSet().getDie(0).isLocked());
        player.toggleDieLock(0);
        assertFalse(player.getDiceSet().getDie(0).isLocked());
    }

}
