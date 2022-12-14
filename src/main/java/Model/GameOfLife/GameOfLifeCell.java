package Model.GameOfLife;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.List;

/**
 * class that extends the `Cell` superclass to implement specific usage for the purpose of the Game
 * of Life model. The methods for determining the states of the cell are specifically derived from
 * the specified rules of the Game of Life.
 *
 * @author Matthew Giglio
 */
public class GameOfLifeCell extends Cell {

  private static final int STABLE = 2;
  private static final int BIRTH = 3;

  /**
   * @param position        position of the cell in the grid
   * @param initialState    initial state for the cell
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   */
  public GameOfLifeCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns, List<Integer> neighborConfig) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
  }

  protected void updateState() {
    setCurrentState(getFutureState());
  }

  protected void determineNextState(Grid grid) {
    int alive = countLivingNeighbors(grid);
    if (alive == STABLE) {
      setFutureState(getCurrentState());
    } else if (alive < STABLE || alive > BIRTH) {
      setFutureState(States.GameOfLife.DEAD);
    } else if (alive == BIRTH) {
      setFutureState(States.GameOfLife.ALIVE);
    }
  }

  private int countLivingNeighbors(Grid grid) {
    int count = 0;
    int[] rowDelta = Neighbors.getRowDelta(getDirection(), getNeighborConfig());
    int[] colDelta = Neighbors.getColDelta(getDirection(), getNeighborConfig());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor)
          && getNeighborState(neighbor, grid) == States.GameOfLife.ALIVE) {
        count++;
      }
    }
    return count;
  }
}
