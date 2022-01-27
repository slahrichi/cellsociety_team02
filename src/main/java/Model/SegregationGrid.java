package Model;

import java.util.ArrayList;
import java.util.List;

public class SegregationGrid extends Grid {
  private List<Coordinate> emptySpots;
  public SegregationGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
    emptySpots = new ArrayList<>();
  }

  protected void setEmptySpots(Coordinate c) {
    emptySpots.add(c);
  }

  protected void moveCell(Coordinate c) {
    if (emptySpots.size() != 0) {
      Coordinate newHome = emptySpots.remove(0);
      makeSwap(c, newHome);
      setEmptySpots(c);
    }
  }

  private void makeSwap(Coordinate c, Coordinate newHome) {
    Cell current = cellMap.get(c);
    Cell swap = cellMap.get(newHome);
    current.setPosition(newHome);
    swap.setPosition(c);
    cellMap.put(newHome, current);
    cellMap.put(c, swap);
  }
}
