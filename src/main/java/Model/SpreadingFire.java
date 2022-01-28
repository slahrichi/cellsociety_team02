package Model;

import java.util.Map;

public class SpreadingFire extends Simulation {
  private double probCatch;

  public SpreadingFire(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      double probCatch) {
    super(numberOfRows, numberOfColumns, setup);
    this.probCatch = probCatch;
  }

  protected void createGrid()  {
    grid = new SpreadingFireGrid(numberOfColumns, numberOfRows);
  }

  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.SpreadingFire.TREE;
        case 1 -> state = States.SpreadingFire.BURNING;
        case 2 -> state = States.SpreadingFire.EMPTY;
      }
      grid.getCellMap().put(c, new SpreadingFireCell(c, state, probCatch));
    }
  }
}
