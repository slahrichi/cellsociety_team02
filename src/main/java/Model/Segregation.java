package Model;

import java.util.Map;

public class Segregation extends Simulation {

  private Map<Coordinate, Integer> setup;
  private double threshold;
  public Segregation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      double threshold) {
    super(numberOfRows, numberOfColumns);
    this.setup = setup;
    this.threshold = threshold;
    createGrid();
    initializeGridCells();
  }


  protected void createGrid() { grid = new SegregationGrid(numberOfRows, numberOfColumns);}


  protected void initializeGridCells() {
    if (setup == null) return;
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> {
          state = States.Segregation.EMPTY;
          ((SegregationGrid) grid).setEmptySpots(c);
        }
        case 1 -> state = States.Segregation.REP;
        case 2 -> state = States.Segregation.DEM;
      }
      grid.getCellMap().put(c, new SegregationCell(c, state, grid, threshold));
    }

  }

}
