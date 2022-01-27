package Model;

public class SegregationCell extends Cell {

  private boolean dissatisfied;
  private double threshold;
  private Grid grid;
  public SegregationCell(Coordinate position, Enum initialState, Grid grid,
      double threshold) {
    super(position, initialState);
    this.threshold = threshold;
    this.grid = grid;
    dissatisfied = false;
  }


  protected void updateState() {
    if (dissatisfied) {
      ((SegregationGrid) grid).moveCell(position);
    };
  }

  protected void determineNextState(Grid grid) {
    int dems = 0;
    int reps = 0;
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.Segregation.DEM) {
          dems++;
        }
        else if (grid.getCellMap().get(neighbor).getCurrentState() == States.Segregation.REP) {
          reps++;
        }
        }
      }
    checkIfSatisfied(dems, reps);
  }

  private void checkIfSatisfied(int dems, int reps) {
    if (currentState == States.Segregation.EMPTY) {
      dissatisfied = false;
    }
    else if (currentState == States.Segregation.DEM) {
      if (dems*1.0/(dems + reps) < threshold) {
        dissatisfied = true;
      }
    }
    else {
      if (reps*1.0/(dems + reps) < threshold) {
        dissatisfied = true;
      }
    }
    System.out.println(position);
    System.out.println(dissatisfied);
  }
}
