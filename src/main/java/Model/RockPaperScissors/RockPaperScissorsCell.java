package Model.RockPaperScissors;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.Random;

public class RockPaperScissorsCell extends Cell {

  private int threshold;
  private Random random;
  private static int RANDOM_FACTOR = 3;
  public RockPaperScissorsCell(Coordinate position, Enum initialState, EdgeType edgeType, Direction direction,
      int numberOfRows, int numberOfColumns, int threshold) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
    this.threshold = threshold;
    this.random = new Random();
  }


  protected void updateState() {currentState = futureState;}

  protected void determineNextState(Grid grid) {
    int count = countWinningNeighbors(grid);
    int randomFactor = random.nextInt(RANDOM_FACTOR);
    count += randomFactor;
    if (count > threshold) {
      futureState = States.RockPaperScissors.getWinningNeighbor(currentState);
    }
    else {
      futureState = currentState;
    }
  }

  private int countWinningNeighbors(Grid grid) {
    int count = 0;
    int[] rowDelta = Neighbors.getRowDelta(direction);
    int[] colDelta = Neighbors.getColDelta(direction);
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i], edgeType,
          numberOfRows, numberOfColumns);
      if (grid.isInBounds(neighbor)) {
        Enum neighborState = grid.getCellMap().get(neighbor).getCurrentState();
        if (neighborState == States.RockPaperScissors.getWinningNeighbor(currentState)) {
          count++;
        }
      }
    }
    return count;
  }
}
