package Model.FallingSand;

import Model.Coordinate;
import Model.Grid;

public class FallingSandGrid extends Grid {

  public FallingSandGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
  }

  protected void handleSwap(Coordinate c, Coordinate newHome) {
    makeSwap(c, newHome);
  }
}
