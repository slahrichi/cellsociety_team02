package Model;


import Model.Edge.EdgeType;

/**
 * Class that updates the state of a cell in a Spreading Fire model given the probability it burns
 * provided its neighbor is on fire
 *
 * @author Matthew Giglio
 */
public class SpreadingFireCell extends Cell {

  private double probCatch;
  private EdgeType edgeType;
  /**
   * @param position     Coordinate position of the cell in the grid
   * @param initialState initial state of the cell
   * @param probCatch    probability cell catches on fire if its neighbor is burning
   */
  public SpreadingFireCell(Coordinate position, Enum initialState, EdgeType edgeType,
      double probCatch) {
    super(position, initialState);
    this.probCatch = probCatch;
    this.edgeType = edgeType;

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
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.SpreadingFire.BURNING) {
          return true;
        }
      }
    }
    return false;
  }
}
