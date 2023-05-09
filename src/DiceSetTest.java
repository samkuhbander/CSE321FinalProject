import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DiceSetTest {

    private DiceSet diceSet;

    @Before
    public void setUp() {
        diceSet = new DiceSet();
    }

    @Test
    public void testRollDice() {
        diceSet.rollDice();
        List<Dice> dice = diceSet.getDice();
        for (Dice die : dice) {
            assertTrue(die.getFaceValue() >= 1 && die.getFaceValue() <= 6);
        }
    }

    @Test
    public void testRollUnlockedDice() {
        diceSet.unlockAllDice();
        diceSet.rollUnlockedDice();
        List<Dice> dice = diceSet.getDice();
        for (Dice die : dice) {
            if (!die.isLocked()) {
                assertTrue(die.getFaceValue() >= 1 && die.getFaceValue() <= 6);
            }
        }
    }

    @Test
    public void testGetDie() {
        Dice die = diceSet.getDie(0);
        assertNotNull(die);
    }

    @Test
    public void testGetDice() {
        List<Dice> dice = diceSet.getDice();
        assertEquals(5, dice.size());
    }

    @Test
    public void testLockDie() {
        diceSet.lockDie(0);
        assertTrue(diceSet.getDie(0).isLocked());
    }

    @Test
    public void testUnlockDie() {
        diceSet.unlockAllDice();
        diceSet.lockDie(0);
        diceSet.unlockDie(0);
        assertFalse(diceSet.getDie(0).isLocked());
    }

    @Test
    public void testToggleDieLock() {
        diceSet.unlockAllDice();
        diceSet.toggleDieLock(0);
        assertTrue(diceSet.getDie(0).isLocked());
        diceSet.toggleDieLock(0);
        assertFalse(diceSet.getDie(0).isLocked());
    }

    @Test
    public void testUnlockAllDice() {
        diceSet.lockDie(0);
        diceSet.lockDie(1);
        diceSet.unlockAllDice();
        List<Dice> dice = diceSet.getDice();
        for (Dice die : dice) {
            assertFalse(die.isLocked());
        }
    }

}
