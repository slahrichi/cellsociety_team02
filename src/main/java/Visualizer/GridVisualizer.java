package cellsociety;

import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class GridVisualizer {

  private int gridWidth;
  private int gridHeight;
  private int numRows;
  private int numColumns;
  private ColorMap colorMap;
  private Grid myGrid;

  public GridVisualizer(int width, int height, int numberOfRows, int numberOfColumns, Grid grid) {
    this.gridWidth = width;
    this.gridHeight = height;
    this.numRows = numberOfRows;
    this.numColumns = numberOfColumns;
    this.colorMap = new ColorMap();
    this.myGrid = grid;

  }

  protected abstract void calculateCellSize();

  public abstract Group makeRoot();

  protected abstract Group arrangeCells();

  protected abstract Shape createCell(int xPos, int yPos, Coordinate c);

  protected int getWidth() {
    return this.gridWidth;
  }

  protected int getHeight() {
    return this.gridHeight;
  }

  protected int getNumRows() {
    return this.numRows;
  }

  protected int getNumColumns() {
    return this.numColumns;
  }

  protected ColorMap getColorMap() {
    return this.colorMap;
  }

  protected Grid getGrid() {
    return this.myGrid;
  }


}
