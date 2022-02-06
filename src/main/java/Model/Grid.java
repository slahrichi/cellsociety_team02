package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for representing the grid of a cellular automata given a desired number of grids
 * and columns. The grid itself is stored in a `Map<Coordinate, Cell>` to represent the grid as a
 * graph, and protected methods are leveraged to limit the usage of internal methods to extensions
 * of the class.
 *
 * @author Matthew Giglio
 */
public abstract class Grid {

  private Map<Coordinate, Cell> cellMap;
  private int numberOfColumns;
  private int numberOfRows;

  public Grid(int numberOfRows, int numberOfColumns) {
    this.numberOfColumns = numberOfColumns;
    this.numberOfRows = numberOfRows;
    cellMap = new HashMap<>();
  }

  /**
   * getter method for getting the Map object that stores the graph representation of the grid
   *
   * @return the map holding the graph representation of the object
   */
  public Map<Coordinate, Cell> getCellMap() {
    return cellMap;
  }

  /**
   * method for checking if a given point lies within in the boundaries of the grid
   *
   * @param position a Coordinate whose
   * @return boolean whether the coordinate can exist within the grid dimensions
   */
  public boolean isInBounds(Coordinate position) {
    return position.getRow() >= 0 && position.getRow() < numberOfRows &&
        position.getColumn() >= 0 && position.getColumn() < numberOfColumns;
  }


  protected void makeSwap(Coordinate c, Coordinate newHome) {
    if (newHome != null) {
      Cell current = cellMap.get(c);
      Cell swap = cellMap.get(newHome);
      current.setPosition(newHome);
      swap.setPosition(c);
      cellMap.put(newHome, current);
      cellMap.put(c, swap);
    }
  }

  public int getNumRows() {
    return numberOfRows;
  }

  public int getNumCols() {
    return numberOfColumns;
  }
}
