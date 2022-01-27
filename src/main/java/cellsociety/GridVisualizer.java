package cellsociety;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class GridVisualizer {
  protected int gridWidth;
  protected int gridHeight;
  protected int numRows;
  protected int numColumns;

  public GridVisualizer(int width, int height,int numberOfRows,int numberOfColumns){
    this.gridWidth=width;
    this.gridHeight=height;
    this.numRows = numberOfRows;
    this.numColumns = numberOfColumns;

  }

  protected abstract void calculateCellSize() ;

  public abstract Group makeRoot() ;

  protected abstract Group arrangeCells();

  protected abstract Shape createCell(int xPos, int yPos) ;


}
