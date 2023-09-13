import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import org.junit.Test;
import tictactoe.FailingAppendable;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeConsoleController;
import tictactoe.TicTacToeController;
import tictactoe.TicTacToeModel;


/**
 * Test cases for the tic tac toe controller, using mocks for readable and appendable.
 */
public class TicTacToeControllerTest {

  /**
   * Test for single valid input.
   */
  @Test
  public void testSingleValidMove() {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 q"), gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for bogus input as row.
   */
  @Test
  public void testBogusInputAsRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("!#$ 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    // split the output into an array of lines
    String[] lines = gameLog.toString().split("\n");
    // check that it's the correct number of lines
    assertEquals(13, lines.length);
    // check that the last 6 lines are correct
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  ", lastMsg);
    // note no trailing \n here, because of the earlier split
  }

  /**
   * Test for tie game.
   */
  @Test
  public void testTieGame() {
    TicTacToe m = new TicTacToeModel();
    // note the entire sequence of user inputs for the entire game is in this one string:
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(60, lines.length);
    assertEquals("Game is over! Tie game.", lines[lines.length - 1]);
  }

  /**
   * Test for failing appendable.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Test to play game to completion, where X is winner.
   */
  @Test
  public void testWinsX() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 2 1 1 2 2 2 2 3 3 1 3 2 3 3 1 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(60, lines.length);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
  }

  /**
   * Test to play game to completion, where O is winner.
   */
  @Test
  public void testWinsO() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 2 1 1 2 1 2 2 2 3 1 3 3 2 3 1");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(54, lines.length);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
  }

  /**
   * Test for input where the q comes instead of an integer for the row.
   */
  @Test
  public void testQuitForRow() {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 q 1 1 3"),
        gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for input where the q comes instead of an integer for the column.
   */
  @Test
  public void testQuitForCol() {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 1 q 1 3"),
        gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }


  /**
   * Test for input where non-integer garbage comes instead of an integer for the row.
   */
  @Test
  public void testGarbageInputAsRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 1 JAVA! 2 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Not a valid number: JAVA!\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   | O |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Game quit! Ending game state:\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   | O |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for input where non-integer garbage comes instead of an integer for the column.
   */
  @Test
  public void testGarbageInputAsCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("1 JAVA! 1 2 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Not a valid number: JAVA!\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   | O |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Game quit! Ending game state:\n"
        + " X |   |  \n"
        + "-----------\n"
        + "   | O |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for input where the move is integers, but outside the bounds of the board.
   */
  @Test
  public void testForOutOfBound() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 5 2 2 Q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Not a valid move: 2, 5\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for input where the move is integers, but invalid because the cell is occupied.
   */
  @Test
  public void testForCellOccupied() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 2 2 1 1 Q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Not a valid move: 2, 2\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + " O |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Game quit! Ending game state:\n"
        + " O |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }


  /**
   * Test for multiple invalid moves in a row of various kinds.
   */
  @Test
  public void testForMultipleInvalidMovesInRow() {
    TicTacToe m = new TicTacToeModel();
    // first occupy (2,2), then use invalid moves (5, 2), (JAVA! 2),
    // and then try to occupy (2,2) again.
    StringReader input = new StringReader("2 2 5 2 JAVA! 2 2 2 1 1 Q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Not a valid move: 5, 2\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Not a valid number: JAVA!\n"
        + "Not a valid move: 2, 2\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        +  "   |   |  \n"
        + "-----------\n"
        + " O | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + " O | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for input including valid moves interspersed with invalid moves,
   * game is played to completion.
   */
  @Test
  public void testGameCompletionWithBothValidAndInvalidMoves() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 JAVA! 3 3 $#% 2 1 5 2 1 -6 1 3 1 3 3 1 1 1 2 2 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    // split the output into an array of lines
    String[] lines = gameLog.toString().split("\n");
    // check that it's the correct number of lines
    assertEquals(76, lines.length);
    // check that the last 6 lines are correct
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals(" X | O | O\n"
        + "-----------\n"
        + " O | X | X\n"
        + "-----------\n"
        + " X | X | O\n"
        + "Game is over! Tie game.", lastMsg);
    // note no trailing \n here, because of the earlier split
  }

  /**
   * Test for what happens when the input ends "abruptly" -- no more input,
   * but not quit, and game not over.
   */
  @Test(expected = IllegalStateException.class)
  public void testForInputEndsAbruptly() {
    try {
      TicTacToe m = new TicTacToeModel();
      StringReader input = new StringReader("2 2 1 1");
      StringBuilder gameLog = new StringBuilder();
      TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
      c.playGame(m);
    } catch (IllegalArgumentException ex) {
      assertEquals("Input stream closed before game ended.", ex.getMessage());
      throw ex;
    }
    fail("IllegalStateException did not throw.");
  }


  /**
   * Test for what happens when the model is invalid.
   * (controller throws an Illegal Argument Exception)
   */
  @Test
  public void testForInvalidModel() {
    try {
      StringReader input = new StringReader("2 2 1 1 q");
      StringBuilder gameLog = new StringBuilder();
      TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
      c.playGame(null);
      fail("IllegalArgumentException did not throw.");
    } catch (IllegalArgumentException ex) {
      assertEquals("Model cannot be null.", ex.getMessage());
    }
  }

  /**
   * Test for failing appendable with appending different things.
   */
  @Test
  public void testAppend() {
    FailingAppendable failingAppendable = new FailingAppendable();
    try {
      failingAppendable.append("test");
      fail("IOException did not throw");
    } catch (IOException ex) {
      assertEquals("Fail!", ex.getMessage());
    }

    try {
      failingAppendable.append("test", 0, 2);
      fail("IOException did not throw");
    } catch (IOException e) {
      assertEquals("Fail!", e.getMessage());
    }

    try {
      failingAppendable.append('t');
      fail("IOException did not throw");
    } catch (IOException e) {
      assertEquals("Fail!", e.getMessage());
    }
  }

}

