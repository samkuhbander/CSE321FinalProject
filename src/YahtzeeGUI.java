import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YahtzeeGUI {
    // Declare GUI components and game-related variables
    private JFrame frame;
    private JButton rollButton;
    private JPanel dicePanel;
    private JPanel scorePanel;
    private JLabel rollCountLabel;
    private YahtzeeGame game;
    private JButton[] diceButtons;
    private JButton[] scoreButtons;

    public YahtzeeGUI() {
        // Initialize the Yahtzee game
        game = new YahtzeeGame("Player 1");

        // Create and configure the main frame
        frame = new JFrame("Yahtzee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the "Roll Dice" button and its action listener
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Roll the dice and update the display
                game.rollDice();
                updateDiceDisplay();
                updateRollCountDisplay();
            }
        });

        // Initialize the label for displaying the remaining rolls
        rollCountLabel = new JLabel("Rolls left: 3");

        // Initialize the panel for displaying dice and set its layout
        dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayout(1, 5));

        // Initialize the dice buttons and their action listeners
        diceButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            final int index = i;
            diceButtons[i] = new JButton();
            diceButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Toggle the lock state of the die and update the display
                    game.getPlayer().getDiceSet().toggleDieLock(index);
                    updateDiceDisplay();
                }
            });
            dicePanel.add(diceButtons[i]);
        }

        // Initialize the panel for displaying scores and set its layout
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(13, 2));

        // Initialize the score buttons and their action listeners
        scoreButtons = new JButton[13];
        for (int i = 0; i < 13; i++) {
            final ScoreCard.ScoreType scoreType = ScoreCard.ScoreType.values()[i];
            scoreButtons[i] = new JButton(scoreType.toString());
            scoreButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Calculate score for the selected scoreType and update the display
                    int score = game.getPlayer().getScoreCard().calculateScore(scoreType, game.getPlayer().getDiceSet());
                    game.applyScore(scoreType, score);
                    updateScoreDisplay();
                }
            });
            scorePanel.add(scoreButtons[i]);
        }

        // Add components to the main frame
        frame.add(rollButton, BorderLayout.NORTH);
        frame.add(rollCountLabel, BorderLayout.SOUTH);
        frame.add(dicePanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.EAST);
    }

    // Method to update the display of dice and their lock state
    private void updateDiceDisplay() {
        for (int i = 0; i < 5; i++) {
            Dice die = game.getPlayer().getDiceSet().getDie(i);
            String text = die.getFaceValue() + (die.isLocked() ? " (Locked)" : "");
            diceButtons[i].setText(text);
        }
    }

    // Method to update the display of the remaining roll count
    private void updateRollCountDisplay() {
        int rollsRemaining = 3 - game.getRollCount();
        rollCountLabel.setText("Rolls left: " + rollsRemaining);
    }

    // Method to update the display of available score options and scores
    private void updateScoreDisplay() {
        for (int i = 0; i < 13; i++) {
            ScoreCard.ScoreType scoreType = ScoreCard.ScoreType.values()[i];
            if (!game.getPlayer().getScoreCard().isScoreTypeAvailable(scoreType)) {
                scoreButtons[i].setEnabled(false);
                scoreButtons[i].setText(scoreType.toString() + ": " + game.getPlayer().getScoreCard().getScore(scoreType));
            }
        }
    }

    // Method to make the main frame visible
    public void show() {
        frame.setVisible(true);
    }

    // Main method to run the Yahtzee GUI
    public static void main(String[] args) {
    	// Use SwingUtilities.invokeLater to create and show the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                YahtzeeGUI gui = new YahtzeeGUI();
                gui.show();
            }
        });
    }
}

