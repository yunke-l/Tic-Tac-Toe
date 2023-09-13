import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import tictactoe.Player;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeModel;

/**
 * Test cases for the tic tac toe model. Verifying that game state is properly managed, and
 * all game actions are properly validated.
 */
public class TicTacToeModelTest {

  private TicTacToe ttt1 = new TicTacToeModel();


  /**
   * Test that the game starts with Player X.
   */
  @Test
  public void testGameStart() {
    assertEquals(Player.X, ttt1.getTurn());
  }

  /**
   * Test that the player whose turn it is changes when the move() method is called.
   */
  @Test
  public void testMove() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
  }

  /**
   * Test that a move cannot be made to an invalid row and/or column,
   * or to a square that is already filled in.
   */
  @Test
  public void testInvalidMove() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    assertEquals(Player.X, ttt1.getMarkAt(0, 0));

    // square already be filled in
    try {
      ttt1.move(0, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      assertTrue(iae.getMessage().length() > 0);
    }

    // row out of bound
    try {
      ttt1.move(-1, 0);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      assertTrue(iae.getMessage().length() > 0);
    }

    // column out of bound
    try {
      ttt1.move(0, 4);
      fail("Invalid move should have thrown exception");
    } catch (IllegalArgumentException iae) {
      assertTrue(iae.getMessage().length() > 0);
    }
  }

  /**
   * Test that a move cannot be made after the game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveAttemptAfterGameOver() {
    diagonalWinHelper();
    ttt1.move(2, 2); // 2,2 is an empty position
  }


  /**
   * Test that the getMarkAt method returns the correct value.
   */
  @Test
  public void testValidGetMarkAt() {
    ttt1.move(1, 0); // X takes middle left
    assertEquals(Player.X, ttt1.getMarkAt(1, 0));
    ttt1.move(0, 2); // O takes upper right
    assertEquals(Player.O, ttt1.getMarkAt(0, 2));
  }

  /**
   * Test that the getMarkAt method throws an exception for an invalid row.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtRow() {
    ttt1.getMarkAt(-12, 0);
    ttt1.getMarkAt(12, 0);
  }

  /**
   * Test that the getMarkAt method throws an exception for an invalid column.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtCol() {
    ttt1.getMarkAt(0, -30);
    ttt1.getMarkAt(0, 30);
  }

  /**
   * Test that the getMarkAt method throws an exception for both invalid row and column.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGetMarkAtRowAndCol() {
    ttt1.getMarkAt(12, -30);
    ttt1.getMarkAt(-12, 30);
  }


  /**
   * Test if the isGameOver method returns true when the game is over,
   * returns false when the game is not over.
   */
  @Test
  public void testIsGameOver() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 0); // X takes lower left
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 2); // X takes upper right
    assertFalse(ttt1.isGameOver());
    ttt1.move(0, 1); // O takes upper center
    ttt1.move(2, 2); // X takes lower right
    ttt1.move(2, 1); // O takes lower center
    assertTrue(ttt1.isGameOver());
    assertEquals(" X | O | X\n"
                        + "-----------\n"
                        + " O | O |  \n"
                        + "-----------\n"
                        + " X | O | X", ttt1.toString());
  }


  /**
   * Test if a horizontal win properly ends the game.
   */
  @Test
  public void testHorizontalWin() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(0, 1); // X takes upper middle
    assertNull(ttt1.getWinner());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(0, 2); // X takes upper right
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X | X | X\n"
                        + "-----------\n"
                        + " O |   |  \n"
                        + "-----------\n"
                        + " O |   |  ", ttt1.toString());
  }


  /**
   * Test if a vertical win properly ends the game.
   */
  @Test
  public void testVerticalWin() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 1); // O takes center
    ttt1.move(1, 0); // X takes middle left
    assertNull(ttt1.getWinner());
    ttt1.move(1, 2); // O takes middle right
    ttt1.move(2, 0); // X takes lower left
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X |   |  \n"
                        + "-----------\n"
                        + " X | O | O\n"
                        + "-----------\n"
                        + " X |   |  ", ttt1.toString());
  }


  /**
   * Test if a diagonal win properly ends the game.
   */
  @Test
  public void testDiagonalWin() {
    diagonalWinHelper();
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.O, ttt1.getWinner());
    assertEquals(" X | X | O\n"
                        + "-----------\n"
                        + " X | O |  \n"
                        + "-----------\n"
                        + " O |   |  ", ttt1.toString());
  }

  // set up situation where game is over, O wins on the diagonal, board is not full
  private void diagonalWinHelper() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(2, 0); // O takes lower left
    ttt1.move(1, 0); // X takes middle left
    assertNull(ttt1.getWinner());
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 1); // X takes upper middle
    ttt1.move(0, 2); // O takes upper right
  }

  /**
   * Test another diagonal win scenario.
   */
  @Test
  public void testVerticalWin2() {
    ttt1.move(0, 0); // X takes upper left
    assertFalse(ttt1.isGameOver());
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(1, 1); // X takes center
    assertNull(ttt1.getWinner());
    ttt1.move(1, 2); // O takes middle right
    ttt1.move(2, 2); // X takes lower right
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X |   |  \n"
                        + "-----------\n"
                        + " O | X | O\n"
                        + "-----------\n"
                        + "   |   | X", ttt1.toString());
  }

  /**
   * Test if a tied game (called a Cat's game) properly ends the game.
   */
  @Test
  public void testCatsGame() {
    ttt1.move(0, 0);
    assertEquals(Player.O, ttt1.getTurn());
    ttt1.move(1, 1);
    assertEquals(Player.X, ttt1.getTurn());
    ttt1.move(0, 2);
    ttt1.move(0, 1);
    ttt1.move(2, 1);
    ttt1.move(1, 0);
    ttt1.move(1, 2);
    ttt1.move(2, 2);
    ttt1.move(2, 0);
    assertTrue(ttt1.isGameOver());
    assertNull(ttt1.getWinner());
    assertEquals(" X | O | X\n"
        + "-----------\n"
        + " O | O | X\n"
        + "-----------\n"
        + " X | X | O", ttt1.toString());
  }


  /**
   * Test if the getWinner method returns a winner when there is a winner.
   */
  @Test
  public void testGetWinner() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 0); // X takes lower left
    assertNull(ttt1.getWinner());
    ttt1.move(1, 1); // O takes center
    ttt1.move(0, 2); // X takes upper right
    ttt1.move(0, 1); // O takes upper center
    ttt1.move(2, 2); // X takes lower right
    assertNull(ttt1.getWinner());
    ttt1.move(2, 1); // O takes lower center
    assertEquals(Player.O, ttt1.getWinner());
    assertEquals(" X | O | X\n"
        + "-----------\n"
        + " O | O |  \n"
        + "-----------\n"
        + " X | O | X", ttt1.toString());
  }


  /**
   * Test if getBoard method works properly.
   */
  @Test
  public void testGetBoard() {
    diagonalWinHelper();
    Player[][] bd = ttt1.getBoard();
    assertEquals(Player.X, bd[0][0]);
    assertEquals(Player.O, bd[1][1]);
    assertEquals(Player.X, bd[0][1]);
    // attempt to cheat by mutating board returned by getBoard()
    // check correct preconditions
    assertEquals(Player.O, bd[2][0]);
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    bd[2][0] = Player.X;  // mutate
    // check correct post conditions
    assertEquals(Player.O, ttt1.getMarkAt(2, 0));
    Player[][] bd2 = ttt1.getBoard();
    assertEquals(Player.O, bd2[2][0]);
  }



  /**
   * Test case where board is full AND there is a winner.
   */
  @Test
  public void testFullBoardWithWinner() {
    ttt1.move(0, 0); // X takes upper left
    ttt1.move(1, 0); // O takes middle left
    ttt1.move(2, 0); // X takes lower left
    ttt1.move(0, 1); // O takes upper center
    ttt1.move(1, 1); // X takes center
    ttt1.move(1, 2); // O takes middle right
    ttt1.move(2, 1); // X takes lower middle
    ttt1.move(2, 2); // O takes lower right
    ttt1.move(0, 2); // X takes upper right
    assertTrue(ttt1.isGameOver());
    assertEquals(Player.X, ttt1.getWinner());
    assertEquals(" X | O | X\n"
                        + "-----------\n"
                        + " O | X | O\n"
                        + "-----------\n"
                        + " X | X | O", ttt1.toString());
  }

}
