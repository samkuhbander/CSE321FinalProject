import java.util.ArrayList;
import java.util.List;

public class DiceSet {
    // Define the constant for the number of dice in the set
    private static final int DICE_COUNT = 5;
    // Declare the list to hold the dice objects
    private List<Dice> dice;

    // Constructor for initializing the DiceSet
    public DiceSet() {
        // Initialize the dice list with the specified size
        dice = new ArrayList<>(DICE_COUNT);
        // Add new Dice objects to the list
        for (int i = 0; i < DICE_COUNT; i++) {
            dice.add(new Dice());
        }
    }

    // Method to roll all dice in the set
    public void rollDice() {
        for (Dice die : dice) {
            die.roll();
        }
    }

    // Method to roll only the unlocked dice in the set
    public void rollUnlockedDice() {
        for (Dice die : dice) {
            if (!die.isLocked()) {
                die.roll();
            }
        }
    }

    // Method to get a specific die from the set based on the provided index
    public Dice getDie(int index) {
        return dice.get(index);
    }

    // Method to get the list of all dice in the set
    public List<Dice> getDice() {
        return dice;
    }

    // Method to lock a specific die in the set based on the provided index
    public void lockDie(int index) {
        dice.get(index).lock();
    }

    // Method to unlock a specific die in the set based on the provided index
    public void unlockDie(int index) {
        dice.get(index).unlock();
    }

    // Method to toggle the lock state of a specific die in the set based on the provided index
    public void toggleDieLock(int index) {
        Dice die = dice.get(index);
        if (die.isLocked()) {
            die.unlock();
        } else {
            die.lock();
        }
    }
    
    // Method to unlock all dice in the set
    public void unlockAllDice() {
        for (Dice die : dice) {
            die.unlock();
        }
    }
    
    // Method to count the occurrences of each face value in the set and return them as an array
    public int[] getDiceCounts() {
        int[] counts = new int[6];
        for (Dice die : dice) {
            counts[die.getFaceValue() - 1]++;
        }
        return counts;
    }
}
