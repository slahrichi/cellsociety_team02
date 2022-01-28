package Model;

import java.util.Map;

public class GameOfLife extends Simulation {

  public GameOfLife(int numberOfColumns, int numberOfRows, Map<Coordinate, Integer> setup) {
    super(numberOfColumns, numberOfRows, setup);
  }


  protected void createGrid() {grid = new GameOfLifeGrid(numberOfColumns, numberOfRows);}


  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.GameOfLife.DEAD;
        case 1 -> state = States.GameOfLife.ALIVE;
      }
      grid.getCellMap().put(c, new GameOfLifeCell(c, state));
    }
  }
}
