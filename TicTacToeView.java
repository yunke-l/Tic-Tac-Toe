package tictactoe;

/**
 * Interface for a tic tac toe game's view.
 */
public interface TicTacToeView {

  /**
   * Add the Controller to the view.
   * @param features the implementation of the controller
   */
  void addFeatures(Features features);


  /**
   * Display a message to indicate it's X's turn.
   */
  void displayTurnX();


  /**
   * Display a message to indicate it's O's turn.
   */
  void displayTurnO();


  /**
   * Change the button's name to the player who selected it.
   *
   * @param i the row of the button
   * @param j the column of the button
   * @param current the current player
   */
  void displaySelection(int i, int j, Player current);


  /**
   * Display a message to indicate X wins.
   */
  void displayWinX();

  /**
   * Display a message to indicate O wins.
   */
  void displayWinO();


  /**
   * Display a message to indicate game ends in a tie.
   */
  void displayTie();


  /**
   * Restart the game.
   */
  void restart();

  /**
   * show the error message.
   * @param warning the error message that is thrown.
   */
  void showError(String warning);


}
