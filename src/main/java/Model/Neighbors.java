package Model;

public class Neighbors {

  public enum Directions {
    SQUARE,
    TRIANGULAR,
    HEXAGONAL;
  }

  public static int[] getRowDelta(Directions direction) {
    if (direction == Directions.SQUARE) return new int[]{-1, 1, 0, 0, 1, 1, -1, -1};
    else if (direction == Directions.HEXAGONAL) return new int[]{-1, -1, -1, 0, 0, 1};
    else if (direction == Directions.TRIANGULAR) return new int[]{-1, -1, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1};
    else throw new IllegalArgumentException("Invalid direction");
  }

  public static int[] getColDelta(Directions direction) {
    if (direction == Directions.SQUARE) return new int[]{0, 0, -1, 1, -1, 1, -1, 1};
    else if (direction == Directions.HEXAGONAL) return new int[]{-1, 0, 1, -1, 1, 0};
    else if (direction == Directions.TRIANGULAR) return new int[]{-2, -1, 0, 1, 2, -2, -1, 1, 2, -1, 0, 1};
    else throw new IllegalArgumentException("Invalid direction");

  }

}
