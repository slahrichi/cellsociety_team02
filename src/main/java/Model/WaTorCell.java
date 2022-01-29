package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaTorCell extends Cell {
  private WaTorGrid grid;
  private int fishChronon;
  private int sharkChronon;
  private List<Coordinate> empty;
  private List<Coordinate> fish;
  private int turnsElapsed;
  private Random random;
  private static int DEATH;
  public WaTorCell(Coordinate c, Enum state, Grid grid, int fishChronon, int sharkChronon) {
    super(c, state);
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
    if (currentState == States.WaTor.FISH) {
      updateFish();
    } else if (currentState == States.WaTor.SHARK) {
      updateShark();
    }
  }

  private void updateShark() {
    if (!fish.isEmpty()) {
      eatFish();
    }
    if (hasDied()) {
      currentState = States.WaTor.EMPTY;
      clearTurnsElapsed();
    }
    else if (canReproduce(States.WaTor.SHARK)) {
      reproduce(States.WaTor.SHARK);
    }
    else {
      moveToNeighbor();
      turnsElapsed++;
    }
  }

  private void clearTurnsElapsed() {turnsElapsed = 0;}

  private void updateFish() {
    if (canReproduce(States.WaTor.FISH)) {
      reproduce(States.WaTor.FISH);
    }
    else {
      moveToNeighbor();
      turnsElapsed++;
    }
  }

  private void reproduce(States.WaTor state) {
    Coordinate offspring = empty.remove(random.nextInt(empty.size()));
    updateNeighborState(offspring, state);
    clearTurnsElapsed();
  }

  private boolean canReproduce(States.WaTor state) {
    switch (state) {
      case FISH: return turnsElapsed >= fishChronon && !empty.isEmpty();
      case SHARK: return turnsElapsed >= sharkChronon && !empty.isEmpty();
    }
    return false;
  }

  private void eatFish() {
    if (!fish.isEmpty()) {
      Coordinate fishToBeEaten = fish.remove(random.nextInt(fish.size()));
      updateNeighborState(fishToBeEaten, States.WaTor.EMPTY);
      clearTurnsElapsed();
    }
  }

  private void updateNeighborState(Coordinate c, Enum state) {
    WaTorCell newNeighbor = new WaTorCell(c, state, grid, fishChronon, sharkChronon);
    grid.getCellMap().put(c, newNeighbor);
    newNeighbor.updateNewNeighbors();
  }

  private void moveToNeighbor() {
    if (!empty.isEmpty()) {
      Coordinate emptySpot = empty.remove(random.nextInt(empty.size()));
      grid.makeSwap(position, emptySpot);
    }
  }

  private boolean hasDied() {
    return currentState == States.WaTor.SHARK && turnsElapsed == DEATH;
  }


  protected void determineNextState(Grid grid) {
    determineNeighbors(grid);
  }

  private void determineNeighbors(Grid grid) {
    clearLists();
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.WaTor.EMPTY) {
          empty.add(position);
        }
        else if (grid.getCellMap().get(neighbor).getCurrentState() == States.WaTor.FISH) {
          fish.add(position);
        }
      }
    }
  }

  private void updateNewNeighbors() {
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        WaTorCell cell = (WaTorCell) grid.getCellMap().get(position);
        cell.determineNeighbors(grid);
      }
    }
  }

  private void clearLists() {
    empty.clear();
    fish.clear();
  }
}
