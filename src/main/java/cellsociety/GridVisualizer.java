package cellsociety;

import Model.Coordinate;
import Model.Grid;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class GridVisualizer {
  protected int gridWidth;
  protected int gridHeight;
  protected int numRows;
  protected int numColumns;
  protected ColorMap colorMap;
  protected Grid myGrid;

  public GridVisualizer(int width, int height,int numberOfRows,int numberOfColumns, Grid grid){
    this.gridWidth=width;
    this.gridHeight=height;
    this.numRows = numberOfRows;
    this.numColumns = numberOfColumns;
    colorMap = new ColorMap();
    myGrid=grid;

  }

  protected abstract void calculateCellSize() ;

  public abstract Group makeRoot() ;

  protected abstract Group arrangeCells();

  protected abstract Shape createCell(int xPos, int yPos, Coordinate c) ;


}
