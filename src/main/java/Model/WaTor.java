package Model;

import Model.Edge.EdgeType;
import java.util.Map;

/**
 * Class for managing the model resources for a Wa-Tor simulation. Initializes all cell states and
 * updates their states upon command
 *
 * @author Matthew Giglio
 */
public class WaTor extends Simulation {

  private int fishChronon;
  private int sharkChronon;


  /**
   *
   * @param numberOfRows number of rows in grid
   * @param numberOfColumns number of cells in grid
   * @param setup `Map` to initialize the state of all cells
   * @param fishChronon number of turns before fish can reproduce
   * @param sharkChronon number of turns before shark can reproduce
   */
  public WaTor(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, int fishChronon, int sharkChronon) {
    super(numberOfRows, numberOfColumns, setup, edgeType);
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
        case 0 -> state = States.WaTor.EMPTY;
        case 1 -> state = States.WaTor.FISH;
        case 2 -> state = States.WaTor.SHARK;
      }
      grid.getCellMap().put(c, new WaTorCell(c, state, grid, edgeType, fishChronon, sharkChronon));
    }
  }

}
