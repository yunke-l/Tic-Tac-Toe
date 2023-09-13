package tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents the game logic for a Tic Tac Toe game.
 * It keeps track of the current state of the game board
 * and determines if the game has been won or if it is a tie.
 * It offers all the operations mandated by the TicTacToe interface,
 * and two helper functions checkForWinner() and switchPlayer().
 */
public class TicTacToeModel implements TicTacToe {
  private Player[][] board;
  private Player currentPlayer;
  private boolean gameOver;
  private Player winner;
  private int numMoves;

  /**
   * Construct a TicTacToeModel object that takes no arguments.
   * The object creates a 3 * 3 null board, sets the current player as X,
   * game over status as false, winner as null, and number of moves as 0.
   */
  public TicTacToeModel() {
    // create a 3 * 3 null board
    board = new Player[3][3];
    currentPlayer = Player.X;
    gameOver = false;
    winner = null;
    numMoves = 0;
  }


  /**
   * Checks if the game is over by checking all possible winning combinations
   * (rows, columns, and diagonals) and
   * if the board is full (meaning the game is a tie).
   * If a winning combination is found, sets the {@code gameOver} flag
   * and sets the {@code winner} field to the winning player.
   * If the board is full and there is no winner, sets the {@code gameOver} flag to true.
   */
  private void checkForWinner() {
    // check rows
    for (int i = 0; i < 3; i++) {
      if (board[i][0] != null
          && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        winner = board[i][0];
        gameOver = true;
        return;
      }
    }
    // check columns
    for (int j = 0; j < 3; j++) {
      if (board[0][j] != null
          && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
        winner = board[0][j];
        gameOver = true;
        return;
      }
    }
    // check diagonals
    if (board[0][0] != null
        && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      winner = board[0][0];
      gameOver = true;
      return;
    }
    if (board[0][2] != null
        && board[0][2] == board [1][1] && board[1][1] == board[2][0]) {
      winner = board[0][2];
      gameOver = true;
      return;
    }
    // check tie
    if (numMoves == 9) {
      gameOver = true;
    }
  }


  /**
   * Switches the current player by setting {@code currentPlayer} to the other player.
   */
  private void switchPlayer() {
    if (currentPlayer == Player.X) {
      currentPlayer = Player.O;
    } else {
      currentPlayer = Player.X;
    }
  }


  @Override
  public void move(int r, int c) {
    // the game is over
    if (gameOver) {
      throw new IllegalStateException("The game is over.");
    }

    // the input r or c is invalid
    if (r < 0 || r > 2 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Invalid position: r and c must be 0, 1, 2");
    }

    // the position is occupied
    if (board[r][c] != null) {
      throw new IllegalArgumentException("Invalid position: position is already occupied");
    }

    board[r][c] = currentPlayer;
    numMoves++;
    checkForWinner();
    switchPlayer();
  }


  @Override
  public Player getTurn() {
    return currentPlayer;
  }

  @Override
  public boolean isGameOver() {
    return gameOver;
  }

  @Override
  public Player getWinner() {
    return winner;
  }

  @Override
  public Player[][] getBoard() {
    Player[][] copyBoard = new Player[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        copyBoard[i][j] = board[i][j];
      }
    }
    return copyBoard;
  }

  @Override
  public Player getMarkAt(int r, int c) {
    if (r < 0 || r > 2 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Invalid position: r and c must be 0, 1, 2");
    }

    return board[r][c];
  }


  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
            row -> " " + Arrays.stream(row).map(
                p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
        .collect(Collectors.joining("\n-----------\n"));

  }

}
