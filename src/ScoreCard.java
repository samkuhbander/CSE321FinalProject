import java.util.HashMap;
import java.util.Map;

public class ScoreCard {
    // Define the possible score types for Yahtzee
    public enum ScoreType {
        ACES, TWOS, THREES, FOURS, FIVES, SIXES,
        THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, SMALL_STRAIGHT,
        LARGE_STRAIGHT, YAHTZEE, CHANCE
    }

    // Declare a map to store the scores for each score type
    private Map<ScoreType, Integer> scores;
    // Declare a boolean variable to track if a Yahtzee score has been recorded
    private boolean yahtzeeScored;

    // Constructor for initializing the ScoreCard
    public ScoreCard() {
        scores = new HashMap<>();
        yahtzeeScored = false;
        // Initialize all score types with null (score not recorded yet)
        for (ScoreType scoreType : ScoreType.values()) {
            scores.put(scoreType, null);
        }
    }

    // Method to check if a specific score type is available (not recorded yet)
    public boolean isScoreTypeAvailable(ScoreType scoreType) {
        return scores.get(scoreType) == null;
    }

    // Method to set the score for a specific score type and update Yahtzee status
    public void setScore(ScoreType scoreType, int score) {
        if (isScoreTypeAvailable(scoreType)) {
            scores.put(scoreType, score);

            // Update Yahtzee status if the score type is YAHTZEE
            if (scoreType == ScoreType.YAHTZEE) {
                yahtzeeScored = true;
            }
        }
    }

    // Method to get the score for a specific score type
    public Integer getScore(ScoreType scoreType) {
        return scores.get(scoreType);
    }

    // Method to calculate the bonus for upper section and Yahtzee
    private int calculateBonus() {
        int upperSectionSum = 0;
        int yahtzeeBonus = 0;
        
        for (ScoreType scoreType : ScoreType.values()) {
            if (scoreType == ScoreType.ACES || scoreType == ScoreType.TWOS || scoreType == ScoreType.THREES
                    || scoreType == ScoreType.FOURS || scoreType == ScoreType.FIVES || scoreType == ScoreType.SIXES) {
                Integer score = getScore(scoreType);
                if (score != null) {
                    upperSectionSum += score;
                }
            } else if (scoreType == ScoreType.YAHTZEE && isYahtzeeScored()) {
                Integer score = getScore(scoreType);
                if (score != null && score > 50) {
                    yahtzeeBonus += score - 50;
                }
            }
        }

        int bonus = 0;
        if (upperSectionSum > 63) {
            bonus += 35;
        }
        bonus += yahtzeeBonus;

        return bonus;
    }

    // Method to calculate the total score by summing all recorded scores and bonuses
    public int getTotalScore() {
        int total = 0;
        for (Integer score : scores.values()) {
            if (score != null) {
                total += score;
            }
        }
        total += calculateBonus();
        return total;
    }

    // Method to check if a Yahtzee score has been recorded
    public boolean isYahtzeeScored() {
        return yahtzeeScored;
    }

    public int calculateScore(ScoreType scoreType, DiceSet diceSet) {
        // Calculate the score for the given score type based on the dice set
        switch (scoreType) {
            case ACES:
                return sumOfDiceWithValue(1, diceSet);
            case TWOS:
                return sumOfDiceWithValue(2, diceSet);
            case THREES:
                return sumOfDiceWithValue(3, diceSet);
            case FOURS:
                return sumOfDiceWithValue(4, diceSet);
            case FIVES:
                return sumOfDiceWithValue(5, diceSet);
            case SIXES:
                return sumOfDiceWithValue(6, diceSet);
            case THREE_OF_A_KIND:
                return scoreNOfAKind(3, diceSet);
            case FOUR_OF_A_KIND:
                return scoreNOfAKind(4, diceSet);
            case FULL_HOUSE:
                return scoreFullHouse(diceSet);
            case SMALL_STRAIGHT:
                return scoreSmallStraight(diceSet);
            case LARGE_STRAIGHT:
                return scoreLargeStraight(diceSet);
            case YAHTZEE:
                return scoreYahtzee(diceSet);
            case CHANCE:
                return scoreChance(diceSet);
            default:
                return 0;
        }
    }

    // Helper method to calculate the sum of dice with a specific face value
    private int sumOfDiceWithValue(int value, DiceSet diceSet) {
        int sum = 0;
        for (Dice die : diceSet.getDice()) {
            if (die.getFaceValue() == value) {
                sum += value;
            }
        }
        return sum;
    }

    // Helper method to score N of a kind (e.g., three of a kind, four of a kind)
    private int scoreNOfAKind(int n, DiceSet diceSet) {
        int sum = 0;
        for (Dice die : diceSet.getDice()) {
            sum += die.getFaceValue();
        }
        return sum;
    }


    // Helper method to score a full house
    private int scoreFullHouse(DiceSet diceSet) {
        int[] counts = diceSet.getDiceCounts();
        boolean foundThree = false;
        boolean foundTwo = false;
        for (int count : counts) {
            if (count == 3) {
                foundThree = true;
            } else if (count == 2) {
                foundTwo = true;
            }
        }
        return foundThree && foundTwo ? 25 : 0;
    }

    // Helper method to score a small straight (sequence of four consecutive numbers)
    private int scoreSmallStraight(DiceSet diceSet) {
        int[] counts = diceSet.getDiceCounts();
        int consecutiveCount = 0;
        for (int count : counts) {
            if (count > 0) {
                consecutiveCount++;
                if (consecutiveCount == 4) {
                    return 30;
                }
            } else {
                consecutiveCount = 0;
            }
        }
        return 0;
    }

    // Helper method to score a large straight (sequence of five consecutive numbers)
    private int scoreLargeStraight(DiceSet diceSet) {
        int[] counts = diceSet.getDiceCounts();
        int consecutiveCount = 0; 
        for (int count : counts) {
            if (count == 1) {
                consecutiveCount++;
                if (consecutiveCount == 5) {
                    return 40; 
                }
            } else {
                consecutiveCount = 0; 
            }
        }
        return 0;
    }

    private int scoreYahtzee(DiceSet diceSet) {
        int[] counts = diceSet.getDiceCounts();
        for (int count : counts) {
            if (count == 5) {
                return 50;
            }
        }
        return 0;
    }

    private int scoreChance(DiceSet diceSet) {
        int sum = 0;
        for (Dice die : diceSet.getDice()) {
            sum += die.getFaceValue();
        }
        return sum;
    }
}
