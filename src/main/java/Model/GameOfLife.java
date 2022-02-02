package Model;

import Model.Edge.EdgeType;
import java.util.Map;

/**
 * class that extends the `Simulation` class for implementing the Game Of Life. The class
 * initializes the states of all cells given the states defined in the model
 *
 * @author Matthew Giglio
 */
public class GameOfLife extends Simulation {

  private EdgeType edgeType;
  public GameOfLife(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
  EdgeType edgeType) {
    super(numberOfRows, numberOfColumns, setup);
    this.edgeType = edgeType;
    initializeGridCells();
  }


  protected void createGrid() {
    grid = new GameOfLifeGrid(numberOfRows, numberOfColumns);
  }


  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.GameOfLife.DEAD;
        case 1 -> state = States.GameOfLife.ALIVE;
      }
      grid.getCellMap().put(c, new GameOfLifeCell(c, state, edgeType));
    }
  }
}
