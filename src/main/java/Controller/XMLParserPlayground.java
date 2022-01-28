package Controller;

import Model.Cell;
import Model.Coordinate;
import Model.GameOfLife;
import Model.GameOfLifeCell;
import Model.Percolation;
import Model.PercolationCell;
import Model.Segregation;
import Model.SegregationCell;
import Model.SegregationGrid;
import Model.Simulation;
import Model.SpreadingFire;
import Model.SpreadingFireCell;
import Model.States;
import Model.WaTor;
import Model.WaTorCell;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLParserPlayground {

  public static final String DEFAULT_GRID = """
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      """;
  public static final String DEFAULT_NCOLS = "10";
  public static final String DEFAULT_NROWS = "10";
  public static final String DEFAULT_PROB_CATCH = "0";
  public static final String DEFAULT_THRESHOLD = "0.3";
  public static final String DEFAULT_FISH_CHRONON = "3";
  public static final String DEFAULT_SHARK_CHRONON = "6";
  private final DocumentBuilder DOCUMENT_BUILDER;
  private String filePath = "doc/";

  public XMLParserPlayground() throws ParserConfigurationException {
    DOCUMENT_BUILDER = createDocumentBuilder();
  }

  private HashMap<String, String> parseXML(String game) throws Exception {
    if (GeneralController.SIMULATIONS.contains(game)) {
      filePath += game + ".xml";
    } else {
      throw new Exception("Game not supported");
    }
    File XMLFile = new File(filePath);
    Document XMLDocument = DOCUMENT_BUILDER.parse(XMLFile);
    Element simulation = XMLDocument.getDocumentElement();
    HashMap<String, String> data = new HashMap<>();
    for (String tag : GeneralController.TAGS) {
      data.put(tag, simulation.getElementsByTagName(tag).item(0).getTextContent());
    }
    return data;
  }

  private Cell[][] createGrid(HashMap<String, String> data) {

    // returns the String simulation type or SpreadingFire by default
    String simulation = data.getOrDefault("type", "SpreadingFire");
    Simulation CURRENT_SIMULATION;

    int numCols = Integer.parseInt(data.getOrDefault("numberOfColumns", DEFAULT_NCOLS));
    int numRows = Integer.parseInt(data.getOrDefault("numberOfRows", DEFAULT_NROWS));
    double probCatch = Double.parseDouble(data.getOrDefault("probCatch", DEFAULT_PROB_CATCH));
    double threshold = Double.parseDouble(data.getOrDefault("threshold", DEFAULT_THRESHOLD));
    int fishChronon = Integer.parseInt(data.getOrDefault("fishChronon", DEFAULT_FISH_CHRONON));
    int sharkChronon = Integer.parseInt(data.getOrDefault("sharkChronon", DEFAULT_SHARK_CHRONON));
    String grid = data.getOrDefault("grid", DEFAULT_GRID);
    String allCells = grid.replaceAll("[^0-9]", "");
    // source: https://stackoverflow.com/questions/42546052/take-a-string-and-turn-it-into-a-2d-array-java
    int[][] cellsArray = Arrays.stream(allCells.split("(?<=\\G.{10})"))
        .map(s -> (Arrays.stream(s.split("(?<=\\G.{1})")).mapToInt(Integer::parseInt).toArray()))
        .toArray(int[][]::new);

    Map<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        map.put(new Coordinate(i, j), cellsArray[i][j]);
      }
    }
    switch(simulation){
      case "GameOfLife": CURRENT_SIMULATION = new GameOfLife(numCols, numRows, map);
      break;
      case "SpreadingFire": CURRENT_SIMULATION = new SpreadingFire(numRows, numCols, probCatch);
      break;
      case "Segregation": CURRENT_SIMULATION = new Segregation(numRows, numCols, map, threshold);
      break;
      case "WaTor": CURRENT_SIMULATION = new WaTor(numRows, numCols, map, fishChronon, sharkChronon);
      break;
      case "Percolation": CURRENT_SIMULATION = new Percolation(numRows, numCols, map);
    }

    Cell[][] gridArray = new Cell[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        // need to find a way to call States.simulation where simulation is the String name of the simulation (see line 71)
        // can we add a Simulations.java class that would have an enum with all the simulations?
       // is there a way I can create a specific cell type using the string simulation
        // Current_Simulation + Cell = GameOfLifeCell
        switch(simulation){
          case "GameOfLife" : gridArray[i][j] = new GameOfLifeCell(new Coordinate(i, j),
              States.GameOfLife.values()[cellsArray[i][j]]);
          break;
          case "SpreadingFire":  gridArray[i][j] = new SpreadingFireCell(new Coordinate(i, j),
              States.SpreadingFire.values()[cellsArray[i][j]], probCatch);
          break;
//          case "Segregation":  gridArray[i][j] = new SegregationCell(new Coordinate(i, j),
//              States.Segregation.values()[cellsArray[i][j]], SegregationGrid, threshold);
//          break;
//          case "WaTor": gridArray[i][j] = new WaTorCell(new Coordinate(i, j),
//              States.WaTor.values()[cellsArray[i][j]], WaTorGrid, fishChronon, sharkChronon);
//          break;
          case "Percolation": gridArray[i][j] = new PercolationCell(new Coordinate(i, j),
              States.WaTor.values()[cellsArray[i][j]]);
        }
        // this needs to be generalized depending on the simulation
      }
    }

   return gridArray;
  }

  private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }


  private String pickSimulation(int simID) {
    return GeneralController.SIMULATIONS.get(simID);
  }

  public static void main(String[] args)
      throws Exception {
    XMLParserPlayground parser = new XMLParserPlayground();
    int simID = 1; //Hard coded for now, in the future, this would be passed by the View after the user selects a Simulation.
    // int simID = View.getSimulationID();
    HashMap<String, String> data = parser.parseXML(parser.pickSimulation(simID));
    String grid = data.get("grid");
    String allNumbers = grid.replaceAll("[^0-9]", "");
    // source: https://stackoverflow.com/questions/42546052/take-a-string-and-turn-it-into-a-2d-array-java
    int[][] numbers = Arrays.stream(allNumbers.split("(?<=\\G.{10})"))
        .map(s -> (Arrays.stream(s.split("(?<=\\G.{1})")).mapToInt(Integer::parseInt).toArray()))
        .toArray(int[][]::new);
    System.out.println(Arrays.deepToString(numbers));
    Cell[][] test = parser.createGrid(data);
    System.out.println(Arrays.deepToString(test));
  }

}