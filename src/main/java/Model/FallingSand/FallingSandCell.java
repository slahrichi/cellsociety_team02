package Model.FallingSand;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors.Direction;
import Model.States;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FallingSandCell extends Cell {

  private FallingSandGrid grid;
  private boolean canDrop;
  private List<Coordinate> emptySpots;
  private Random random;

  private static int BOTTOM_NEIGHBOR_ROW = 1;
  private static int BOTTOM_NEIGHBOR_COL = 0;
  private static int[] ROW_NEIGHBORS = {0, -1, 0};
  private static int[] COL_NEIGHBORS = {-1, 0, 1};

  /**
   * @param position        position of the cell in the grid
   * @param initialState    initial state for the cell
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   * @param grid            Grid object in which the cell exists
   */


  public FallingSandCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns, List<Integer> neighborConfig,
      Grid grid) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
    this.grid = (FallingSandGrid) grid;
    canDrop = false;
    emptySpots = new ArrayList<>();
    random = new Random();
  }


  protected void updateState() {
    Coordinate neighbor = null;
    if (canDrop) {
      neighbor = getPosition().checkNeighbors(BOTTOM_NEIGHBOR_ROW,
          BOTTOM_NEIGHBOR_COL, getEdgeType(), getNumberOfRows(), getNumberOfColumns());
    } else if (!emptySpots.isEmpty()) {
      neighbor = emptySpots.remove(random.nextInt(emptySpots.size()));
    }
    grid.handleSwap(getPosition(), neighbor);


  }

  protected void determineNextState(Grid grid) {
    resetStates();
    Enum state = getCurrentState();
    if (state == States.FallingSand.EMPTY || state == States.FallingSand.METAL) {
      return;
    } else if (state == States.FallingSand.SAND) {
      checkCanDrop(grid);
    } else if (state == States.FallingSand.WATER) {
      checkEmptyNeighbors(grid);
    }
  }

  private void resetStates() {
    canDrop = false;
    emptySpots.clear();
  }

  private void checkEmptyNeighbors(Grid grid) {
    for (int i = 0; i < ROW_NEIGHBORS.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(ROW_NEIGHBORS[i], COL_NEIGHBORS[i],
          getEdgeType(), getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
          States.FallingSand.EMPTY) {
        emptySpots.add(neighbor);
      }
    }
  }

  private void checkCanDrop(Grid grid) {
    Coordinate neighbor = getPosition().checkNeighbors(BOTTOM_NEIGHBOR_ROW,
        BOTTOM_NEIGHBOR_COL, getEdgeType(), getNumberOfRows(), getNumberOfColumns());
    if (grid.isInBounds(neighbor) && getNeighborState(neighbor, grid) ==
        States.FallingSand.EMPTY) {
      canDrop = true;
    }
  }
}
