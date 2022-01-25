package Model;

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

  protected abstract void update();

}
