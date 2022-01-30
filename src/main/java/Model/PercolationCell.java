package Model;

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
   */
  public PercolationCell(Coordinate position, Enum initialState) {
    super(position, initialState);
  }

  protected void updateState() {
    currentState = futureState;
  }


  protected void determineNextState(Grid grid) {
    if (currentState == States.Percolation.BLOCKED ||
        currentState == States.Percolation.PERCOLATED) {
      futureState = currentState;
      return;
    }
    if (canPercolate(grid)) {
      futureState = States.Percolation.PERCOLATED;
    } else {
      futureState = States.Percolation.OPEN;
    }
  }

  private boolean canPercolate(Grid grid) {
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.Percolation.PERCOLATED) {
          return true;
        }
      }
    }
    return false;
  }
}
