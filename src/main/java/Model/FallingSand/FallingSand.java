package Model.FallingSand;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
import java.util.Map;

public class FallingSand extends Simulation {

  private static final int EMPTY = 0;
  private static final int METAL = 1;
  private static final int SAND = 2;
  private static final int WATER = 3;
  private static final String INVALID = "Invalid state number";

  public FallingSand(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
  }


  protected void createGrid() {grid = new FallingSandGrid(getNumberOfRows(), getNumberOfColumns());}

  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case EMPTY -> state = States.FallingSand.EMPTY;
        case METAL -> state = States.FallingSand.METAL;
        case SAND -> state = States.FallingSand.SAND;
        case WATER -> state = States.FallingSand.WATER;
        default -> throw new IllegalArgumentException(INVALID);
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
