import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * A simple implementation of the Tic Tac Toe game.
 * The game allows to players to play against each other on a 3x3 grid.
 * The game will display a message when a player wins or when the game is a draw.
 * The game will also keep track of the score of each player.
 * The game will allow the players to play again or view the game.
 */
class TicTacToe {
    private JFrame frame; // Main frame for the game.
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons for the game.
    private boolean XTurn = true; // Flag to keep track of the current player.
    private boolean gameOver = false; // Flag to keep track of the game state.
    private JButton resetButton; // Button to reset the game.
    private int xScore, oScore = 0; // Scores of the players.
    private JPanel scorePanel; // Panel to display the scores.
    private JLabel xScoreLabel; // Label to display the score of player X.
    private JLabel oScoreLabel; // Label to display the score of player O.
    private JLabel currentPlayer; // Label to display the current player.

    /**
     * Main method to start the game.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new TicTacToe();
    }

    /**
     * Constructor to initialize the game UI.
     */
    public TicTacToe() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(400, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        frame.add(gridPanel, BorderLayout.CENTER);

        // Initialize the score panel.
        scorePanel = new JPanel(new FlowLayout());
        scorePanel.setBackground(Color.decode("#113128"));
        xScoreLabel = new JLabel("X: " + xScore);
        oScoreLabel = new JLabel("O: " + oScore);
        currentPlayer = new JLabel("X's Turn");

        // Personalize labels.
        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 20);
        xScoreLabel.setFont(labelFont);
        oScoreLabel.setFont(labelFont);
        currentPlayer.setFont(labelFont);
        currentPlayer.setForeground(Color.decode("#c0e9dc"));
        xScoreLabel.setForeground(Color.decode("#c0e9dc"));
        oScoreLabel.setForeground(Color.decode("#c0e9dc"));

        scorePanel.add(xScoreLabel);
        scorePanel.add(Box.createHorizontalStrut(20));
        scorePanel.add(oScoreLabel);
        scorePanel.add(Box.createHorizontalStrut(80));
        scorePanel.add(currentPlayer);
        frame.add(scorePanel, BorderLayout.NORTH);

        // Initialize the reset button.
        resetButton = new JButton("Reset Game");
        resetButton.setFont(labelFont);
        resetButton.setBackground(Color.decode("#113128"));
        resetButton.setForeground(Color.decode("#c0e9dc"));
        resetButton.addActionListener(e -> resetGame());
        frame.add(resetButton, BorderLayout.SOUTH);

        // Initialize the game board.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i, col = j;
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 50));
                buttons[i][j].setBackground(Color.decode("#c0e9dc"));
                buttons[i][j].setForeground(Color.decode("#113128"));
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.decode("#43bc98"), 2, true));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        buttons[row][col].setBackground(Color.decode("#95d9c5"));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        buttons[row][col].setBackground(Color.decode("#c0e9dc"));
                    }
                });
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gridPanel.add(buttons[i][j]);
            }
        }
        frame.setVisible(true);
    }

    /**
     * Resets the game board and allows players to play again.
     */
    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        gameOver = false;
        XTurn = true;
        updateCurrentPlayer();
    }

    /**
     * ActionListener to handle the button clicks on the game board.
     */
    private class ButtonClickListener implements ActionListener {
        private int i, j;

        public ButtonClickListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[i][j].getText().equals("") && !gameOver) {
                buttons[i][j].setText(XTurn ? "X" : "O");
                if (CheckWin()) {
                    displayWinner(XTurn ? "X" : "O");
                } else if (CheckDraw()) {
                    displayWinner("");
                } else {
                    XTurn = !XTurn;
                    updateCurrentPlayer();
                }
            }
        }

        private boolean CheckWin() {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText()) && !buttons[i][0].getText().equals("") || 
                buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[1][i].getText().equals(buttons[2][i].getText()) && !buttons[0][i].getText().equals("")) {
                    return true;
                }
            }
            if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][2].getText()) && !buttons[0][0].getText().equals("") || 
            buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[1][1].getText().equals(buttons[2][0].getText()) && !buttons[0][2].getText().equals("")) {
                return true;
            }
            return false;
        }

        private boolean CheckDraw() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void displayWinner(String winner) {
            Object[] options = {"Play Again", "View Game"};
            int choice;
            
            UIManager.put("OptionPane.background", Color.decode("#113128"));
            UIManager.put("Panel.background", Color.decode("#113128"));      // Panel color
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("OptionPane.messageFont", new Font("Comic Sans MS", Font.BOLD, 18)); // Custom font
            UIManager.put("Button.background", Color.decode("#c0e9dc"));      // Button color
            UIManager.put("Button.foreground", Color.decode("#113128"));      // Button text color
            
            if (winner.isEmpty()) {
                choice = JOptionPane.showOptionDialog(frame, "Draw!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            } else {    
                updateScore(winner);
                choice = JOptionPane.showOptionDialog(frame, winner + " wins!", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            }
            if (choice == 0) {
                resetGame();
            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            }  
        }

        private void updateScore(String winner) {
            if (winner.equals("X")) {
                xScore++;
            }
            else {
                oScore++;
            }
            xScoreLabel.setText("X: " + xScore);
            oScoreLabel.setText(" O: " + oScore);
        }
    }

    /**
     * Updates the current player label.
     */
    private void updateCurrentPlayer() {
        if (XTurn) {
            currentPlayer.setText("X's Turn");
        } else {
            currentPlayer.setText("O's Turn");
        }
    }
}