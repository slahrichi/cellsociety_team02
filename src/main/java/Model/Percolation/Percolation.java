package Model.Percolation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.Map;

/**
 * Class that leverages the `Simulation` superclass in order to devise an implementation of
 * Percolation.
 *
 * @author Matthew Giglio
 */

public class Percolation extends Simulation {


  /**
   * object for modeling a simulation of Percolation
   *
   * @param numberOfRows    number of rows in the model's grid
   * @param numberOfColumns number of columns in the model's grid
   * @param setup           map storing integer representations of states in order to initialize
   *                        simulation states
   */
  public Percolation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
  }


  protected void createGrid() {
    grid = new PercolationGrid(numberOfRows, numberOfColumns);
  }


  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.Percolation.OPEN;
        case 1 -> state = States.Percolation.PERCOLATED;
        case 2 -> state = States.Percolation.BLOCKED;
      }
      grid.getCellMap().put(c, new PercolationCell(c, state, edgeType, direction, numberOfRows,
          numberOfColumns));
    }
  }

  protected void updateData() {
    int open = 0, percolated = 0, blocked = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.Percolation.OPEN) {
        open++;
      }
      else if (state == States.Percolation.PERCOLATED) {
        percolated++;
      }
      else if (state == States.Percolation.BLOCKED) {
        blocked++;
      }
    }
    data.put(States.Percolation.OPEN, open);
    data.put(States.Percolation.PERCOLATED, percolated);
    data.put(States.Percolation.BLOCKED, blocked);
  }
}
