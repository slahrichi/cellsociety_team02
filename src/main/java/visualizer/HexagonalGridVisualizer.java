package visualizer;


import Model.Coordinate;
import Model.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class HexagonalGridVisualizer extends GridVisualizer {

  private double cellWidth;
  private double cellHeight;
  private double hexEdgeWidth;
  private double gapBalancer;

  public HexagonalGridVisualizer(int width, int height, int numberOfRows, int numberOfColumns,
      Grid grid,boolean gridRule) {
    super(width, height, numberOfRows, numberOfColumns, grid,gridRule);
    calculateCellSize();

  }

  @Override
  protected void calculateCellSize() {
    cellHeight = getHeight() / Double.valueOf(getNumRows());
    cellWidth = getWidth() / Double.valueOf(getNumColumns());
    hexEdgeWidth=cellWidth/2;
    gapBalancer=hexEdgeWidth;
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
    for (int i = 0; i < getNumRows(); i++) {
      xPos = 0;
      for (int j = 0; j < getNumColumns(); j++) {
        Coordinate c = new Coordinate(i, j);
        double textXCord=0;
        double textYCord=0;
        if(j%2==1){textXCord=xPos-hexEdgeWidth/2 ;textYCord=yPos + cellHeight;}
        else{textXCord=xPos;textYCord=yPos + cellHeight/2;}
        Text stateTag = new Text(textXCord, textYCord, getCellStateString(c));
        stateTag.setId("stateTag");
        cellGroup.getChildren().addAll(createCell(xPos, yPos, c), stateTag);
        xPos = xPos + cellWidth;
        if(j%2==1 ){xPos=xPos-hexEdgeWidth;}
      }
      yPos = yPos + cellHeight;
    }
    return cellGroup;
  }


  @Override
  protected Polygon createCell(double xPos, double yPos, Coordinate c) {
    Polygon newCell;
    if (c.getColumn() % 2 == 1) {
      newCell = new Polygon(
          xPos-hexEdgeWidth/2,yPos+cellHeight/2,
          xPos-hexEdgeWidth,yPos+cellHeight,
          xPos-hexEdgeWidth/2,yPos+cellHeight*1.5,
          xPos+hexEdgeWidth/2,yPos+cellHeight*1.5,
          xPos+hexEdgeWidth,yPos+cellHeight,
          xPos+hexEdgeWidth/2,yPos+cellHeight/2);

    } else {
      newCell = new Polygon(
          xPos,yPos,
          xPos-hexEdgeWidth/2,yPos+cellHeight/2,
          xPos,yPos+cellHeight,
          xPos+hexEdgeWidth,yPos+cellHeight,
          xPos+0.75*cellWidth,yPos+cellHeight/2,
          xPos+hexEdgeWidth,yPos
);

    }
    if(getGridRule()){
    newCell.setStroke(Color.BLACK);}
    newCell.setFill(getColorMap().getStateMatch(getCellStateString(c)));
    return newCell;

  }

}
