import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YahtzeeGameTest {

    private YahtzeeGame game;
    
    @BeforeEach
    void setUp() throws Exception {
        game = new YahtzeeGame("John");
    }

    @Test
    void testGetPlayer() {
        assertEquals("John", game.getPlayer().getName());
    }

    @Test
    void testGetRound() {
        assertEquals(1, game.getRound());
    }

    @Test
    void testGetRollCount() {
        assertEquals(0, game.getRollCount());
    }

    @Test
    void testStartNewRound() {
        game.startNewRound();
        assertEquals(2, game.getRound());
        assertEquals(0, game.getRollCount());
    }

    @Test
    void testRollDice() {
        game.rollDice();
        assertEquals(1, game.getRollCount());
        game.rollDice();
        assertEquals(2, game.getRollCount());
        game.rollDice();
        assertEquals(3, game.getRollCount());
        game.rollDice();
        assertEquals(3, game.getRollCount());
    }
}
