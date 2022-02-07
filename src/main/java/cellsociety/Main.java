package cellsociety;

import Controller.XMLParser;
import Model.Simulation;
import java.util.Map;
import visualizer.ErrorWindow;
import visualizer.SimulationVisualizer;
import visualizer.ResettableStage;
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


  public static final int SIZE_HORIZONTAL = 775;
  public static final int SIZE_VERTICAL = 625;
  public static final String DEFAULT_FILE_PATH = "data/GameOfLifeBlinker.xml";
  public static final String DEFAULT_STYLE = "lightMode";
  private Map<String, String> data;
  private int numCols;
  private int numRows;
  private Simulation currentSimulation;
  private XMLParser parser;

  @Override
  public void start(Stage stage) throws ParserConfigurationException {

    parser = new XMLParser();
    startAdditionalGUI(DEFAULT_FILE_PATH);


  }

  private void extractDataStartSimulation(String filePath) {

    data = parser.parseXML(filePath);

    currentSimulation = parser.createSimulation(data);
    getNumberOfColumnAndRow();
  }

  public void startAdditionalGUI(String filePath) {
    ResettableStage newStage = new ResettableStage(filePath, DEFAULT_STYLE);
    extractDataStartSimulation(filePath);
    startGUI(newStage);

  }

  private void startGUI(ResettableStage stage) {

    SimulationVisualizer visualizer = new SimulationVisualizer(stage, currentSimulation,
        SIZE_HORIZONTAL, SIZE_VERTICAL,
        numRows, numCols, this, data.get("direction"));
    visualizer.setUpScene();
  }

  /**
   * Called from the visualizer when a user selects a new file to load a model from.
   *
   * @param stage    the main stage of the javaFX GUI
   * @param filepath the filepath for the new file which was selected by the user to load
   */
  public void changeGUI(ResettableStage stage, String filepath) {
    stage.close();
    stage.setCurrentFile(filepath);
    extractDataStartSimulation(stage.getCurrentFile());
    startGUI(stage);

  }

  /**
   * Called from the visualizer when a user decides to reload the current model.
   *
   * @param stage the main stage of the javaFX GUI
   */
  public void resetModel(ResettableStage stage) {
    stage.close();
    extractDataStartSimulation(stage.getCurrentFile());
    startGUI(stage);

  }

  /**
   * @throws ParserConfigurationException indicates a serious configuration error
   * @throws TransformerException         specifies an exceptional condition that occurred during
   *                                      the transformation process.
   */
  public void export() {
    try {
      parser.saveGrid();
    } catch (ParserConfigurationException parserConfigurationException) {
      ErrorWindow newErr = new ErrorWindow(
          parserConfigurationException.getMessage());
    } catch (TransformerException transformerException) {
      ErrorWindow newErr = new ErrorWindow(
          transformerException.getMessage());
    }

  }

  private void getNumberOfColumnAndRow() {
    numCols = Integer.parseInt(data.get("numberOfColumns"));
    numRows = Integer.parseInt(data.get("numberOfRows"));
  }
}



