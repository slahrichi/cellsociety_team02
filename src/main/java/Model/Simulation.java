package Model;

import java.util.Map;

public abstract class Simulation {
  protected Grid grid;
  protected int numberOfColumns;
  protected int numberOfRows;

  public Simulation(int numberOfRows, int numberOfColumns) {
    this.numberOfColumns = numberOfColumns;
    this.numberOfRows = numberOfRows;
    createGrid();
    initializeGridCells();
  }

  public Grid getGrid() {return grid;}

  protected abstract void createGrid();

  protected abstract void initializeGridCells();

  public void update() {
    determineNewCellStates();
    updateCellStates();
  }

  protected void determineNewCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.determineNextState(grid);
    }
  }

  protected void updateCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.updateState();
    }
  }

}
