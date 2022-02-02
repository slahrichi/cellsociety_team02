package Model;

import Model.Edge.EdgeType;

/**
 * Extension of `Cell` superclass that manages the updating of cell states in the grid given the
 * modeling rules of Segregation
 *
 * @author Matthew Giglio
 */
public class SegregationCell extends Cell {

  private boolean dissatisfied;
  private double threshold;
  private SegregationGrid grid;

  /**
   * @param position     `Coordinate` value of the cell on the grid
   * @param initialState initial state of the cell
   * @param grid         the `Grid` object in which the cell exists
   * @param threshold    satisfaction threshold for constituents given their neighbors
   */
  public SegregationCell(Coordinate position, Enum initialState, Grid grid,
      EdgeType edgeType, double threshold) {
    super(position, initialState, edgeType);
    this.threshold = threshold;
    this.grid = (SegregationGrid) grid;
    dissatisfied = false;
  }


  protected void updateState() {
    if (dissatisfied) {
      grid.moveCell(position);
    }
    ;
  }

  protected void determineNextState(Grid grid) {
    int dems = 0;
    int reps = 0;
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = position.checkNeighbors(rowDelta[i], colDelta[i]);
      if (grid.isInBounds(neighbor)) {
        if (grid.getCellMap().get(neighbor).getCurrentState() == States.Segregation.DEM) {
          dems++;
        } else if (grid.getCellMap().get(neighbor).getCurrentState() == States.Segregation.REP) {
          reps++;
        }
      }
    }
    checkIfSatisfied(dems, reps);
  }

  private void checkIfSatisfied(int dems, int reps) {
    if (currentState == States.Segregation.EMPTY) {
      dissatisfied = false;
    } else if (currentState == States.Segregation.DEM) {
      if (dems * 1.0 / (dems + reps) < threshold) {
        dissatisfied = true;
      }
    } else {
      if (reps * 1.0 / (dems + reps) < threshold) {
        dissatisfied = true;
      }
    }
  }
}
