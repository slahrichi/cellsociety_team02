package Model.RockPaperScissors;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.List;
import java.util.Random;

/**
 * Class for updating the state of a cell and updating the states of its neighbors given the
 * modeling algorithm for the rock-paper-scissors cellular automata
 *
 * @author Matthew Giglio
 */
public class RockPaperScissorsCell extends Cell {

  private int threshold;
  private Random random;
  private static int RANDOM_FACTOR = 3;

  /**
   * @param position        position of the cell in the grid
   * @param initialState    initial state for the cell
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   * @param threshold
   */
  public RockPaperScissorsCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction,
      int numberOfRows, int numberOfColumns, List<Integer> neighborConfig, int threshold) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
    this.threshold = threshold;
    this.random = new Random();
  }


  protected void updateState() {
    setCurrentState(getFutureState());
  }

  protected void determineNextState(Grid grid) {
    int count = countWinningNeighbors(grid);
    int randomFactor = random.nextInt(RANDOM_FACTOR);
    count += randomFactor;
    if (count > threshold) {
      setFutureState(States.RockPaperScissors.getWinningNeighbor(getCurrentState()));
    } else {
      setFutureState(getCurrentState());
    }
  }

  private int countWinningNeighbors(Grid grid) {
    int count = 0;
    int[] rowDelta = Neighbors.getRowDelta(getDirection(), getNeighborConfig());
    int[] colDelta = Neighbors.getColDelta(getDirection(), getNeighborConfig());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
          States.RockPaperScissors.getWinningNeighbor(getCurrentState())) {
        count++;
      }
    }
    return count;
  }
}
