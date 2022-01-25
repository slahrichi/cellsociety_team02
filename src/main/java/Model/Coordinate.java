package Model;

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

}
