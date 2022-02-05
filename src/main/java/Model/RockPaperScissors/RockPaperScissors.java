package Model.RockPaperScissors;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;

import java.util.List;
import java.util.Map;

/**
 * Class to simulate a cellular automata model inspired by rock-paper-scissors. The class
 * initializes the states of all cells given the states defined in the model
 *
 * @author Matthew Giglio
 */
public class RockPaperScissors extends Simulation {

  private int threshold;

  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private static final String INVALID = "Invalid state number";

  /**
   * @param numberOfRows    number of rows in simulation grid
   * @param numberOfColumns number of columns in simulation grid
   * @param setup           map to initialize the states of the grid's cells
   * @param edgeType        edge type of grid boundaries
   * @param direction       directions from which a cell can have neighbors given its shape
   * @param neighborConfig  configuration of neighbors being considered
   * @param threshold       number of dominating neighbors a cell needs to change states
   */

  public RockPaperScissors(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig, int threshold) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction, neighborConfig);
    this.threshold = threshold;
    initializeGridCells();

  }

  protected void createGrid() {
    grid = new RockPaperScissorsGrid(getNumberOfRows(), getNumberOfColumns());
  }


  protected void initializeGridCells() {
    for (Coordinate c : getSetup().keySet()) {
      Enum state;
      switch (getSetup().get(c)) {
        case ROCK -> state = States.RockPaperScissors.ROCK;
        case PAPER -> state = States.RockPaperScissors.PAPER;
        case SCISSORS -> state = States.RockPaperScissors.SCISSORS;
        default -> throw new IllegalArgumentException(INVALID);

      }
      grid.getCellMap().put(c, new RockPaperScissorsCell(c, state, getEdgeType(), getDirection(),
          getNumberOfRows(), getNumberOfColumns(), getNeighborConfig(), threshold));
    }
  }


  protected void updateData() {
    int rock = 0, paper = 0, scissors = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.RockPaperScissors.ROCK) {
        rock++;
      } else if (state == States.RockPaperScissors.PAPER) {
        paper++;
      } else if (state == States.RockPaperScissors.SCISSORS) {
        scissors++;
      }
    }
    getData().put(States.RockPaperScissors.ROCK, rock);
    getData().put(States.RockPaperScissors.PAPER, paper);
    getData().put(States.RockPaperScissors.SCISSORS, scissors);
  }
}


