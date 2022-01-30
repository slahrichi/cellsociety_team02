package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Extension of `Grid` superclass that handles the swapping of cells in the Segregation model.
 *
 * @author Matthew Giglio
 */
public class SegregationGrid extends Grid {

  private List<Coordinate> emptySpots;
  private Random random;

  /**
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   */
  public SegregationGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
    emptySpots = new ArrayList<>();
    random = new Random();
  }

  protected void setEmptySpots(Coordinate c) {
    emptySpots.add(c);
  }

  protected void moveCell(Coordinate c) {
    if (emptySpots.size() != 0) {
      Coordinate newHome = emptySpots.remove(random.nextInt(emptySpots.size()));
      makeSwap(c, newHome);
      setEmptySpots(c);
    }
  }

  private void makeSwap(Coordinate c, Coordinate newHome) {
    Cell current = cellMap.get(c);
    Cell swap = cellMap.get(newHome);
    current.setPosition(newHome);
    swap.setPosition(c);
    cellMap.put(newHome, current);
    cellMap.put(c, swap);
  }
}
