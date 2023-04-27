public class Dice {
    // Define the constant for the number of sides on the die
    private static final int SIDES = 6;
    // Declare the variables to store the face value and lock state of the die
    private int faceValue;
    private boolean locked;

    // Constructor for initializing the Dice
    public Dice() {
        roll();       // Roll the die to set its initial face value
        locked = false; // Initialize the lock state to false (unlocked)
    }

    // Method to roll the die and set a new face value if it is not locked
    public void roll() {
        if (!locked) {
            faceValue = (int) (Math.random() * SIDES) + 1; // Generate a random face value between 1 and SIDES
        }
    }

    // Getter method for returning the face value of the die
    public int getFaceValue() {
        return faceValue;
    }

    // Method to lock the die
    public void lock() {
        locked = true;
    }

    // Method to unlock the die
    public void unlock() {
        locked = false;
    }

    // Method to check if the die is locked
    public boolean isLocked() {
        return locked;
    }
}
