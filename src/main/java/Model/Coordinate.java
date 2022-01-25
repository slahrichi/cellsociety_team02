package Model;

import java.util.Objects;

public class Coordinate {

  private int row;
  private int column;

  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getRow() {return row;}

  public int getColumn() {return column;}

  public Coordinate checkNeighbors(int r, int c) {
    return new Coordinate(row + r, column + c);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return row == that.row && column == that.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }

  @Override
  public String toString() {
    return "Coordinate{" +
        "row=" + row +
        ", column=" + column +
        '}';
  }
}
