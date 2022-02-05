package Model;

import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import java.util.List;

/**
 * Abstract class for representing the basic attributes of a cell in a cellular automata model.
 * Methods are mostly implemented as protected methods in order to limit their usage to extended
 * usages of the class
 *
 * @author Matthew Giglio
 */
public abstract class Cell {

  private Coordinate position;
  private Enum currentState;
  private Enum futureState;
  private EdgeType edgeType;
  private int numberOfRows;
  private int numberOfColumns;
  private Direction direction;
  private List<Integer> neighborConfig;

  /**
   * @param position        Coordinate position of the cell in the grid
   * @param initialState    initial state of the cell
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   */
  public Cell(Coordinate position, Enum initialState, EdgeType edgeType, Direction direction,
      int numberOfRows, int numberOfColumns, List<Integer> neighborConfig) {
    this.position = position;
    this.currentState = initialState;
    this.edgeType = edgeType;
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.direction = direction;
    this.neighborConfig = neighborConfig;
  }

  protected Coordinate getPosition() {
    return position;
  }

  protected EdgeType getEdgeType() {
    return edgeType;
  }

  protected Direction getDirection() {
    return direction;
  }

  protected int getNumberOfRows() {
    return numberOfRows;
  }

  protected int getNumberOfColumns() {
    return numberOfColumns;
  }

  protected void setFutureState(Enum state) {
    futureState = state;
  }

  protected Enum getFutureState() {
    return futureState;
  }

  protected void setCurrentState(Enum state) {
    currentState = state;
  }

  protected List<Integer> getNeighborConfig() {
    return neighborConfig;
  }

  protected void setPosition(Coordinate c) {
    position = c;
  }

  protected Enum getCurrentState() {
    return currentState;
  }

  protected abstract void updateState();

  protected abstract void determineNextState(Grid grid);

  protected Enum getNeighborState(Coordinate c, Grid grid) {
    return grid.getCellMap().get(c).getCurrentState();
  }

  /**
   * overridden method to return a Cell object as a String
   *
   * @return the state of the cell as a String
   */
  @Override
  public String toString() {
    return currentState.toString();
  }
}
