package Model;

import Model.Edge.EdgeType;
import Model.Neighbors.Direction;

/**
 * Abstract class for representing the basic attributes of a cell in a cellular automata model.
 * Methods are mostly implemented as protected methods in order to limit their usage to extended
 * usages of the class
 *
 * @author Matthew Giglio
 */
public abstract class Cell {

  protected Coordinate position;
  protected Enum currentState;
  protected Enum futureState;
  protected EdgeType edgeType;
  protected int numberOfRows;
  protected int numberOfColumns;
  protected Direction direction;

  public Cell(Coordinate position, Enum initialState, EdgeType edgeType, Direction direction,
      int numberOfRows, int numberOfColumns) {
    this.position = position;
    this.currentState = initialState;
    this.edgeType = edgeType;
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.direction = direction;
  }

  protected Coordinate getPosition() {
    return position;
  }

  public void setPosition(Coordinate c) {
    position = c;
  }

  /**
   * Getter method for a cell's current state, particularly helpful for
   * @return the current state of the cell
   */
  public Enum getCurrentState() {
    return currentState;
  }

  protected abstract void updateState();

  protected abstract void determineNextState(Grid grid);

  protected Enum getNeighborState(Coordinate c, Grid grid) {
    return grid.getCellMap().get(c).getCurrentState();
  }

  @Override
  public String toString() {
    return currentState.toString();
  }
}
