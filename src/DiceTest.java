import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {
    private Dice dice;

    @BeforeEach
    public void setUp() {
        dice = new Dice();
    }

    @Test
    public void testRoll() {
        boolean differentFaceValueFound = false;
        int initialFaceValue = dice.getFaceValue();
        for (int i = 0; i < 100; i++) {
            dice.roll();
            if (dice.getFaceValue() != initialFaceValue) {
                differentFaceValueFound = true;
                break;
            }
        }
        assertTrue(differentFaceValueFound, "Rolling the dice should produce different face values");
    }

    @Test
    public void testLock() {
        dice.lock();
        assertTrue(dice.isLocked(), "The die should be locked after calling lock()");
    }

    @Test
    public void testUnlock() {
        dice.lock();
        dice.unlock();
        assertFalse(dice.isLocked(), "The die should be unlocked after calling unlock()");
    }

    @Test
    public void testRollWhenLocked() {
        dice.lock();
        int initialFaceValue = dice.getFaceValue();
        dice.roll();
        assertEquals(initialFaceValue, dice.getFaceValue(),
                "The face value should remain unchanged when rolling a locked die");
    }
}
