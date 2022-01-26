package Model;

public class GameOfLifeCell extends Cell {

  public GameOfLifeCell(Coordinate position, Enum initialState) {
    super(position, initialState);
  }

  protected void updateState() {
    currentState = futureState;
  }

  protected void determineNextState(Grid grid) {
    int alive = countLivingNeighbors(grid);
    if (alive == 2) futureState = currentState;
    else if (alive < 2 || alive > 3) futureState = States.GameOfLife.DEAD;
    else futureState = States.GameOfLife.ALIVE;
  }

  private int countLivingNeighbors(Grid grid) {
    int[] rowDelta = {-1, 1, 0, 0, 1, 1, -1, -1};
    int[] colDelta = {0, 0, -1, 1, -1, 1, -1, 1};
    int count = 0;
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.GameOfLife.ALIVE) {
          count++;
        }
      }
    }
    return count;
  }
}
