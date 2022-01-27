package Model;

import java.util.ArrayList;
import java.util.List;

public class WaTorGrid extends Grid {
  private List<Coordinate> emptySpots;
  public WaTorGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
    emptySpots = new ArrayList<>();
  }

  public void setEmptySpots(Coordinate c) {emptySpots.add(c);}
}
