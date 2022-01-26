package Model;

public class SegregationCell extends Cell {

  public SegregationCell(Coordinate position, Enum initialState) {
    super(position, initialState);
  }


  protected void updateState() {
    currentState = futureState;
  }

  protected void determineNextState(Grid grid) {


  }
}
