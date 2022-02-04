package Model.FallingSand;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.Map;

public class FallingSand extends Simulation {

  public FallingSand(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
  }


  protected void createGrid() {grid = new FallingSandGrid(getNumberOfRows(), getNumberOfColumns());}

  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state = null;
      switch (getSetup().get(c)) {
        case 0 -> state = States.FallingSand.EMPTY;
        case 1 -> state = States.FallingSand.METAL;
        case 2 -> state = States.FallingSand.SAND;
        case 3 -> state = States.FallingSand.WATER;

      }
      grid.getCellMap().put(c, new FallingSandCell(c, state, getEdgeType(), getDirection(),
          getNumberOfRows(), getNumberOfColumns(), grid));
    }
  }

  protected void updateData() {
    int empty = 0, metal = 0, sand = 0, water = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.FallingSand.EMPTY) {
        empty++;
      }
      else if (state == States.FallingSand.METAL) {
        metal++;
      }
      else if (state == States.FallingSand.SAND) {
        sand++;
      }
      else if (state == States.FallingSand.WATER) {
        water++;
      }
    }
    getData().put(States.FallingSand.EMPTY, empty);
    getData().put(States.FallingSand.METAL, metal);
    getData().put(States.FallingSand.SAND, sand);
    getData().put(States.FallingSand.WATER, water);
  }
}
