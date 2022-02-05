package Model.WaTor;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
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

  private static final int EMPTY = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;
  private static final String INVALID = "Invalid state number";


  /**
   * @param numberOfRows    number of rows in grid
   * @param numberOfColumns number of cells in grid
   * @param setup           `Map` to initialize the state of all cells
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param neighborConfig  configuration of neighbors being considered
   * @param fishChronon     number of turns before fish can reproduce
   * @param sharkChronon    number of turns before shark can reproduce
   */
  public WaTor(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig, int fishChronon,
      int sharkChronon) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
    this.fishChronon = fishChronon;
    this.sharkChronon = sharkChronon;
    initializeGridCells();
  }


  protected void createGrid() {
    grid = new WaTorGrid(getNumberOfRows(), getNumberOfColumns());
  }

  @Override
  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case EMPTY -> state = States.WaTor.EMPTY;
        case FISH -> state = States.WaTor.FISH;
        case SHARK -> state = States.WaTor.SHARK;
        default -> throw new IllegalArgumentException(INVALID);

      }
      grid.getCellMap().put(c, new WaTorCell(c, state, grid, getEdgeType(), getDirection(),
          fishChronon, sharkChronon, getNumberOfRows(), getNumberOfColumns(), getNeighborConfig()));
    }
  }

  protected void updateData() {
    int empty = 0, fish = 0, sharks = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.WaTor.EMPTY) {
        empty++;
      } else if (state == States.WaTor.FISH) {
        fish++;
      } else if (state == States.WaTor.SHARK) {
        sharks++;
      }
    }
    getData().put(States.WaTor.EMPTY, empty);
    getData().put(States.WaTor.FISH, fish);
    getData().put(States.WaTor.SHARK, sharks);
  }
}
