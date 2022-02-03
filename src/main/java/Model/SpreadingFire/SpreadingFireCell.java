package Model.SpreadingFire;


import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;

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
      Direction direction, double probCatch, int numberOfRows, int numberOfColumns) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
    this.probCatch = probCatch;
  }

  protected void updateState() {
    currentState = futureState;
  }

  protected void determineNextState(Grid grid) {
    if (currentState == States.SpreadingFire.EMPTY ||
        currentState == States.SpreadingFire.BURNING) {
      futureState = States.SpreadingFire.EMPTY;
      return;
    }
    if (canCatchFire(grid) && Math.random() <= probCatch) {
      futureState = States.SpreadingFire.BURNING;
    } else {
      futureState = States.SpreadingFire.TREE;
    }
  }

  private boolean canCatchFire(Grid grid) {
    int[] rowDelta = Neighbors.getRowDelta(direction);
    int[] colDelta = Neighbors.getColDelta(direction);
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i], edgeType,
          numberOfRows, numberOfColumns);
      if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
          States.SpreadingFire.BURNING) {
          return true;
        }
      }
    return false;
  }
}
