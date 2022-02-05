package Model.Segregation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
import java.util.Map;

/**
 * Extension of `Simulation` superclass that initializes and manages the models resources for the
 * Segregation model.
 *
 * @author Matthew Giglio
 */
public class Segregation extends Simulation {

  private double threshold;

  private static final int EMPTY = 0;
  private static final int REP = 1;
  private static final int DEM = 2;
  private static final String INVALID = "Invalid state number";


  /**
   * @param numberOfRows    number of rows in simulation grid
   * @param numberOfColumns number of columns in simulation grid
   * @param setup           map to initialize the states of the grid's cells
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param neighborConfig  configuration of neighbors being considered
   * @param threshold       minimum satisfaction threshold for constituents given their neighbors
   */
  public Segregation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig, double threshold) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
    this.threshold = threshold;
    initializeGridCells();
  }


  protected void createGrid() {
    grid = new SegregationGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case EMPTY -> {
          state = States.Segregation.EMPTY;
          ((SegregationGrid) grid).setEmptySpots(c);
        }
        case REP -> state = States.Segregation.REP;
        case DEM -> state = States.Segregation.DEM;
        default -> throw new IllegalArgumentException(INVALID);

      }
      grid.getCellMap().put(c, new SegregationCell(c, state, grid, getEdgeType(), getDirection(),
          threshold, getNumberOfRows(), getNumberOfColumns(), getNeighborConfig()));
    }

  }

  protected void updateData() {
    int empty = 0, reps = 0, dems = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.Segregation.EMPTY) {
        empty++;
      } else if (state == States.Segregation.REP) {
        reps++;
      } else if (state == States.Segregation.DEM) {
        dems++;
      }
    }
    getData().put(States.Segregation.EMPTY, empty);
    getData().put(States.Segregation.REP, reps);
    getData().put(States.Segregation.DEM, dems);
  }
}
