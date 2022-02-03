package Model.RockPaperScissors;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors.Direction;

public class RockPaperScissorsCell extends Cell {

  private int threshold;
  public RockPaperScissorsCell(Coordinate position, Enum initialState, EdgeType edgeType, Direction direction,
      int numberOfRows, int numberOfColumns, int threshold) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
    this.threshold = threshold;

  }


  protected void updateState() {

  }

  protected void determineNextState(Grid grid) {

  }
}
