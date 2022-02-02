package Model;

import Model.Edge.EdgeType;
import java.util.Map;

/**
 * Extension of `Simulation` superclass that initializes and manages the models resources for the
 * Segregation model.
 *
 * @author Matthew Giglio
 */
public class Segregation extends Simulation {

  private double threshold;

  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   * @param setup           map to initialize the states of the grid's cells
   * @param threshold       minimum satisfaction threshold for constituents given their neighbors
   */
  public Segregation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, double threshold) {
    super(numberOfRows, numberOfColumns, setup, edgeType);
    this.threshold = threshold;
    initializeGridCells();
  }


  protected void createGrid() {
    grid = new SegregationGrid(numberOfRows, numberOfColumns);
  }


  protected void initializeGridCells() {
    if (setup == null) {
      return;
    }
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> {
          state = States.Segregation.EMPTY;
          ((SegregationGrid) grid).setEmptySpots(c);
        }
        case 1 -> state = States.Segregation.REP;
        case 2 -> state = States.Segregation.DEM;
      }
      grid.getCellMap().put(c, new SegregationCell(c, state, grid, edgeType, threshold));
    }

  }

}
