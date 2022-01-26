package Model;

import java.util.Map;

public class Percolation extends Simulation {

  public Percolation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup) {
    super(numberOfRows, numberOfColumns);
    initializeGridCells(setup);
  }


  protected void createGrid() {grid = new PercolationGrid(numberOfRows, numberOfColumns);}


  protected void initializeGridCells(Map<Coordinate, Integer> setup) {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.Percolation.OPEN;
        case 1 -> state = States.Percolation.PERCOLATED;
        case 2 -> state = States.Percolation.BLOCKED;
      }
      grid.getCellMap().put(c, new PercolationCell(c, state));
    }
  }


  protected void update() {

  }
}
