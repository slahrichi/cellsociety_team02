package Model;

public abstract class Cell {
  protected Coordinate position;
  protected Enum currentState;
  protected Enum futureState;

  protected Cell(Coordinate position, Enum initialState) {
    this.position = position;
    this.currentState = initialState;
  }

  protected Enum getCurrentState() {return currentState;}

  protected abstract void updateState();

  protected abstract void determineNextState(Grid grid);
}
