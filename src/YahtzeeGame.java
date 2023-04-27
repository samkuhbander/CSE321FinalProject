public class YahtzeeGame {
    // Declare game-related variables
    private Player player;
    private int round;
    private int rollCount;

    // Constructor for initializing the Yahtzee game with a player's name
    public YahtzeeGame(String playerName) {
        player = new Player(playerName);  // Initialize the player
        round = 1;                        // Initialize the current round to 1
        rollCount = 0;                    // Initialize the roll count to 0
    }

    // Getter method for returning the player object
    public Player getPlayer() {
        return player;
    }

    // Getter method for returning the current round number
    public int getRound() {
        return round;
    }

    // Getter method for returning the current roll count
    public int getRollCount() {
        return rollCount;
    }

    // Method to start a new round in the game
    public void startNewRound() {
        player.getDiceSet().unlockAllDice(); // Unlock all dice for the new round
        rollCount = 0;                       // Reset the roll count for the new round
        round++;                             // Increment the round number
    }

    // Method to check if the game is over (after 13 rounds)
    public boolean isGameOver() {
        return round > 13;
    }

    // Method to roll the dice if the player has not exceeded the limit of 3 rolls per round
    public void rollDice() {
        if (rollCount < 3) {
            player.rollDice(); // Roll the dice
            rollCount++;       // Increment the roll count
        }
    }

    // Method to apply the calculated score to the player's scorecard and start a new round if the score type is available
    public void applyScore(ScoreCard.ScoreType scoreType, int score) {
        if (player.getScoreCard().isScoreTypeAvailable(scoreType)) {
            player.getScoreCard().setScore(scoreType, score); // Set the score for the selected score type
            startNewRound();                                  // Start a new round after applying the score
        }
    }
}
