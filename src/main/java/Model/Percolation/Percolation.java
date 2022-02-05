package Model.Percolation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
import java.util.Map;

/**
 * Class that leverages the `Simulation` superclass in order to devise an implementation of
 * Percolation.
 *
 * @author Matthew Giglio
 */

public class Percolation extends Simulation {

  private static final int OPEN = 0;
  private static final int PERCOLATED = 1;
  private static final int BLOCKED = 2;
  private static final String INVALID = "Invalid state number";


  /**
   * object for modeling a simulation of Percolation
   *
   * @param numberOfRows    number of rows in the model's grid
   * @param numberOfColumns number of columns in the model's grid
   * @param setup           map storing integer representations of states in order to initialize
   *                        simulation states
   * @param numberOfRows    number of rows in grid in which cell exists
   * @param numberOfColumns number of columns in grid in which cell exists
   * @param neighborConfig  configuration of neighbors being considered
   */
  public Percolation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
  }


  protected void createGrid() {
    grid = new PercolationGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case OPEN -> state = States.Percolation.OPEN;
        case PERCOLATED -> state = States.Percolation.PERCOLATED;
        case BLOCKED -> state = States.Percolation.BLOCKED;
        default -> throw new IllegalArgumentException(INVALID);
      }
      grid.getCellMap().put(c, new PercolationCell(c, state, getEdgeType(), getDirection(),
          getNumberOfRows(), getNumberOfColumns(), getNeighborConfig()));
    }
  }

  protected void updateData() {
    int open = 0, percolated = 0, blocked = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.Percolation.OPEN) {
        open++;
      } else if (state == States.Percolation.PERCOLATED) {
        percolated++;
      } else if (state == States.Percolation.BLOCKED) {
        blocked++;
      }
    }
    getData().put(States.Percolation.OPEN, open);
    getData().put(States.Percolation.PERCOLATED, percolated);
    getData().put(States.Percolation.BLOCKED, blocked);
  }
}
