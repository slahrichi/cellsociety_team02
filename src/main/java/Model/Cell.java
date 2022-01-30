package Model;

/**
 * Abstract class for representing the basic attributes of a cell in a cellular automata model.
 * Methods are mostly implemented as protected methods in order to limit their usage to extended
 * usages of the class
 *
 * @author Matthew Giglio
 */
public abstract class Cell {

  protected Coordinate position;
  protected Enum currentState;
  protected Enum futureState;
  protected int[] rowDelta = {-1, 1, 0, 0, 1, 1, -1, -1};
  protected int[] colDelta = {0, 0, -1, 1, -1, 1, -1, 1};

  public Cell(Coordinate position, Enum initialState) {
    this.position = position;
    this.currentState = initialState;
  }

  protected Coordinate getPosition() {
    return position;
  }

  protected void setPosition(Coordinate c) {
    position = c;
  }

  /**
   * Getter method for a cell's current state, particularly helpful for
   * @return the current state of the cell
   */
  public Enum getCurrentState() {
    return currentState;
  }

  protected abstract void updateState();

  protected abstract void determineNextState(Grid grid);

  @Override
  public String toString() {
    return currentState.toString();
  }
}
