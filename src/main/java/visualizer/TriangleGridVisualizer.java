package visualizer;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TriangleGridVisualizer extends GridVisualizer {
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
  public TriangleGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns,
      Grid grid) {
    super(width, height, numberOfRows, numberOfColumns, grid);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = getHeight() / Double.valueOf(getNumRows());
    cellWidth = getWidth()   / Double.valueOf( getNumColumns()/2+1);
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
    double xPos = 0;
    double yPos = 0;
    int initial_i=0;
    for (int i = 0; i < getNumRows(); i++) {
      xPos =0;
      for (int j = 0; j < getNumColumns(); j++) {
        Coordinate c = new Coordinate(i, j);
        String cellState =getGrid().getCellMap().get(c).toString();
        Text text = new Text(xPos+cellWidth/3, yPos+cellHeight/1.5, cellState);

        cellGroup.getChildren().add(createCell(xPos, yPos, c));
        cellGroup.getChildren().add(text);

        xPos = xPos + cellWidth/2;
      }
        yPos = yPos + cellHeight ;
    }
    return cellGroup;
  }

  @Override
  protected Polygon createCell(double xPos, double yPos, Coordinate c) {
    Polygon newCell ;
    if((c.getRow()+c.getColumn())%2==1) {newCell = new Polygon(xPos, yPos, xPos + cellWidth, yPos, xPos + cellWidth / 2,
        yPos + cellHeight );
    }
    else {newCell=new Polygon(xPos + cellWidth / 2, yPos,
        xPos + cellWidth, yPos+cellHeight,
        xPos,yPos+cellHeight);

    }
    //newCell.setStroke(Color.BLACK);
    newCell.setFill(getColorMap().getStateMatch(getGrid().getCellMap().get(c).toString()));
    return newCell;
  }

}
