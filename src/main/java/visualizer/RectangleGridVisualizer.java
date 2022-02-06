package visualizer;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Extension of grid class which Sets up a rectangular graphical grid. With rectangular cells.
 *
 * @author Luka Mdivani
 */
public class RectangleGridVisualizer extends GridVisualizer {

  private double cellWidth;
  private double cellHeight;

  /**
   * @param width           width of the space allocated for the grid on the screen.
   * @param height          height of the space allocated for the grid on the screen.
   * @param numberOfRows    number of rows in the grid
   * @param numberOfColumns number of columns in the grid
   * @param grid            the Grid object taken from the Simulation object, used to get the states
   *                        of the cells during simulation.
   */
  public RectangleGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns,
      Grid grid, boolean gridRule, boolean cellTextDisplayRule) {
    super(width, height, numberOfRows, numberOfColumns, grid, gridRule, cellTextDisplayRule);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = (getHeight() - (getNumRows() + 1)) / Double.valueOf(getNumRows());
    cellWidth = (getWidth() - (getNumColumns() + 1)) / Double.valueOf(getNumColumns());
  }

  @Override
  public Group makeRoot() {
    Group gridRoot = new Group();
    gridRoot.getChildren().add(arrangeCells());
    return gridRoot;
  }

  @Override
  protected Group arrangeCells() {
    Group cellGroup = new Group();
    double xPos;
    double yPos = 0;
    for (int i = 0; i < getNumRows(); i++) {
      xPos = 0;
      for (int j = 0; j < getNumColumns(); j++) {
        Coordinate c = new Coordinate(i, j);
        cellGroup.getChildren().add(createCell(xPos, yPos, c));
        addStateTagsToDisplay(xPos, yPos, j, c, cellGroup);
        xPos = xPos + cellWidth;
      }
      yPos = yPos + cellHeight;
    }
    return cellGroup;
  }

  @Override
  protected double[] getTextCoordinates(double xPos, double yPos, int j) {
    double[] textCoordinate = new double[2];
    textCoordinate[0] = xPos + cellWidth / 4;
    textCoordinate[1] = yPos + cellHeight / 2;

    return textCoordinate;
  }

  @Override
  protected Rectangle createCell(double xPos, double yPos, Coordinate c) {
    Rectangle newCell = new Rectangle(xPos, yPos, cellWidth, cellHeight);
    if (getGridRule()) {
      newCell.setStroke(Color.BLACK);
    }
    newCell.setFill(getColorMap().getStateMatch(getCellStateString(c)));
    return newCell;
  }

}
