package cellsociety;

import Controller.XMLParserPlayground;
import Model.Coordinate;
import Model.Segregation;
import Model.Simulation;
import Model.SpreadingFire;
import java.util.Arrays;
import java.util.HashMap;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used

  public static final int SIZE_HORIZONTAL = 725;
  public static final int SIZE_VERTICAL = 575;
  public static String  language = "English";
  public static final String DEFAULT_FILE_PATH = "doc/SpreadingFire.xml";
  private SimulationVisualizer visualizer;
  private HashMap<Coordinate, Integer> setup;
  private HashMap<String, String> data;
  private int numCols;
  private int numRows;
  private Simulation test;
  private String currentFilePath;
  private XMLParserPlayground parser;

  private HashMap<Coordinate, Integer> setupSegregation(int[][] grid) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        Coordinate p = new Coordinate(i, j);
        map.put(p, grid[i][j]);
      }
    }
    return map;
  }


  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) throws Exception {


   //int[][] grid = {{2, 2, 1, 2, 1}, {0, 1, 1, 1, 1}, {2, 2, 0, 0, 0},
   //    {2, 1, 2, 2, 2}, {2, 1, 1, 0, 1}};
   // setup = setupSegregation(grid);

   parser = new XMLParserPlayground();
    currentFilePath=DEFAULT_FILE_PATH;
    extractDataStartSimulation(DEFAULT_FILE_PATH);

    startGUI(stage,parser);

  }

  private void extractDataStartSimulation(String filePath) throws Exception {
    data = parser.parseXML(filePath);

    String grid = data.get("grid");
    String allNumbers = grid.replaceAll("[^0-9]", "");
    //// source: https://stackoverflow.com/questions/42546052/take-a-string-and-turn-it-into-a-2d-array-java
    int[][] numbers = Arrays.stream(allNumbers.split("(?<=\\G.{10})"))
        .map(s -> (Arrays.stream(s.split("(?<=\\G.{1})")).mapToInt(Integer::parseInt).toArray()))
        .toArray(int[][]::new);
    test = parser.createSimulation(data);
    getNumberOfColumnAndRow();
  }

  public  void startGUI(Stage stage, XMLParserPlayground parser){

    visualizer = new SimulationVisualizer(stage, test, SIZE_HORIZONTAL,
        SIZE_VERTICAL,numRows,numCols,this,language,parser);
  }

  public void changeGUI(Stage stage, String filepath) throws Exception {
    currentFilePath=filepath;
    extractDataStartSimulation(currentFilePath);
    visualizer = new SimulationVisualizer(stage,test, SIZE_HORIZONTAL,
        SIZE_VERTICAL,numRows,numCols,this,language,parser);

  }
  public void resetModel(Stage stage) throws Exception {
    extractDataStartSimulation(currentFilePath);
    visualizer = new SimulationVisualizer(stage,test, SIZE_HORIZONTAL,
        SIZE_VERTICAL,numRows,numCols,this,language,parser);

  }

  public void export() throws ParserConfigurationException, TransformerException {parser.saveGrid();}

  private void getNumberOfColumnAndRow(){
     numCols = Integer.parseInt(data.get("numberOfColumns"));
     numRows = Integer.parseInt(data.get("numberOfRows"));
  }
}



