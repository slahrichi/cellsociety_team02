package Model.SpreadingFire;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.Map;

/**
 * Extension of `Simulation` class that manages the model resources for the Spreading Fire
 * simulation and facilitates the initialization and updating of the simulation.
 *
 * @author Matthew Giglio
 */
public class SpreadingFire extends Simulation {

  private double probCatch;

  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   * @param setup           map for setting up the initial states of the cells in the grid
   * @param probCatch       probability that a tree catches on fire if its neighbor is burning
   */
  public SpreadingFire(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, double probCatch) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
    this.probCatch = probCatch;
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
      grid.getCellMap().put(c, new SpreadingFireCell(c, state, edgeType, direction, probCatch,
          numberOfRows, numberOfColumns));
    }
  }


  protected void updateData() {
    int trees = 0, burning = 0, empty = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.SpreadingFire.TREE) {
        trees++;
      }
      else if (state == States.SpreadingFire.BURNING) {
        burning++;
      }
      else if (state == States.SpreadingFire.EMPTY) {
        empty++;
      }
    }
    data.put(States.SpreadingFire.TREE, trees);
    data.put(States.SpreadingFire.BURNING, burning);
    data.put(States.SpreadingFire.EMPTY, empty);
  }
}
