package Model;

import java.util.Map;

public class WaTor extends Simulation {
  private int fishChronon;
  private int sharkChronon;

  public WaTor(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      int fishChronon, int sharkChronon) {
    super(numberOfRows, numberOfColumns, setup);
    this.fishChronon = fishChronon;
    this.sharkChronon = sharkChronon;
    initializeGridCells();
  }


  protected void createGrid() {grid = new WaTorGrid(numberOfRows, numberOfColumns);}

  @Override
  protected void initializeGridCells() {
    if (setup == null) return;
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> {
          state = States.WaTor.EMPTY;
          ((WaTorGrid) grid).setEmptySpots(c);
        }
        case 1 -> state = States.WaTor.FISH;
        case 2 -> state = States.WaTor.SHARK;
      }
      grid.getCellMap().put(c, new WaTorCell(c, state, grid, fishChronon, sharkChronon));
    }


  }
}
