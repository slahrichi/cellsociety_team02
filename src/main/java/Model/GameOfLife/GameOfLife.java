package Model.GameOfLife;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.List;
import java.util.Map;

/**
 * class that extends the `Simulation` class for implementing the Game Of Life. The class
 * initializes the states of all cells given the states defined in the model
 *
 * @author Matthew Giglio
 */
public class GameOfLife extends Simulation {

  private static final int DEAD = 0;
  private static final int ALIVE = 1;
  private static final String INVALID = "Invalid state number";


  public GameOfLife(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
  EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
  }


  protected void createGrid() {
    grid = new GameOfLifeGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case DEAD -> state = States.GameOfLife.DEAD;
        case ALIVE -> state = States.GameOfLife.ALIVE;
        default -> throw new IllegalArgumentException(INVALID);
      }
      grid.getCellMap().put(c, new GameOfLifeCell(c, state, getEdgeType(), getDirection(),
          getNumberOfRows(), getNumberOfColumns()));
    }
  }

  protected void updateData() {
    int dead = 0, alive = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.GameOfLife.DEAD) {
        dead++;
      }
      else if (state == States.GameOfLife.ALIVE) {
        alive++;
      }
    }
    getData().put(States.GameOfLife.DEAD, dead);
    getData().put(States.GameOfLife.ALIVE, alive);
  }
}
