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
    initializeGridCells(null);
  }

  public Grid getGrid() {return grid;}

  protected abstract void createGrid();

  protected abstract void initializeGridCells(Map<Coordinate, Integer> setup);

  protected abstract void update();

}
