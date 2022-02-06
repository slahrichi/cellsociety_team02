package Model;

import java.util.List;

/**
 * class for holding infrastructure for different cell shapes. Provides customized row and column
 * delta arrays for the cell classes to consider their neighbors given the cell shape and a
 * requested configuration of neighbors
 *
 * @author Matthew Giglio
 */

public class Neighbors {

  private static final int[] SQUARE_ROW = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static final int[] SQUARE_COL = {-1, 0, 1, -1, 1, -1, 0, 1};
  private static final int[] HEXAGONAL_ROW = {-1, -1, -1, 0, 0, 1};
  private static final int[] HEXAGONAL_COL = {-1, 0, 1, -1, 1, 0};
  private static final int[] TRIANGULAR_ROW = {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
  private static final int[] TRIANGULAR_COL = {-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
  private static final int FIRST = 0;
  private static final int FULL = -1;
  private static final String INVALID = "Invalid message";

  /**
   * enums for holding possible states for shape organization of cell
   */
  public enum Direction {
    SQUARE,
    TRIANGULAR,
    HEXAGONAL;
  }

  /**
   * method for getting the row coordinates of all neighbors to be considered
   *
   * @param direction shape configuration of cell
   * @param config    list of neighbors from which should be considered
   * @return an int[] of selected row coordinates
   */
  public static int[] getRowDelta(Direction direction, List<Integer> config) {
    if (direction == Direction.SQUARE) {
      return getNeighbors(SQUARE_ROW, config);
    } else if (direction == Direction.HEXAGONAL) {
      return getNeighbors(HEXAGONAL_ROW, config);
    } else if (direction == Direction.TRIANGULAR) {
      return getNeighbors(TRIANGULAR_ROW, config);
    } else {
      throw new IllegalArgumentException(INVALID);
    }
  }

  /**
   * method for getting the row coordinates of all neighbors to be considered
   *
   * @param direction shape configuration of cell
   * @param config    list of neighbors from which should be considered
   * @return an int[] of column coordinates
   */
  public static int[] getColDelta(Direction direction, List<Integer> config) {
    if (direction == Direction.SQUARE) {
      return getNeighbors(SQUARE_COL, config);
    } else if (direction == Direction.HEXAGONAL) {
      return getNeighbors(HEXAGONAL_COL, config);
    } else if (direction == Direction.TRIANGULAR) {
      return getNeighbors(TRIANGULAR_COL, config);
    } else {
      throw new IllegalArgumentException(INVALID);
    }
  }

  private static int[] getNeighbors(int[] neighbors, List<Integer> config) {
    if (isFull(config)) {
      return neighbors;
    } else if (config.size() > neighbors.length) {
      throw new IllegalArgumentException("Neighbor configuration has more neighbors than"
          + "physically possible");
    }
    int size = config.size();
    int[] coords = new int[size];
    for (int i = 0; i < size; i++) {
      int index = config.get(i);
      if (index < 0 || index >= neighbors.length) {
        throw new IllegalArgumentException("Index is out of range of requested neighbor formation");
      }
      coords[i] = neighbors[config.get(i)];
    }
    return coords;
  }

  private static boolean isFull(List<Integer> neighbors) {
    return neighbors.size() == 1 && neighbors.get(FIRST) == FULL;
  }

}
