package Model;

public abstract class Cell {
  protected Coordinate position;

  public Cell(Coordinate position) {
    this.position = position;
  }

  protected abstract void updateState();

  protected abstract void determineNextState();
}
