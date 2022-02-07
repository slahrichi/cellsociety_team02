package visualizer.gridvisualizer;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * The class is an extension of GridVisualizer abstract class. It builds the gridGroup in the case
 * of rectangular grid with triangular cell tiling.
 * <p>
 * Along with JavaFX, this class depends on the GridVisualizer class, as well as
 * SimulationVisualizer. SimulationVisualizer in turn depends on this class.
 *
 * @author Luka Mdivani
 */
public class TriangleGridVisualizer extends GridVisualizer {

  private double cellWidth;
  private double cellHeight;

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
  public TriangleGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns,
      Grid grid, boolean gridRule, boolean cellStateDisplayRule) {
    super(width, height, numberOfRows, numberOfColumns, grid, gridRule, cellStateDisplayRule);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = getHeight() / Double.valueOf(getNumRows());
    cellWidth = getWidth() / Double.valueOf((getNumColumns() / 2) + 1);
  }

  @Override
  //This is not implemented in the abstract class because someone might want to have a custom background
  //for the grid.Thus need to add more items to the gridGroup.
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
        xPos = xPos + cellWidth / 2;
      }
      yPos = yPos + cellHeight;
    }
    return cellGroup;
  }

  @Override
  protected double[] getTextCoordinates(double xPos, double yPos, int j) {
    double[] textCoordinate = new double[2];
    textCoordinate[0] = xPos + cellWidth / 3;
    textCoordinate[1] = yPos + cellHeight / 1.5;
    return textCoordinate;
  }


  @Override
  protected Polygon createCell(double xPos, double yPos, Coordinate c) {
    Polygon newCell;
    if ((c.getRow() + c.getColumn()) % 2 == 1) {
      newCell = new Polygon(xPos, yPos, xPos + cellWidth, yPos, xPos + cellWidth / 2,
          yPos + cellHeight);
    } else {
      newCell = new Polygon(xPos + cellWidth / 2, yPos, xPos + cellWidth, yPos + cellHeight, xPos,
          yPos + cellHeight);

    }
    if (getGridRule()) {
      newCell.setStroke(Color.BLACK);
    }
    newCell.setFill(getColorMap().getStateMatch(getCellStateString(c)));
    return newCell;
  }

}
