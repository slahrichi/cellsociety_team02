package Model.Segregation;

import Model.Cell;
import Model.Coordinate;
import Model.Edge.EdgeType;
import Model.Grid;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.States;
import java.util.List;

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
  public SegregationCell(Coordinate position, Enum initialState, Grid grid, EdgeType edgeType,
      Direction direction, double threshold, int numberOfRows, int numberOfColumns,
      List<Integer> neighborConfig) {
    super(position, initialState, edgeType, direction, numberOfRows, numberOfColumns,
        neighborConfig);
    this.threshold = threshold;
    this.grid = (SegregationGrid) grid;
    dissatisfied = false;
  }


  protected void updateState() {
    if (dissatisfied) {
      grid.moveCell(getPosition());
    }
    ;
  }

  protected void determineNextState(Grid grid) {
    int dems = 0;
    int reps = 0;
    int[] rowDelta = Neighbors.getRowDelta(getDirection(), getNeighborConfig());
    int[] colDelta = Neighbors.getColDelta(getDirection(), getNeighborConfig());
    for (int i = 0; i < rowDelta.length; i++) {
      Coordinate neighbor = getPosition().checkNeighbors(rowDelta[i], colDelta[i], getEdgeType(),
          getNumberOfRows(), getNumberOfColumns());
      if (grid.isInBounds(neighbor)) {
        Enum state = getNeighborState(neighbor, grid);
        if (state == States.Segregation.DEM) {
          dems++;
        } else if (state == States.Segregation.REP) {
          reps++;
        }
      }
    }
    checkIfSatisfied(dems, reps);
  }

  private void checkIfSatisfied(int dems, int reps) {
    if (getCurrentState() == States.Segregation.EMPTY) {
      dissatisfied = false;
    } else if (getCurrentState() == States.Segregation.DEM) {
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
