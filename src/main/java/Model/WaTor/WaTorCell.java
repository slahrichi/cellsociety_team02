package Model.WaTor;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for updating the state of a cell and updating the states of its neighbors given the
 * modeling algorithm for the Wa-Tor simulation
 *
 * @author Matthew Giglio
 */
public class WaTorCell extends Cell {

  private WaTorGrid grid;
  private int fishChronon;
  private int sharkChronon;
  private List<Coordinate> empty;
  private List<Coordinate> fish;
  private int turnsElapsed;
  private Random random;
  private static int DEATH;

  /**
   * @param c            position of the cell in the grid
   * @param state        initial state for the cell
   * @param grid         `Grid` object in which the cell exists
   * @param fishChronon  number of turns before fish can reproduce
   * @param sharkChronon number of turns before shark can reproduce
   */
  public WaTorCell(Coordinate c, Enum state, Grid grid, EdgeType edgeType, Direction direction,
      int fishChronon, int sharkChronon, int numberOfRows, int numberOfColumns,
      List<Integer> neighborConfig) {
    super(c, state, edgeType, direction, numberOfRows, numberOfColumns, neighborConfig);
    this.grid = (WaTorGrid) grid;
    this.fishChronon = fishChronon;
    this.sharkChronon = sharkChronon;
    empty = new ArrayList<>();
    fish = new ArrayList<>();
    random = new Random();
    DEATH = sharkChronon + 3;
  }

  @Override
  protected void updateState() {
    if (getCurrentState() == States.WaTor.FISH) {
      updateFish();
    } else if (getCurrentState() == States.WaTor.SHARK) {
      updateShark();
    }
  }

  private void updateShark() {
    if (!fish.isEmpty()) {
      eatFish();
    }
    if (hasDied()) {
      setCurrentState(States.WaTor.EMPTY);
      clearTurnsElapsed();
    } else if (canReproduce(States.WaTor.SHARK)) {
      reproduce(States.WaTor.SHARK);
    } else {
      moveToNeighbor();
      turnsElapsed++;
    }
  }

  private void updateFish() {
    if (canReproduce(States.WaTor.FISH)) {
      reproduce(States.WaTor.FISH);
    } else {
      moveToNeighbor();
      turnsElapsed++;
    }
  }

  private void clearTurnsElapsed() {
    turnsElapsed = 0;
  }

  //let's treat reproduction like making one of the neighbors an offspring as opposed to
  //directly leaving something behind
  //let's also make health and turns elapsed equivalent
  private void reproduce(States.WaTor state) {
    Coordinate offspring = empty.remove(random.nextInt(empty.size()));
    updateNeighborState(offspring, state);
    clearTurnsElapsed();
  }

  private boolean canReproduce(States.WaTor state) {
    switch (state) {
      case FISH:
        return turnsElapsed >= fishChronon && !empty.isEmpty();
      case SHARK:
        return turnsElapsed >= sharkChronon && !empty.isEmpty();
    }
    return false;
  }

  private void eatFish() {
    Coordinate fishToBeEaten = fish.remove(random.nextInt(fish.size()));
    updateNeighborState(fishToBeEaten, States.WaTor.EMPTY);
    clearTurnsElapsed();
  }

  private void updateNeighborState(Coordinate c, Enum state) {
    WaTorCell newNeighbor = new WaTorCell(c, state, grid, getEdgeType(), getDirection(),
        fishChronon, sharkChronon, getNumberOfRows(), getNumberOfColumns(), getNeighborConfig());
    grid.getCellMap().put(c, newNeighbor);
    newNeighbor.updateNewNeighbors();
  }

  private void moveToNeighbor() {
    if (!empty.isEmpty()) {
      Coordinate emptySpot = empty.remove(random.nextInt(empty.size()));
      grid.makeSwap(getPosition(), emptySpot);
    }
  }

  private boolean hasDied() {
    return getCurrentState() == States.WaTor.SHARK && turnsElapsed == DEATH;
  }


  protected void determineNextState(Grid grid) {
    determineNeighbors(grid);
  }

  private void determineNeighbors(Grid grid) {
    clearLists();
    int[] rowDelta = Neighbors.getRowDelta(getDirection());
    int[] colDelta = Neighbors.getColDelta(getDirection());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor)) {
        Enum state = getNeighborState(neighbor, grid);
        if (state == States.WaTor.EMPTY) {
          empty.add(neighbor);
        } else if (state == States.WaTor.FISH) {
          fish.add(neighbor);
        }
      }
    }
  }

  private void updateNewNeighbors() {
    int[] rowDelta = Neighbors.getRowDelta(getDirection());
    int[] colDelta = Neighbors.getColDelta(getDirection());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor)) {
        WaTorCell cell = (WaTorCell) grid.getCellMap().get(getPosition());
        cell.determineNeighbors(grid);
      }
    }
  }

  private void clearLists() {
    empty.clear();
    fish.clear();
  }
}
