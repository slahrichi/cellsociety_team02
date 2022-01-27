package Model;

import java.util.Map;

public class SpreadingFire extends Simulation {
  private double probCatch;

  public SpreadingFire(int numberOfRows, int numberOfColumns, double probCatch) {
    super(numberOfRows, numberOfColumns);
    this.probCatch = probCatch;
    initializeGridCells();

  }

  protected void createGrid()  {
    grid = new SpreadingFireGrid(numberOfColumns, numberOfRows);
  }

  protected void initializeGridCells() {
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        Coordinate coord = new Coordinate(i, j);
        if (i == numberOfRows/2 && j == numberOfColumns/2) {
          grid.getCellMap().put(coord, new SpreadingFireCell(coord, States.SpreadingFire.BURNING,
              probCatch));
        }
        else {
          grid.getCellMap().put(coord, new SpreadingFireCell(coord, States.SpreadingFire.TREE,
              probCatch));
        }
      }
    }
  }
}
