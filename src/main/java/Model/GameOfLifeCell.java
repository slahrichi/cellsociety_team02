package Model;

import Model.Edge.EdgeType;

/**
 * class that extends the `Cell` superclass to implement specific usage for the purpose of the Game
 * of Life model. The methods for determining the states of the cell are specifically derived from
 * the specified rules of the Game of Life.
 *
 * @author Matthew Giglio
 */
public class GameOfLifeCell extends Cell {

  public GameOfLifeCell(Coordinate position, Enum initialState, EdgeType edgeType) {
    super(position, initialState, edgeType);
  }

  protected void updateState() {
    currentState = futureState;
  }

  protected void determineNextState(Grid grid) {
    int alive = countLivingNeighbors(grid);
    if (alive == 2) {
      futureState = currentState;
    } else if (alive < 2 || alive > 3) {
      futureState = States.GameOfLife.DEAD;
    } else {
      futureState = States.GameOfLife.ALIVE;
    }
  }

  private int countLivingNeighbors(Grid grid) {
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
