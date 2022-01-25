package Model;



public class SpreadingFireCell extends Cell{
  private double probCatch;
  protected SpreadingFireCell(Coordinate position, Enum initialState, double probCatch) {
    super(position, initialState);
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
    }
    else {
      futureState = States.SpreadingFire.TREE;
    }
  }

  private boolean canCatchFire(Grid grid) {
    int[] rowDelta = {-1, 1, 0, 0};
    int[] colDelta = {0, 0, -1, 1};
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
