package visualizer;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * An abstract class for Grid visualization, it has abstractions for methods which would be used by
 * any grid no matter the shape(Rectangular, Triangular, etc.). Also protected methods for getting
 * instance variables which would be shared among subclasses, so constructors are not needed for
 * each one.
 * <p>
 * The <class>SimulationVisualizer</class> depends on this to add the gridGroup to the main root for
 * the scene. This class depends on JavaFx. as well as the <class>ColorMap</class> class to get the
 * color values corresponding to state.
 *
 * @author Luka Mdivani
 */
public abstract class GridVisualizer {

  private final int gridWidth;
  private final int gridHeight;
  private final int numRows;
  private final int numColumns;
  private final ColorMap colorMap;
  private final Grid myGrid;
  private boolean gridLineRule;
  private boolean cellStateDisplayRule;

  /**
   * @param width                width of the space allocated for the grid on the screen.
   * @param height               height of the space allocated for the grid on the screen.
   * @param numberOfRows         number of rows in the grid
   * @param numberOfColumns      number of columns in the grid
   * @param grid                 the Grid object taken from the Simulation object, used to get the
   *                             states of the cells during simulation.
   * @param gridRule             initial rule of whether gridlines should be shown.
   * @param cellStateDisplayRule initial rule of whether cell states should be displayed.
   */
  public GridVisualizer(int width, int height, int numberOfRows, int numberOfColumns, Grid grid,
      boolean gridRule, boolean cellStateDisplayRule) {
    this.gridWidth = width;
    this.gridHeight = height;
    this.numRows = numberOfRows;
    this.numColumns = numberOfColumns;
    this.colorMap = new ColorMap();
    this.myGrid = grid;
    this.gridLineRule = gridRule;
    this.cellStateDisplayRule = cellStateDisplayRule;

  }


  /**
   * Calculates the cell size depending on the screen size allocated for the grid, as well as number
   * of necessary rows and columns for the cells. This makes it easy to visualize grids of any size,
   * without the need for hard-coding dimensions and sizes.
   */
  protected abstract void calculateCellSize();

  /**
   * Creates and returns a javaFX <code>Group</code> of the grid backgrounds shape and cell nodes
   * arranged in the necessary order,and that group is added to the main scene root, makes it easy
   * to change the grid on every update.
   *
   * @return the <code>Group</code> which has all the cell children <code>Node</code>s for the grid.
   */
  public abstract Group makeRoot();

  /**
   * Checks the state of cells in the simulation grid, and arranges the graphical cell Shapes into
   * the correct order with correct features(Color).
   *
   * @return a <code>Group</code> of cells arranged in the correct order and color.
   */
  protected abstract Group arrangeCells();

  /**
   * Creates the correct Shape object for the cell, assigns color depending on state.
   *
   * @param xPos the X coordinate of the cell with respect to the space allocated for the grid.
   * @param yPos the Y coordinate of the cell with respect to the space allocated for the grid.
   * @param c    the <code>Coordinate</code> index pair of the cell with respect to position in the
   *             2D array of cells.
   * @return the cell Shape object.
   */
  protected abstract Shape createCell(double xPos, double yPos, Coordinate c);

  /**
   * @param xPos         the X coordinate of the cell with respect to the space allocated for the
   *                     grid.
   * @param yPos         the Y coordinate of the cell with respect to the space allocated for the
   *                     grid.
   * @param columnNumber the column number index of the cell.
   * @return the coordinates on which the Text corresponding to cell and its state should be
   * displayed for correct alignment.
   */
  protected abstract double[] getTextCoordinates(double xPos, double yPos, int columnNumber);

  protected String getCellStateString(Coordinate c) {
    return getGrid().getCellMap().get(c).toString();
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return width of the grid.
   */
  protected int getWidth() {
    return this.gridWidth;
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return Height of the grid.
   */
  protected int getHeight() {
    return this.gridHeight;
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return number of rows of the grid.
   */
  protected int getNumRows() {
    return this.numRows;
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return number of columns in the grid.
   */
  protected int getNumColumns() {
    return this.numColumns;
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return width of the grid.
   */
  protected ColorMap getColorMap() {
    return this.colorMap;
  }

  /**
   * Convenient getter for use in subclasses, avoids having protected instance variables.
   *
   * @return <class>Grid</class> class object taken from the <code>Simulation</code> object.
   */
  protected Grid getGrid() {
    return this.myGrid;
  }

  protected boolean getGridRule() {
    return this.gridLineRule;
  }

  /**
   * Method which redefines whether gridLines should be added to the grid.Called to handle user
   * input.
   */
  public void toggleGridRule() {
    this.gridLineRule = !this.gridLineRule;
  }


  /**
   * Method which redefines whether cells should show their states.Called to handle user input.
   */
  public void toggleCellStateDisplay() {
    this.cellStateDisplayRule = !cellStateDisplayRule;
  }

  protected void addStateTagsToDisplay(double xPos, double yPos, int j, Coordinate c,
      Group cellGroup) {
    if (cellStateDisplayRule) {
      double[] textCoordinate = getTextCoordinates(xPos, yPos, j);
      Text stateTag = new Text(textCoordinate[0], textCoordinate[1], getCellStateString(c));
      stateTag.setId("stateTag");
      cellGroup.getChildren().add(stateTag);
    }
  }


}
