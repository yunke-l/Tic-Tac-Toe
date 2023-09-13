package tictactoe;

/**
 * Interface for a tic tac toe game's controller.
 */
public interface Features {

  /**
   * Provide view with all the callbacks.
   * @param v the view.
   */
  public void setView(TicTacToeView v);


  /**
   * Print which player's turn is.
   */
  void printTurn();


  /**
   * Get the button selected by user.
   *
   * @param i the row of the button
   * @param j the column of the button
   */
  void getSelection(int i, int j);


  /**
   * Reset the game.
   */
  void resetGame();


  /**
   * Exit the game.
   */
  void exitProgram();



}
