package Model;

public class PercolationCell extends Cell {

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
    }
    else {
      futureState = States.Percolation.OPEN;
    }
  }

  private boolean canPercolate(Grid grid) {
    int[] rowDelta = {-1, 1, 0, 0, 1, 1, -1, -1};
    int[] colDelta = {0, 0, -1, 1, -1, 1, -1, 1};
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
