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
    private JLabel totalScoreLabel;
    private YahtzeeGame game;
    private JButton[] diceButtons;
    private JButton[] scoreButtons;
    private JButton restartButton;
    private Font newFont = new Font("Arial", Font.PLAIN, 18);
    private JLabel upperScoreLabel;
    private JLabel lowerScoreLabel;
    private JPanel upperScorePanel;
    private JPanel lowerScorePanel;


    public YahtzeeGUI() {
        // Initialize the Yahtzee game
        game = new YahtzeeGame("Player 1");

        // Create and configure the main frame
        frame = new JFrame("Yahtzee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // array to track what buttons to lock
        Boolean[] lockScoreButtons = { false, false, false, false, false, false, false, false, false, false, false,
                false, false };
        // Create the "Roll Dice" button and its action listener
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Roll the dice and update the display
                game.rollDice();
                updateDiceDisplay();
                updateRollCountDisplay();
                for (int i = 0; i < scoreButtons.length; i++) {
                    if (lockScoreButtons[i] == true) {

                    } else {
                        scoreButtons[i].setEnabled(true);
                    }
                }

                // Enable the dice buttons after the first roll
                for (JButton diceButton : diceButtons) {
                    diceButton.setEnabled(true);
                }

                // Disable the "Roll Dice" button if there are no rolls left
                if (game.getRollCount() >= 3) {
                    rollButton.setEnabled(false);
                }
            }
        });

        // Initialize the label for displaying the remaining rolls
        rollCountLabel = new JLabel("Rolls left: 3");
        rollCountLabel.setFont(newFont);

        // Initialize the panel for displaying dice and set its layout
        dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayout(1, 5));

        // Initialize the dice buttons and their action listeners
        diceButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            final int index = i;
            diceButtons[i] = new JButton();
            diceButtons[i].setFont(newFont);
            diceButtons[i].setEnabled(false); // Disable the dice buttons initially
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
        scorePanel.setLayout(new GridLayout(4, 1));
    
        // Add labels for the upper and lower score boxes
        JLabel upperScoreBoxLabel = new JLabel("Upper Score Box");
        upperScoreBoxLabel.setFont(newFont);
    
        JLabel lowerScoreBoxLabel = new JLabel("Lower Score Box");
        lowerScoreBoxLabel.setFont(newFont);
    
        // Initialize labels for the upper and lower box scores
        upperScoreLabel = new JLabel("Upper Box Score: 0");
        upperScoreLabel.setFont(newFont);
    
        lowerScoreLabel = new JLabel("Lower Box Score: 0");
        lowerScoreLabel.setFont(newFont);
    
        // Initialize and set the layout for the upper and lower score panels
        upperScorePanel = new JPanel();
        upperScorePanel.setLayout(new GridLayout(6, 2));
    
        lowerScorePanel = new JPanel();
        lowerScorePanel.setLayout(new GridLayout(7, 2));
    
        // Add the upper and lower box score labels to their respective panels
        upperScorePanel.add(upperScoreBoxLabel);
        upperScorePanel.add(upperScoreLabel);
    
        lowerScorePanel.add(lowerScoreBoxLabel);
        lowerScorePanel.add(lowerScoreLabel);

        // Initialize the score buttons and their action listeners
        scoreButtons = new JButton[13];
        for (int i = 0; i < 13; i++) {
            final ScoreCard.ScoreType scoreType = ScoreCard.ScoreType.values()[i];
            scoreButtons[i] = new JButton(scoreType.toString() + ": ");
            scoreButtons[i].putClientProperty("index", i);
            scoreButtons[i].setEnabled(false);
            scoreButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Calculate score for the selected scoreType and update the display
                    int score = game.getPlayer().getScoreCard().calculateScore(scoreType,
                            game.getPlayer().getDiceSet());
                    game.applyScore(scoreType, score);
                    updateScoreDisplay();
                    int index = (Integer) ((JButton) e.getSource()).getClientProperty("index");
                    lockScoreButtons[index] = true;
                    for (int j = 0; j < scoreButtons.length; j++) {
                        if (index != j)
                            scoreButtons[j].setEnabled(false);

                    }
                    rollButton.setEnabled(true);
                }
            });

            if (i < 6) {
                upperScorePanel.add(scoreButtons[i]);
            } else {
                lowerScorePanel.add(scoreButtons[i]);
            }
        }

        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Want to play another game?", "Restart",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    frame.setVisible(false);
                    YahtzeeGUI newGame = new YahtzeeGUI();
                    newGame.show();
                } else if (response == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Initialize the label for displaying the total score
        totalScoreLabel = new JLabel("Total score with bonus: 0  ");
        totalScoreLabel.setFont(newFont);

        // Add components to the main frame
        frame.add(rollButton, BorderLayout.NORTH);
        frame.add(rollCountLabel, BorderLayout.SOUTH);
        frame.add(dicePanel, BorderLayout.CENTER);
        scorePanel.add(upperScorePanel);
        scorePanel.add(lowerScorePanel);
        frame.add(scorePanel, BorderLayout.EAST);
        frame.add(totalScoreLabel, BorderLayout.WEST);
        scorePanel.add(totalScoreLabel);
        scorePanel.add(restartButton);
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
        rollCountLabel.setFont(newFont);
    }

    // Add a new method to update the display of the total score
    private void updateTotalScoreDisplay() {
        int totalScore = game.getPlayer().getScoreCard().getTotalScore();
        totalScoreLabel.setText("Total score with bonus: " + totalScore);
    }

    // Method to update the display of available score options and scores
    private void updateScoreDisplay() {
        int upperScore = 0;
        int lowerScore = 0;
        
        for (int i = 0; i < 13; i++) {
            ScoreCard.ScoreType scoreType = ScoreCard.ScoreType.values()[i];
            if (!game.getPlayer().getScoreCard().isScoreTypeAvailable(scoreType)) {
                scoreButtons[i].setEnabled(false);
                int score = game.getPlayer().getScoreCard().getScore(scoreType);
                scoreButtons[i].setText(scoreType.toString() + ": " + score);

                if (i < 6) {
                    upperScore += score;
                } else {
                    lowerScore += score;
                }
            }
        }

        upperScoreLabel.setText("Upper Box Score: " + upperScore);
        lowerScoreLabel.setText("Lower Box Score: " + lowerScore);

        updateTotalScoreDisplay();
    }

    // Method to make the main frame visible
    public void show() {
        frame.setVisible(true);
    }

    // Main method to run the Yahtzee GUI
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to create and show the GUI on the
        // event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                YahtzeeGUI gui = new YahtzeeGUI();
                gui.show();
            }
        });
    }
}