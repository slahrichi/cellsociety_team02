package Model;

import java.util.List;

public class Neighbors {

  private static final int[] SQUARE_ROW = {-1, 1, 0, 0, 1, 1, -1, -1};
  private static final int[] HEXAGONAL_ROW = {-1, -1, -1, 0, 0, 1};
  private static final int[] TRIANGULAR_ROW = {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
  private static final int[] SQUARE_COL = {0, 0, -1, 1, -1, 1, -1, 1};
  private static final int[] HEXAGONAL_COL = {-1, 0, 1, -1, 1, 0};
  private static final int[] TRIANGULAR_COL = {-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
  private static final int FIRST = 0;
  private static final int FULL = -1;
  private static final String INVALID = "Invalid message";

  public enum Direction {
    SQUARE,
    TRIANGULAR,
    HEXAGONAL;
  }

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
    if (isFull(config)) return neighbors;
    int size = config.size();
    int[] coords = new int[size];
    for (int i = 0; i < size; i++) {
      coords[i] = neighbors[config.get(i)];
    }
    return coords;
  }

  private static boolean isFull(List<Integer> neighbors) {
    return neighbors.size() == 1 && neighbors.get(FIRST) == FULL;
  }

}
