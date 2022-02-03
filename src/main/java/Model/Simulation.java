package Model;


import Model.Edge.EdgeType;
import Model.Neighbors.Direction;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for storing the fundamental attributes of a cellular automata simulation. The
 * class holds a `Grid` object, initializes the states of the cells within it, and facilitates
 * communication between the various model classes
 *
 * @author Matthew Giglio
 */
public abstract class Simulation {

  protected Grid grid;
  protected int numberOfColumns;
  protected int numberOfRows;
  protected Map<Coordinate, Integer> setup;
  protected EdgeType edgeType;
  protected Direction direction;
  protected Map<Enum, Integer> data;

  public Simulation(int numberOfRows, int numberOfColumns, Map<Coordinate, Integer> setup,
      EdgeType edgeType, Direction direction) {
    this.numberOfColumns = numberOfColumns;
    this.numberOfRows = numberOfRows;
    this.setup = setup;
    this.edgeType = edgeType;
    this.direction = direction;
    this.data = new HashMap<>();
    createGrid();
    initializeGridCells();
  }

  /**
   * getter method for the `Grid` stored within the class
   *
   * @return the `Grid` object instance variable
   */
  public Grid getGrid() {
    return grid;
  }

  protected abstract void createGrid();

  protected abstract void initializeGridCells();

  /**
   * method for updating the states of the cells in the model given the model's rules. Made public
   * so that the view portion of the program can utilize the method to properly update the graphics
   * at each time step
   */
  public void update() {
    determineNewCellStates();
    updateCellStates();
    updateData();
  }

  protected abstract void updateData();

  public Map<Enum, Integer> getData() {return data;}

  protected void determineNewCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.determineNextState(grid);
    }
  }

  protected void updateCellStates() {
    for (Cell cell : grid.getCellMap().values()) {
      cell.updateState();
    }
  }

}
