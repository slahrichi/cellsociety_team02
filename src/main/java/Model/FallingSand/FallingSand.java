package Model.FallingSand;

import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.SpreadingFire.SpreadingFireCell;
import Model.States;
import java.util.Map;

public class FallingSand extends Simulation {

  public FallingSand(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
  }


  protected void createGrid() {grid = new FallingSandGrid(numberOfRows, numberOfColumns);}

  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.FallingSand.EMPTY;
        case 1 -> state = States.FallingSand.METAL;
        case 2 -> state = States.FallingSand.SAND;
        case 3 -> state = States.FallingSand.WATER;

      }
      grid.getCellMap().put(c, new FallingSandCell(c, state, edgeType, direction, numberOfRows,
          numberOfColumns));
    }
  }


}
