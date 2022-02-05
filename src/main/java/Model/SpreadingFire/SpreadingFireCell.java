package Model.SpreadingFire;


import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.List;

/**
 * Class that updates the state of a cell in a Spreading Fire model given the probability it burns
 * provided its neighbor is on fire
 *
 * @author Matthew Giglio
 */
public class SpreadingFireCell extends Cell {

  private double probCatch;
  /**
   * @param position     Coordinate position of the cell in the grid
   * @param initialState initial state of the cell
   * @param probCatch    probability cell catches on fire if its neighbor is burning
   */
  public SpreadingFireCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, double probCatch, int numberOfRows, int numberOfColumns,
      List<Integer> neighborConfig) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
    this.probCatch = probCatch;
  }

  protected void updateState() {
    setCurrentState(getFutureState());
  }

  protected void determineNextState(Grid grid) {
    if (getCurrentState() == States.SpreadingFire.EMPTY ||
        getCurrentState() == States.SpreadingFire.BURNING) {
      setFutureState(States.SpreadingFire.EMPTY);
      return;
    }
    if (canCatchFire(grid) && Math.random() <= probCatch) {
      setFutureState(States.SpreadingFire.BURNING);
    } else {
      setFutureState(States.SpreadingFire.TREE);
    }
  }

  private boolean canCatchFire(Grid grid) {
    int[] rowDelta = Neighbors.getRowDelta(getDirection());
    int[] colDelta = Neighbors.getColDelta(getDirection());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
          States.SpreadingFire.BURNING) {
          return true;
        }
      }
    return false;
  }
}
