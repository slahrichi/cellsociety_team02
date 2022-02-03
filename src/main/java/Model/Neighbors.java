package Model;

public class Neighbors {

  public enum Direction {
    SQUARE,
    TRIANGULAR,
    HEXAGONAL;
  }

  public static int[] getRowDelta(Direction direction) {
    if (direction == Direction.SQUARE) {
      return new int[]{-1, 1, 0, 0, 1, 1, -1, -1};
    } else if (direction == Direction.HEXAGONAL) {
      return new int[]{-1, -1, -1, 0, 0, 1};
    } else if (direction == Direction.TRIANGULAR) {
      return new int[]{-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
    } else {
      throw new IllegalArgumentException("Invalid direction");
    }
  }

  public static int[] getColDelta(Direction direction) {
    if (direction == Direction.SQUARE) {
      return new int[]{0, 0, -1, 1, -1, 1, -1, 1};
    } else if (direction == Direction.HEXAGONAL) {
      return new int[]{-1, 0, 1, -1, 1, 0};
    } else if (direction == Direction.TRIANGULAR) {
      return new int[]{-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
    } else {
      throw new IllegalArgumentException("Invalid direction");
    }

  }

}
