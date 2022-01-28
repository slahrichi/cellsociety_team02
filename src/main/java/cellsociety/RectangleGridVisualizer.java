package cellsociety;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleGridVisualizer extends GridVisualizer {

  private int cellWidth;
  private int cellHeight;
  private int gapBetweenCells = 0;

  public RectangleGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns, Grid grid) {
    super(width, height, numberOfRows, numberOfColumns,grid);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = (gridHeight - (numRows + 1) * gapBetweenCells) / numRows;
    cellWidth = (gridWidth - (numColumns + 1) * gapBetweenCells) / numColumns;
  }

  @Override
  public Group makeRoot() {
    Group gridRoot = new Group();
    Rectangle grid = new Rectangle();
    grid.setWidth(gridWidth);
    grid.setHeight(gridHeight);
    grid.setFill(Color.NAVY);
    grid.setStroke(Color.BLACK);
    gridRoot.getChildren().add(grid);
    gridRoot.getChildren().add(arrangeCells());
    return gridRoot;
  }

  @Override
  protected Group arrangeCells() {
    Group cellGroup = new Group();
    int xPos = gapBetweenCells;
    int yPos = gapBetweenCells;
    for (int i = 0; i < numRows; i++) {
      xPos = gapBetweenCells;
      for (int j = 0; j < numColumns; j++) {
        Coordinate c= new Coordinate(i,j);

        cellGroup.getChildren().add(createCell(xPos, yPos,c));
        xPos = xPos + cellWidth + gapBetweenCells;
      }
      yPos = yPos + cellHeight + gapBetweenCells;
    }
    return cellGroup;
  }

  @Override
  protected Rectangle createCell(int xPos, int yPos,Coordinate c) {
    Rectangle newCell = new Rectangle(xPos, yPos, cellWidth, cellHeight);
    newCell.setStroke(Color.BLACK);
    newCell.setFill(colorMap.getStateMatch( myGrid.getCellMap().get(c).toString()) );
    return newCell;
  }

}
