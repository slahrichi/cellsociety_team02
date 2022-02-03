package Model.FallingSand;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors.Direction;

public class FallingSandCell extends Cell {

  public FallingSandCell(Coordinate position, Enum initialState, EdgeType edgeType,
      Direction direction, int numberOfRows, int numberOfColumns) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns);
  }

  @Override
  protected void updateState() {

  }

  @Override
  protected void determineNextState(Grid grid) {

  }

}
