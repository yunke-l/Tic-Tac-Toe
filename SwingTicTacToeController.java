package tictactoe;

/**
 * Controller. Implementation of the Features interface.
 */
public class SwingTicTacToeController implements Features {
  private TicTacToe model;
  private TicTacToeView view;

  /**
   * Constructor for the controller.
   *
   * @param v  the view of the Tic Tac Toe game.
   * @param m the model of the Tic Tac Toe game.
   */
  public SwingTicTacToeController(TicTacToeView v, TicTacToe m) {
    model = m;
    view = v;
  }

  @Override
  public void setView(TicTacToeView v) {
    v.addFeatures(this);
  }


  @Override
  public void printTurn() {
    if (model.getTurn() == Player.X) {
      view.displayTurnX();
    } else {
      view.displayTurnO();
    }
  }


  @Override
  public void getSelection(int i, int j) {
    if (!model.isGameOver()) {
      boolean isInvalid = false;
      Player currentPlayer = model.getTurn();
      try {
        model.move(i, j);
      } catch (IllegalStateException | IllegalArgumentException e) {
        view.showError(e.getMessage());
        isInvalid = true;

      }
      if (!isInvalid) {
        printTurn();
        view.displaySelection(i, j, currentPlayer);
        view.showError("");
      }
      if (model.isGameOver()) {
        if (model.getWinner() == Player.O) {
          view.displayWinO();
        } else if (model.getWinner() == Player.X) {
          view.displayWinX();
        } else {
          view.displayTie();
        }
      }
    }
  }


  @Override
  public void resetGame() {
    model = new TicTacToeModel();
    view.restart();
    printTurn();
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }


}
