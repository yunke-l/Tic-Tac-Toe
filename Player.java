package tictactoe;

/**
 * This enum Player, representing the players (X and O),
 * with a toString() method that returns "X" and "O" accordingly.
 */
public enum Player {
  X {
    public String toString() {
      return "X";
    }
  },
  O {
    public String toString() {
      return "O";
    }
  };

}


