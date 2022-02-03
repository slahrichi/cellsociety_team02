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

  private Grid grid;
  private boolean canDrop;
  private List<Coordinate> emptySpots;
  private Random random;

  private static int BOTTOM_NEIGHBOR_ROW = 1;
  private static int BOTTOM_NEIGHBOR_COL = 0;
  private static int[] ROW_NEIGHBORS = {0, -1, 0};
  private static int[] COL_NEIGHBORS = {-1, 0, 1};


  public FallingSandCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns, Grid grid) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
    this.grid = grid;
    canDrop = false;
    emptySpots = new ArrayList<>();
    random = new Random();
  }


  protected void updateState() {
    Coordinate neighbor = null;
    if (canDrop) {
      neighbor = position.checkNeighbors(BOTTOM_NEIGHBOR_ROW,
          BOTTOM_NEIGHBOR_COL, edgeType, numberOfRows, numberOfColumns);
    }
    else if (!emptySpots.isEmpty()) {
      neighbor = emptySpots.remove(random.nextInt(emptySpots.size()));
    }
    grid.makeSwap(position, neighbor);


  }

  protected void determineNextState(Grid grid) {
    resetStates();
    if (currentState == States.FallingSand.EMPTY || currentState == States.FallingSand.METAL) {
      return;
    }
    else if (currentState == States.FallingSand.SAND) {
      checkCanDrop(grid);
    }
    else if (currentState == States.FallingSand.WATER) {
      checkEmptyNeighbors(grid);
    }
  }

  private void resetStates() {
    canDrop = false;
    emptySpots.clear();
  }

  private void checkEmptyNeighbors(Grid grid) {
    for (int i = 0; i < ROW_NEIGHBORS.length; i++) {
      Coordinate neighbor = position.checkNeighbors(ROW_NEIGHBORS[i], COL_NEIGHBORS[i], edgeType,
          numberOfRows, numberOfColumns);
      if (grid.isInBounds(neighbor) && grid.getCellMap().get(neighbor).getCurrentState() ==
      States.FallingSand.EMPTY) {
        emptySpots.add(neighbor);
      }
    }
  }

  private void checkCanDrop(Grid grid) {
    Coordinate downstairsNeighbor = position.checkNeighbors(BOTTOM_NEIGHBOR_ROW,
        BOTTOM_NEIGHBOR_COL, edgeType, numberOfRows, numberOfColumns);
    if (grid.isInBounds(downstairsNeighbor) && grid.getCellMap().get(downstairsNeighbor)
        .getCurrentState() == States.FallingSand.EMPTY) {
      canDrop = true;
    }
  }
}
