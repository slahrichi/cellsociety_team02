package Controller;

import Model.Cell;
import Model.Coordinate;
import Model.GameOfLife;
import Model.Percolation;
import Model.Segregation;
import Model.Simulation;
import Model.SpreadingFire;
import Model.States;
import Model.WaTor;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLParser {

  public static final String DEFAULT_GRID = """
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 1 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      0 0 0 0 0 0 0 0 0 0
      """;
  private final String DEFAULT_SIM = "SpreadingFire";
  private final String DEFAULT_NCOLS = "10";
  private final String DEFAULT_NROWS = "10";
  private final String DEFAULT_PROB_CATCH = "1";
  private final String DEFAULT_THRESHOLD = "0.3";
  private final String DEFAULT_FISH_CHRONON = "3";
  private final String DEFAULT_SHARK_CHRONON = "6";
  private final DocumentBuilder DOCUMENT_BUILDER;
  private static Simulation CURRENT_SIMULATION;
  private static Enum[] STATE_VALUES;
  private HashMap<String, String> data;

  /**
   * Instantiates Document Builder object to do the parsing
   *
   * @throws ParserConfigurationException
   */

  public XMLParser() throws ParserConfigurationException {
    DOCUMENT_BUILDER = createDocumentBuilder();
  }

  /**
   * Opens the file if XML, throws error if else, extracts data from list of Tags, stores it and
   * returns it in a HashMap
   *
   * @param filePath String of the path of the XML file
   * @return
   * @throws Exception if DOCUEMENT_BUILDER tries to parse a non-XML file
   */
  public Map<String, String> parseXML(String filePath) throws Exception {
    if (filePath.substring(filePath.lastIndexOf('.')).equals(".xml")) {
      File XMLFile = new File(filePath);
      Document XMLDocument = DOCUMENT_BUILDER.parse(XMLFile);
      Element simulation = XMLDocument.getDocumentElement();
      data = new HashMap<>();
      for (String tag : GeneralController.TAGS) {
        if (simulation.getElementsByTagName(tag).getLength() > 0) {
          data.put(tag, simulation.getElementsByTagName(tag).item(0).getTextContent());
        }
      }
      return data;
    } else {
      throw new Exception("Non-XML files not supported");
    }
  }

  /**
   * exports current Simulation to XML file
   *
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  public void saveGrid() throws ParserConfigurationException, TransformerException {
    DocumentBuilder docBuilder = createDocumentBuilder();
    Document doc = docBuilder.newDocument();
    Element root = doc.createElement("data");
    root.appendChild(doc.createTextNode("simulation"));
    doc.appendChild(root);
    for (Map.Entry<String, String> entry : data.entrySet()) {
      Element node = doc.createElement(entry.getKey());
      if (entry.getKey() != "grid") {
        node.appendChild(doc.createTextNode(entry.getValue()));
      } else {
        node.appendChild(doc.createTextNode(gridToXML()));
      }
      root.appendChild(node);
    }
    Transformer transformer = createTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult file = new StreamResult(new File("doc/output.xml"));
    transformer.transform(source, file);
  }

  /**
   * is called inside saveGrid() when trying to write the grid to the XML file
   *
   * @return current grid setup as String to be added under the <grid> tag
   */
  private String gridToXML() {
    Map<Coordinate, Cell> currentGrid = CURRENT_SIMULATION.getGrid().getCellMap();
    String gridString = "";
    int nrows = Integer.parseInt(DEFAULT_NROWS);
    int ncols = Integer.parseInt(DEFAULT_NCOLS);
    for (int i = 0; i < nrows; i++) {
      for (int j = 0; j < ncols; j++) {
        Cell currentCell = currentGrid.get(new Coordinate(i, j));
        Enum currentState = currentCell.getCurrentState();
        int value = Arrays.asList(STATE_VALUES).indexOf(currentState);
        gridString += value + " ";
      }
      gridString += "\n";
    }
    return gridString;
  }

  /**
   * creates the specific Simulation object corresponding to the data HashMap if the data HashMap
   * does not contain some fields, the method returns an instance of SpreadingFire by default.
   *
   * @param data hashMap returned by parseXML with the Simulation's data
   * @return
   */
  public Simulation createSimulation(Map<String, String> data) {

    // returns the String simulation type or SpreadingFire by default
    String simulation = data.getOrDefault("type", DEFAULT_SIM);
    int numCols = Integer.parseInt(data.getOrDefault("numberOfColumns", DEFAULT_NCOLS));
    int numRows = Integer.parseInt(data.getOrDefault("numberOfRows", DEFAULT_NROWS));
    String allCells = data.getOrDefault("grid", DEFAULT_GRID).replaceAll("[^0-9]", "");
    int[][] cellsArray = new int[numRows][numCols];
    for (int a = 0; a < numRows * numCols; a += numCols) {
      for (int b = 0; b < numCols; b++) {
        cellsArray[a / numCols][b] = Integer.parseInt(
            allCells.substring(a, a + numCols).substring(b, b + 1));
      }
    }

    Map<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        map.put(new Coordinate(i, j), cellsArray[i][j]);
      }
    }

    switch (simulation) {
      case "GameOfLife" -> {
        CURRENT_SIMULATION = new GameOfLife(numRows,numCols, map);
        STATE_VALUES = States.GameOfLife.values();
      }
      case "SpreadingFire" -> {
        CURRENT_SIMULATION = new SpreadingFire(numRows, numCols, map,
            Double.parseDouble(data.getOrDefault("probCatch", DEFAULT_PROB_CATCH)));
        STATE_VALUES = States.SpreadingFire.values();
      }
      case "Segregation" -> {
        CURRENT_SIMULATION = new Segregation(numRows, numCols, map,
        Double.parseDouble(data.getOrDefault("threshold", DEFAULT_THRESHOLD)));
        STATE_VALUES = States.Segregation.values();
      }
      case "WaTor" -> {
        CURRENT_SIMULATION = new WaTor(numRows, numCols, map,
            Integer.parseInt(data.getOrDefault("fishChronon", DEFAULT_FISH_CHRONON)),
            Integer.parseInt(data.getOrDefault("sharkChronon", DEFAULT_SHARK_CHRONON)));
        STATE_VALUES = States.WaTor.values();
      }
      case "Percolation" -> {
        CURRENT_SIMULATION = new Percolation(numRows, numCols, map);
        STATE_VALUES = States.Percolation.values();
      }
    }

    return CURRENT_SIMULATION;
  }

  /**
   * @return DocumentBuilder object instance to be used in parseXML()
   * @throws ParserConfigurationException
   */
  private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }

  /**
   * @return Transformer object instance to be used in saveGrid()
   * @throws TransformerConfigurationException
   */
  private Transformer createTransformer() throws TransformerConfigurationException {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    return transformer;
  }
}