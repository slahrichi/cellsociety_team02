package Model;

public class SpreadingFire extends Simulation {
  private int probCatch;

  public SpreadingFire(int numberOfRows, int numberOfColumns, int probCatch) {
    super(numberOfRows, numberOfColumns);
    this.probCatch = probCatch;

  }

  public enum States {
    TREE,
    BURNING,
    BURNT
  }

  protected void createGrid()  {
    grid = new SpreadingFireGrid(numberOfColumns, numberOfRows);
  }

  protected void initializeGridCells() {
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        Coordinate coord = new Coordinate(i, j);
        if (i == numberOfRows/2 && j == numberOfColumns/2) {
          grid.getCellMap().put(coord, new SpreadingFireCell(coord, States.BURNING));
        }
        grid.getCellMap().put(coord, new SpreadingFireCell(coord, States.TREE));
      }
    }
  }

  public void update() {

  }

}
