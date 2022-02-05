package Model.SpreadingFire;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
import java.util.Map;

/**
 * Extension of `Simulation` class that manages the model resources for the Spreading Fire
 * simulation and facilitates the initialization and updating of the simulation.
 *
 * @author Matthew Giglio
 */
public class SpreadingFire extends Simulation {

  private double probCatch;

  private static final int TREE = 0;
  private static final int BURNING = 1;
  private static final int EMPTY = 2;
  private static final String INVALID = "Invalid state number";


  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   * @param setup           map for setting up the initial states of the cells in the grid
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param neighborConfig  configuration of neighbors being considered
   * @param probCatch       probability that a tree catches on fire if its neighbor is burning
   */
  public SpreadingFire(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig, double probCatch) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
    this.probCatch = probCatch;
    initializeGridCells();
  }

  protected void createGrid() {
    grid = new SpreadingFireGrid(getNumberOfRows(), getNumberOfColumns());
  }

  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case TREE -> state = States.SpreadingFire.TREE;
        case BURNING -> state = States.SpreadingFire.BURNING;
        case EMPTY -> state = States.SpreadingFire.EMPTY;
        default -> throw new IllegalArgumentException(INVALID);

      }
      grid.getCellMap().put(c, new SpreadingFireCell(c, state, getEdgeType(), getDirection(),
          probCatch, getNumberOfRows(), getNumberOfColumns(), getNeighborConfig()));
    }
  }


  protected void updateData() {
    int trees = 0, burning = 0, empty = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.SpreadingFire.TREE) {
        trees++;
      } else if (state == States.SpreadingFire.BURNING) {
        burning++;
      } else if (state == States.SpreadingFire.EMPTY) {
        empty++;
      }
    }
    getData().put(States.SpreadingFire.TREE, trees);
    getData().put(States.SpreadingFire.BURNING, burning);
    getData().put(States.SpreadingFire.EMPTY, empty);
  }
}
