package Model;

public class Neighbors {

  private static final int[] SQUARE_ROW = {-1, 1, 0, 0, 1, 1, -1, -1};
  private static final int[] HEXAGONAL_ROW = {-1, -1, -1, 0, 0, 1};
  private static final int[] TRIANGULAR_ROW = {-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
  private static final int[] SQUARE_COL = {0, 0, -1, 1, -1, 1, -1, 1};
  private static final int[] HEXAGONAL_COL = {-1, 0, 1, -1, 1, 0};
  private static final int[] TRIANGULAR_COL = {-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};

  public enum Direction {
    SQUARE,
    TRIANGULAR,
    HEXAGONAL;
  }

  public static int[] getRowDelta(Direction direction) {
    if (direction == Direction.SQUARE) {
      return getNeighbors(SQUARE_ROW);
    } else if (direction == Direction.HEXAGONAL) {
      return getNeighbors(HEXAGONAL_ROW);
    } else if (direction == Direction.TRIANGULAR) {
      return getNeighbors(TRIANGULAR_ROW);
    } else {
      throw new IllegalArgumentException("Invalid direction");
    }
  }

  public static int[] getColDelta(Direction direction) {
    if (direction == Direction.SQUARE) {
      return getNeighbors(SQUARE_COL);
    } else if (direction == Direction.HEXAGONAL) {
      return getNeighbors(HEXAGONAL_COL);
    } else if (direction == Direction.TRIANGULAR) {
      return getNeighbors(TRIANGULAR_COL);
    } else {
      throw new IllegalArgumentException("Invalid direction");
    }
  }

  private static int[] getNeighbors(int[] neighbors) {
    if (isFull(neighbors)) return neighbors;
    return null;
  }

  private static boolean isFull(int[] neighbors) {
    return neighbors.length == 1 && neighbors[0] == -1;
  }

}
