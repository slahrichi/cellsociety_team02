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
    grid = new SegregationGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state = null;
      switch (getSetup().get(c)) {
        case 0 -> {
          state = States.Segregation.EMPTY;
          ((SegregationGrid) grid).setEmptySpots(c);
        }
        case 1 -> state = States.Segregation.REP;
        case 2 -> state = States.Segregation.DEM;
      }
      grid.getCellMap().put(c, new SegregationCell(c, state, grid, getEdgeType(), getDirection(),
          threshold, getNumberOfRows(), getNumberOfColumns()));
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
    getData().put(States.Segregation.EMPTY, empty);
    getData().put(States.Segregation.REP, reps);
    getData().put(States.Segregation.DEM, dems);
  }
}
