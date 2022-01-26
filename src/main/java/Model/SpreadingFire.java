package Model;

import java.util.Map;

public class SpreadingFire extends Simulation {
  private double probCatch;

  public SpreadingFire(int numberOfRows, int numberOfColumns, double probCatch) {
    super(numberOfRows, numberOfColumns);
    this.probCatch = probCatch;
    initializeGridCells(null);

  }

  protected void createGrid()  {
    grid = new SpreadingFireGrid(numberOfColumns, numberOfRows);
  }

  protected void initializeGridCells(Map<Coordinate, Integer> map) {
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

  public void update() {
    determineNewCellStates();
    updateCellStates();
  }

  private void determineNewCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.determineNextState(grid);
    }
  }

  private void updateCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.updateState();
    }
  }

}
