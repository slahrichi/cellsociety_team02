package Model.Percolation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.List;

/**
 * Extension of `Cell` superclass for modeling Percolation. Properly updates the states of the cells
 * given the algorithm of the model
 *
 * @author Matthew Giglio
 */
public class PercolationCell extends Cell {


  /**
   * @param position     `Coordinate` representing position of the cell in the grid
   * @param initialState initializing state of the cell
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   */
  public PercolationCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns, List<Integer> neighborConfig) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
  }

  protected void updateState() {
    setCurrentState(getFutureState());
  }


  protected void determineNextState(Grid grid) {
    if (getCurrentState() == States.Percolation.BLOCKED ||
        getCurrentState() == States.Percolation.PERCOLATED) {
      setFutureState(getCurrentState());
      return;
    }
    if (canPercolate(grid)) {
      setFutureState(States.Percolation.PERCOLATED);
    } else {
      setFutureState(States.Percolation.OPEN);
    }
  }

  private boolean canPercolate(Grid grid) {
    int[] rowDelta = Neighbors.getRowDelta(getDirection(), getNeighborConfig());
    int[] colDelta = Neighbors.getColDelta(getDirection(), getNeighborConfig());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
          States.Percolation.PERCOLATED) {
          return true;
        }
      }
    return false;
  }
}
