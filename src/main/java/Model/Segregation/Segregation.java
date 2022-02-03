package Model.Segregation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
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
      EdgeType edgeType, Direction direction, double threshold) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
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
      grid.getCellMap().put(c, new SegregationCell(c, state, grid, edgeType, direction, threshold,
          numberOfRows, numberOfColumns));
    }

  }

  protected void updateData() {
    int empty = 0, reps = 0, dems = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.Segregation.EMPTY) {
        empty++;
      }
      else if (state == States.Segregation.REP) {
        reps++;
      }
      else if (state == States.Segregation.DEM) {
        dems++;
      }
    }
    data.put(States.Segregation.EMPTY, empty);
    data.put(States.Segregation.REP, reps);
    data.put(States.Segregation.DEM, dems);
  }
}
