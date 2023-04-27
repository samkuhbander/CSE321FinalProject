public class Player {
    // Declare player-related variables
    private String name;
    private DiceSet diceSet;
    private ScoreCard scoreCard;

    // Constructor for initializing the Player with a name
    public Player(String name) {
        this.name = name;           // Initialize the player's name
        diceSet = new DiceSet();    // Initialize the player's dice set
        scoreCard = new ScoreCard(); // Initialize the player's scorecard
    }

    // Getter method for returning the player's name
    public String getName() {
        return name;
    }

    // Getter method for returning the player's dice set
    public DiceSet getDiceSet() {
        return diceSet;
    }

    // Getter method for returning the player's scorecard
    public ScoreCard getScoreCard() {
        return scoreCard;
    }

    // Method to roll all unlocked dice in the player's dice set
    public void rollDice() {
        diceSet.rollUnlockedDice();
    }

    // Method to lock a specific die in the player's dice set, based on the provided index
    public void lockDie(int index) {
        diceSet.lockDie(index);
    }

    // Method to unlock a specific die in the player's dice set, based on the provided index
    public void unlockDie(int index) {
        diceSet.unlockDie(index);
    }

    // Method to toggle the lock state of a specific die in the player's dice set, based on the provided index
    public void toggleDieLock(int index) {
        diceSet.toggleDieLock(index);
    }
}
