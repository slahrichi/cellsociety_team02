package Model.GameOfLife;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;
import java.util.Map;

/**
 * class that extends the `Simulation` class for implementing the Game Of Life. The class
 * initializes the states of all cells given the states defined in the model
 *
 * @author Matthew Giglio
 */
public class GameOfLife extends Simulation {

  public GameOfLife(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
  EdgeType edgeType, Direction direction) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
  }


  protected void createGrid() {
    grid = new GameOfLifeGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state = null;
      switch (getSetup().get(c)) {
        case 0 -> state = States.GameOfLife.DEAD;
        case 1 -> state = States.GameOfLife.ALIVE;
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
