package Model.RockPaperScissors;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import Model.Simulation;
import Model.States;

import java.util.Map;

public class RockPaperScissors extends Simulation {

  private int threshold;

  public RockPaperScissors(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction, int threshold) {
    super(numberOfRows, numberOfColumns, setup, edgeType, direction);
    this.threshold = threshold;
    initializeGridCells();

  }

  protected void createGrid() {grid = new RockPaperScissorsGrid(numberOfRows, numberOfColumns);}



  protected void initializeGridCells() {
    for (Coordinate c : setup.keySet()) {
      Enum state = null;
      switch (setup.get(c)) {
        case 0 -> state = States.RockPaperScissors.ROCK;
        case 1 -> state = States.RockPaperScissors.PAPER;
        case 2 -> state = States.RockPaperScissors.SCISSORS;
      }
      grid.getCellMap().put(c, new RockPaperScissorsCell(c, state, edgeType, direction,
          numberOfRows, numberOfColumns, threshold));
    }
  }


  protected void updateData() {
    int rock = 0, paper = 0, scissors = 0;
    for (Cell cell : grid.getCellMap().values()) {
      Enum state = cell.getCurrentState();
      if (state == States.RockPaperScissors.ROCK) {
        rock++;
      }
      else if (state == States.RockPaperScissors.PAPER) {
        paper++;
      }
      else if (state == States.RockPaperScissors.SCISSORS) {
        scissors++;
      }
    }
    data.put(States.RockPaperScissors.ROCK, rock);
    data.put(States.RockPaperScissors.PAPER, paper);
    data.put(States.RockPaperScissors.SCISSORS, scissors);
  }
}


