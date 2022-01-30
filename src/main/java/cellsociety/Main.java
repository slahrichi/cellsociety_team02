package cellsociety;

import Controller.XMLParserPlayground;
import Model.Coordinate;
import Model.Simulation;
import Visualizer.SimulationVisualizer;
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
  public static final String DEFAULT_FILE_PATH = "doc/GameOfLifeBlinker.xml";
  private SimulationVisualizer visualizer;
  private HashMap<Coordinate, Integer> setup;
  private HashMap<String, String> data;
  private int numCols;
  private int numRows;
  private Simulation test;
  private String currentFilePath;
  private XMLParserPlayground parser;

  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) throws Exception {


    parser = new XMLParserPlayground();
    currentFilePath=DEFAULT_FILE_PATH;
    extractDataStartSimulation(DEFAULT_FILE_PATH);

    startGUI(stage,parser);

  }

  private void extractDataStartSimulation(String filePath) throws Exception {
    data = parser.parseXML(filePath);

    String grid = data.get("grid");

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



