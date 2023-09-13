package tictactoe;

/**
 * Run and play the Tic Tac Toe game on a GUI.
 */
public class Main {
  /**
   * Run and play the Tic Tac Toe game on a GUI.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    TicTacToe m = new TicTacToeModel();
    TicTacToeView v = new SwingTicTacToeView("Tic-Tac-Toe");
    Features c = new SwingTicTacToeController(v, m);
    c.setView(v);
  }
}
