package Model;



public class WaTorGrid extends Grid {
  public WaTorGrid(int numberOfRows, int numberOfColumns) {
    super(numberOfRows, numberOfColumns);
  }

  protected void makeSwap(Coordinate c, Coordinate newHome) {
    Cell current = cellMap.get(c);
    Cell swap = cellMap.get(newHome);
    current.setPosition(newHome);
    swap.setPosition(c);
    cellMap.put(newHome, current);
    cellMap.put(c, swap);
  }



}
