package Model;

import java.util.Map;

public class Segregation extends Simulation {

  private Map<Coordinate, Integer> setup;
  public Segregation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup) {
    super(numberOfRows, numberOfColumns);
    this.setup = setup;
    initializeGridCells();
  }


  protected void createGrid() { grid = new SegregationGrid(numberOfRows, numberOfColumns);}


  protected void initializeGridCells() {
    if (setup == null) return;
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.Segregation.EMPTY;
        case 1 -> state = States.Segregation.REP;
        case 2 -> state = States.Segregation.DEM;
      }
      grid.getCellMap().put(c, new SegregationCell(c, state));
    }

  }

}
