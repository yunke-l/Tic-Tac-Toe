package tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * GUI Implementation of the TicTacToeView interface.
 */
public class SwingTicTacToeView extends JFrame implements TicTacToeView {

  private final JLabel displayTurn;
  private final JButton[][] board;
  private final JButton reset;
  private final JButton exit;
  private final JLabel warning;

  /**
   * Initialize the window.
   *
   * @param caption Caption for the window.
   */
  public SwingTicTacToeView(String caption) {
    super(caption);
    setPreferredSize(new Dimension(500, 500));
    setLocation(450, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set layout manager for the main frame
    this.setLayout(new BorderLayout());

    // create a label to display whose turn it is
    displayTurn = new JLabel("Turn: X");
    displayTurn.setFont(new Font("Arial", Font.BOLD, 20));
    displayTurn.setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 10));
    displayTurn.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(displayTurn, BorderLayout.NORTH);

    // create a panel for the buttons representing the game board
    JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


    // create the buttons for the game board
    board = new JButton[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = new JButton("");
        board[i][j].setFont(new Font("Arial", Font.BOLD, 80));
        board[i][j].setFocusPainted(false);
        board[i][j].setBackground(Color.WHITE);
        buttonPanel.add(board[i][j]);
      }
    }

    // add the button panel to the main frame
    this.add(buttonPanel, BorderLayout.CENTER);

    // create buttons for resetting the game and exiting the program
    reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.BOLD, 20));
    reset.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    reset.setPreferredSize(new Dimension(100, 50));

    exit = new JButton("Exit");
    exit.setFont(new Font("Arial", Font.BOLD, 20));
    exit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    exit.setPreferredSize(new Dimension(100, 50));

    // create a panel for the reset and exit buttons
    JPanel buttonPanel2 = new JPanel(new GridLayout(1, 2, 20, 0));
    buttonPanel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
    buttonPanel2.add(reset);
    buttonPanel2.add(exit);

    // create a panel for warning and button panel 2
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.add(buttonPanel2, BorderLayout.SOUTH);

    // create a panel to show the warning.
    JPanel warningPanel = new JPanel(new GridLayout(1, 5, 20, 0));
    warning = new JLabel("");
    warning.setHorizontalAlignment(JLabel.CENTER);
    warning.setFont(new Font("Arial", Font.BOLD, 20));
    southPanel.add(warning, BorderLayout.NORTH);

    // add the southPanel to the main frame
    this.add(southPanel, BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        int finalI = i;
        int finalJ = j;
        board[i][j].addActionListener(e -> features.getSelection(finalI, finalJ));
      }
    }
    reset.addActionListener(e -> features.resetGame());
    exit.addActionListener(e -> features.exitProgram());

  }

  @Override
  public void displayTurnX() {
    displayTurn.setText("Turn: X");
  }

  @Override
  public void displayTurnO() {
    displayTurn.setText("Turn: O");
  }

  @Override
  public void displaySelection(int i, int j, Player current) {
    if (current == Player.X) {
      board[i][j].setText("X");
      board[i][j].setForeground(Color.RED);

    } else {
      board[i][j].setText("O");
      board[i][j].setForeground(Color.BLUE);
    }
  }

  @Override
  public void displayWinX() {
    displayTurn.setText("X wins!");
    displayTurn.setForeground(Color.RED);
  }

  @Override
  public void displayWinO() {
    displayTurn.setText("O wins!");
    displayTurn.setForeground(Color.BLUE);
  }

  @Override
  public void displayTie() {
    displayTurn.setText("Tie game!");
    displayTurn.setForeground(Color.BLACK);
  }

  @Override
  public void restart() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j].setText("");
      }
    }
    displayTurn.setForeground(Color.BLACK);
  }


  @Override
  public void showError(String message) {
    warning.setText(message);
  }
}
