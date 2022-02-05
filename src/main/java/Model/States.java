package Model;

/**
 * class for holding all the possible states for each simulation
 *
 * @author Matthew Giglio
 */
public class States {

  /**
   * states for SpreadingFire
   */
  public enum SpreadingFire {
    TREE,
    BURNING,
    EMPTY;
  }

  /**
   * states for Percolation
   */
  public enum Percolation {
    OPEN,
    PERCOLATED,
    BLOCKED;
  }

  /**
   * states for GameOfLife
   */
  public enum GameOfLife {
    DEAD,
    ALIVE;
  }

  /**
   * states for Segregation
   */
  public enum Segregation {
    EMPTY,
    REP,
    DEM;
  }

  /**
   * states for WaTor
   */
  public enum WaTor {
    EMPTY,
    FISH,
    SHARK;
  }

  /**
   * states for RockPaperScissors
   */
  public enum RockPaperScissors {
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * method to check the winning neighbor of a cell
     *
     * @param currentState current state of cell
     * @return the state which beats the state of the current cell
     */
    public static Enum getWinningNeighbor(Enum currentState) {
      if (currentState == SCISSORS) {
        return ROCK;
      } else if (currentState == PAPER) {
        return SCISSORS;
      } else {
        return PAPER;
      }
    }

  }

  /**
   * states for FallingSand
   */
  public enum FallingSand {
    EMPTY,
    METAL,
    SAND,
    WATER;
  }

}
