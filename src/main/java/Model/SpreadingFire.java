package Model;

import Model.Edge.EdgeType;
import java.util.Map;

/**
 * Extension of `Simulation` class that manages the model resources for the Spreading Fire
 * simulation and facilitates the initialization and updating of the simulation.
 *
 * @author Matthew Giglio
 */
public class SpreadingFire extends Simulation {

  private double probCatch;
  private EdgeType edgeType;
  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   * @param setup           map for setting up the initial states of the cells in the grid
   * @param probCatch       probability that a tree catches on fire if its neighbor is burning
   */
  public SpreadingFire(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, double probCatch) {
    super(numberOfRows, numberOfColumns, setup);
    this.probCatch = probCatch;
    this.edgeType = edgeType;
    initializeGridCells();
  }

  protected void createGrid() {
    grid = new SpreadingFireGrid(numberOfRows, numberOfColumns);
  }

  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.SpreadingFire.TREE;
        case 1 -> state = States.SpreadingFire.BURNING;
        case 2 -> state = States.SpreadingFire.EMPTY;
      }
      grid.getCellMap().put(c, new SpreadingFireCell(c, state, edgeType, probCatch));
    }
  }
}
