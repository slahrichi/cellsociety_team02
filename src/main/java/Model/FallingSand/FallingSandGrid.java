package Model.FallingSand;

import Model.Coordinate;
import Model.Grid;

/**
 * class that extends the `Grid` superclass for modeling Falling Sand
 *
 * @author Matthew Giglio
 */
public class FallingSandGrid extends Grid {

  /**
   * @param numberOfRows    number of rows in simulation grid
   * @param numberOfColumns number of columns in simulation grid
   */
  public FallingSandGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
  }

  protected void handleSwap(Coordinate c, Coordinate newHome) {
    makeSwap(c, newHome);
  }
}
