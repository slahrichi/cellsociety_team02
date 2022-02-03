package cellsociety;

import Controller.XMLParser;
import Model.Simulation;
import Visualizer.SimulationVisualizer;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This is the starting class for the application. It initializes the simulation and the GUI when
 * the app first starts, as well as when the user decides to Change the model they want to
 * visualize, or reset the grid.
 *
 * @author Luka Mdivani
 */
public class Main extends Application {

  // useful names for constant values used

  public static final int SIZE_HORIZONTAL = 725;
  public static final int SIZE_VERTICAL = 575;
  public static String language = "English";
  public static final String DEFAULT_FILE_PATH = "doc/GameOfLifeBlinker.xml";
  private SimulationVisualizer visualizer;
  private Map<String, String> data;
  private int numCols;
  private int numRows;
  private Simulation currentSimulation;
  private String currentFilePath;
  private XMLParser parser;

  @Override
  public void start(Stage stage) throws Exception {

    parser = new XMLParser();
    currentFilePath = DEFAULT_FILE_PATH;
    extractDataStartSimulation(DEFAULT_FILE_PATH);

    startGUI(stage);

  }

  private void extractDataStartSimulation(String filePath) throws Exception {
    data = parser.parseXML(filePath);
    currentSimulation = parser.createSimulation(data);
    getNumberOfColumnAndRow();
  }

  private void startGUI(Stage stage) {

    visualizer = new SimulationVisualizer(stage, currentSimulation, SIZE_HORIZONTAL,
        SIZE_VERTICAL, numRows, numCols, this, language);
    visualizer.setUpScene();
  }

  /**
   * Called from the visualizer when a user selects a new file to load a model from.
   *
   * @param stage    the main stage of the javaFX GUI
   * @param filepath the filepath for the new file which was selected by the user to load
   * @throws Exception if the selected file is not .xml; needed for the parseXML method called in
   *                   the <code> extractDataStartSimulation() </code>
   */
  public void changeGUI(Stage stage, String filepath) throws Exception {
    currentFilePath = filepath;
    extractDataStartSimulation(currentFilePath);
    startGUI(stage);

  }

  /**
   * Called from the visualizer when a user decides to reload the current model.
   *
   * @param stage the main stage of the javaFX GUI
   * @throws Exception if the selected file is not .xml; needed for the parseXML method * called in
   *                   the <code> extractDataStartSimulation() </code>
   */
  public void resetModel(Stage stage) throws Exception {
    extractDataStartSimulation(currentFilePath);
    startGUI(stage);

  }

  /**
   * @throws ParserConfigurationException indicates a serious configuration error
   * @throws TransformerException         specifies an exceptional condition that occurred during
   *                                      the transformation process.
   */
  public void export() throws ParserConfigurationException, TransformerException {
    parser.saveGrid();
  }

  private void getNumberOfColumnAndRow() {
    numCols = Integer.parseInt(data.getOrDefault("numberOfColumns", "10"));
    numRows = Integer.parseInt(data.getOrDefault("numberOfRows", "10"));
  }
}



