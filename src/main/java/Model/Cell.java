package Model;

public abstract class Cell {
  protected Coordinate position;
  protected Enum currentState;
  protected Enum futureState;
  protected int[] rowDelta = {-1, 1, 0, 0, 1, 1, -1, -1};
  protected int[] colDelta = {0, 0, -1, 1, -1, 1, -1, 1};
  protected Cell(Coordinate position, Enum initialState) {
    this.position = position;
    this.currentState = initialState;
  }

  protected void setPosition(Coordinate c) {position = c;}

  public Enum getCurrentState() {return currentState;}

  protected abstract void updateState();

  protected abstract void determineNextState(Grid grid);

  @Override
  public String toString() {
    return currentState.toString();
  }
}
