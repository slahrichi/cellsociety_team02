package Model.Percolation;

import Model.Grid;

/**
 * Extension of the `Grid` superclass for the purpose of modeling Percolation.
 *
 * @author Matthew Giglio
 */
public class PercolationGrid extends Grid {

  /**
   *
   * @param numberOfRows number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   */
  public PercolationGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
  }

}
