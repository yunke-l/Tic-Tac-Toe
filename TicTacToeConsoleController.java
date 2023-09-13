package tictactoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller implementation of Tic Tac Toe game.
 * Allows users to play the game on the console.
 */
public class TicTacToeConsoleController implements TicTacToeController {

  final Readable in;
  final Appendable out;

  /**
   * Constructor for the controller.
   *
   * @param in  the input provided by the user.
   * @param out the output of the Tic Tac Toe game.
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void playGame(TicTacToe m) throws IllegalStateException {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    Scanner scan = new Scanner(this.in);
    List<Integer> pair = new ArrayList<>();
    while (!m.isGameOver()) {
      // ask for user input
      printMovePrompt(m);

      pair.clear();

      while (pair.size() < 2) {
        if (!scan.hasNext()) {
          throw new IllegalStateException("Input stream closed before game ended.");
        }
        String item = scan.next();

        // check for quit command
        if ("q".equalsIgnoreCase(item)) {
          printGameQuit(m);
          return;
        }

        // check for valid number
        try {
          int num = Integer.parseInt(item);
          pair.add(num);
        } catch (NumberFormatException e) {
          printError("Not a valid number: " + item);
        }
      }

      int row = pair.get(0) - 1;
      int col = pair.get(1) - 1;

      // move
      try {
        m.move(row, col);
      } catch (IllegalArgumentException e) {
        printError("Not a valid move: " + (row + 1) + ", " + (col + 1));
      }
    }

    //output final game state and result
    printGameResult(m);
  }


  /**
   * Prints the current game state to the console.
   */
  private void printGameState(TicTacToe m) {
    try {
      this.out.append(m.toString() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }


  /**
   * Prints the prompt asking the user to enter a move.
   */
  private void printMovePrompt(TicTacToe m) {
    try {
      printGameState(m);
      this.out.append("Enter a move for " + m.getTurn().toString() + ":\n");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }


  /**
   * Prints the final game state and result to the console.
   */
  private void printGameResult(TicTacToe m) {
    try {
      printGameState(m);
      this.out.append("Game is over! ");
      if (m.getWinner() == null) {
        this.out.append("Tie game.\n");
      } else if (m.getWinner() == Player.X) {
        this.out.append("X wins.\n");
      } else {
        this.out.append("O wins.\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error writing to output", e);
    }
  }


  /**
   * Prints the quit message and ending game state to the console.
   */
  private void printGameQuit(TicTacToe m) {
    try {
      out.append("Game quit! Ending game state:\n").append(m.toString()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error writing to output", e);
    }
  }


  /**
   * Prints the error message to the console.
   */
  private void printError(String message) {
    try {
      this.out.append(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
