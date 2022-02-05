package Model.WaTor;

import Model.Cell;
import Model.Coordinate;
import Model.Grid;

/**
 * Extension of `Grid` superclass for implementing Wa-Tor simulation. Has the ability to swap cells
 * in the grid in order to properly update the model
 *
 * @author Matthew Giglio
 */

public class WaTorGrid extends Grid {

  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   */
  public WaTorGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
  }


  protected void handleSwap(Coordinate c, Coordinate newHome) {
    makeSwap(c, newHome);
  }
}
