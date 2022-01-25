package Model;

import java.util.HashMap;
import java.util.Map;

public abstract class Grid {
  protected Map<Coordinate, Cell> cellMap;
  protected int numberOfColumns;
  protected int numberOfRows;

  protected Grid(int numberOfColumns, int numberOfRows) {
    this.numberOfColumns = numberOfColumns;
    this.numberOfRows = numberOfRows;
    cellMap = new HashMap<>();
  }

  public Map<Coordinate, Cell> getCellMap() {return cellMap;}


}
