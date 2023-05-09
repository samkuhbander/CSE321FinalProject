import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ScoreCardTest {
    private ScoreCard scoreCard;

    @Before
    public void setUp() {
        scoreCard = new ScoreCard();
    }

    @Test
    public void testIsScoreTypeAvailable() {
        assertTrue(scoreCard.isScoreTypeAvailable(ScoreCard.ScoreType.ACES));
        scoreCard.setScore(ScoreCard.ScoreType.ACES, 5);
        assertFalse(scoreCard.isScoreTypeAvailable(ScoreCard.ScoreType.ACES));
    }

    @Test
    public void testSetScore() {
        scoreCard.setScore(ScoreCard.ScoreType.ACES, 5);
        assertEquals(5, (int) scoreCard.getScore(ScoreCard.ScoreType.ACES));
    }

    @Test
    public void testGetScore() {
        assertNull(scoreCard.getScore(ScoreCard.ScoreType.ACES));
        scoreCard.setScore(ScoreCard.ScoreType.ACES, 5);
        assertEquals(5, (int) scoreCard.getScore(ScoreCard.ScoreType.ACES));
    }
    
    @Test
    public void testGetTotalScore() {
        scoreCard.setScore(ScoreCard.ScoreType.ACES, 1);
        scoreCard.setScore(ScoreCard.ScoreType.TWOS, 2);
        scoreCard.setScore(ScoreCard.ScoreType.THREES, 3);
        scoreCard.setScore(ScoreCard.ScoreType.FOURS, 4);
        scoreCard.setScore(ScoreCard.ScoreType.FIVES, 5);
        scoreCard.setScore(ScoreCard.ScoreType.SIXES, 6);
        scoreCard.setScore(ScoreCard.ScoreType.THREE_OF_A_KIND, 15);
        scoreCard.setScore(ScoreCard.ScoreType.FOUR_OF_A_KIND, 20);
        scoreCard.setScore(ScoreCard.ScoreType.FULL_HOUSE, 25);
        scoreCard.setScore(ScoreCard.ScoreType.SMALL_STRAIGHT, 30);
        scoreCard.setScore(ScoreCard.ScoreType.LARGE_STRAIGHT, 40);
        scoreCard.setScore(ScoreCard.ScoreType.YAHTZEE, 50);
        scoreCard.setScore(ScoreCard.ScoreType.CHANCE, 15);
        assertEquals(216, scoreCard.getTotalScore());
    }
    
    @Test
    public void testGetTotalScore_NoScoreRecorded() {
        assertEquals(0, scoreCard.getTotalScore());
    } 

}