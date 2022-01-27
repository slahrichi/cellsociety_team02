package Model;

public class WaTorCell extends Cell {
  private Grid grid;
  private int fishChronon;
  private int sharkChronon;
  public WaTorCell(Coordinate c, Enum state, Grid grid, int fishChronon, int sharkChronon) {
    super(c, state);
    this.grid = grid;
    this.fishChronon = fishChronon;
    this.sharkChronon = sharkChronon;
  }

  @Override
  protected void updateState() {

  }

  @Override
  protected void determineNextState(Grid grid) {

  }
}
