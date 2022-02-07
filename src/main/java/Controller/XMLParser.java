package Controller;

import Model.Cell;
import Model.Coordinate;
import Model.Edge;
import Model.Edge.EdgeType;
import Model.FallingSand.FallingSand;
import Model.GameOfLife.GameOfLife;
import Model.Neighbors;
import Model.Neighbors.Direction;
import Model.Percolation.Percolation;
import Model.RockPaperScissors.RockPaperScissors;
import Model.Segregation.Segregation;
import Model.Simulation;
import Model.SpreadingFire.SpreadingFire;
import Model.States;
import Model.WaTor.WaTor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

  private static HashMap<String, String> defaultValues;
  private final DocumentBuilder DOCUMENT_BUILDER;
  private static Simulation CURRENT_SIMULATION;
  private static Enum[] STATE_VALUES;
  private Map<String, String> data;
  private Element simulation;

  /**
   * Instantiates Document Builder object to do the parsing and the defaultValues Map
   *
   * @throws ParserConfigurationException
   */

  public XMLParser() throws ParserConfigurationException {
    DOCUMENT_BUILDER = createDocumentBuilder();
    defaultValues = storeDefaultValues();
  }

  /**
   * method to create a new document builder
   *
   * @return DocumentBuilder object instance to be used in parseXML()
   * @throws ParserConfigurationException
   */
  private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
    return DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }


  /**
   * Opens the file. If no exceptions happen, extracts data from list of Tags, stores it and returns
   * it in a Map, else throws error and loads SpreadingFire by default.
   *
   * @param filePath String of the path of the XML file
   * @return
   */

  public Map<String, String> parseXML(String filePath) {
    try {
      return extractData(extractSimulation(filePath));
    } catch (XMLException e) {
      System.out.println(e.getMessage());
      return extractData(extractSimulation(defaultValues.get("filepath")));
    }
  }

  /**
   * Tries to open the file and catches for any exception. Uses default filePath in case of
   * exception
   *
   * @param filePath String of the filePath to open
   * @return The simulation Element to be used in extractData
   */
  private Element extractSimulation(String filePath) {
    try {
      File XMLFile = new File(filePath);
      Document XMLDocument = DOCUMENT_BUILDER.parse(XMLFile);
      simulation = XMLDocument.getDocumentElement();
      return simulation;
    }
    // wrong file type
    catch (Exception e) {
      System.out.println(e.getMessage());
      return extractSimulation(defaultValues.get("filepath"));
    }
  }

  /**
   * Loops over all TAGS and stores them in HashMap. Throws exception if tag is empty or
   * non-existent.
   *
   * @param simulation Element returned from extractSimulation
   * @return Map of each tag in the file to its passed value
   */
  private Map<String, String> extractData(Element simulation) {
    data = new HashMap<>();
    for (String tag : GeneralController.TAGS) {
      try {
        if (simulation.getElementsByTagName(tag).item(0) == null ||
            simulation.getElementsByTagName(tag).getLength() == 0) {
          throw new XMLException(
              "Empty/non-existing tag " + tag + " using default value \n" + defaultValues.get(tag));
        }
        data.put(tag, simulation.getElementsByTagName(tag).item(0).getTextContent());
      }
      // tag not found
      catch (XMLException e) {
        data.put(tag, defaultValues.get(tag));
        System.out.println(e.getMessage());
      }
    }
    return data;
  }

  /**
   * exports current Simulation with all its parameters to XML file
   *
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  public void saveGrid() throws ParserConfigurationException, TransformerException {
    Document doc = createDocumentBuilder().newDocument();
    Element root = doc.createElement("data");
    appendRootToDoc(doc, root);
    for (Map.Entry<String, String> entry : data.entrySet()) {
      addChildrenToNode(doc, root, entry);
    }
    transformToOutput(doc);
  }

  /**
   * helper method to append the root Element to the doc Document
   *
   * @param doc  new Document
   * @param root new Element created from doc
   */
  private void appendRootToDoc(Document doc, Element root) {
    root.appendChild(doc.createTextNode("simulation"));
    doc.appendChild(root);
  }


  /**
   * helper method to add the children to the document adds the value as is for all tags but grid
   * calls gridToXML() for the grid to convert it to one sequence of integers for easier
   * manipulation
   *
   * @param doc   Document created
   * @param root  root Element inside the doc
   * @param entry key,value Entry object with tags and their String values
   */
  private void addChildrenToNode(Document doc, Element root, Entry<String, String> entry) {
    Element node = doc.createElement(entry.getKey());
    if (!entry.getKey().equals("grid")) {
      node.appendChild(doc.createTextNode(entry.getValue()));
    } else {
      node.appendChild(doc.createTextNode(gridToXML()));
    }
    root.appendChild(node);
  }

  /**
   * create Transformer object to output the file
   *
   * @param doc Document used to create output file
   * @throws TransformerException should there be issues with the transformer
   */
  private void transformToOutput(Document doc) throws TransformerException {
    Transformer transformer = createTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult file = new StreamResult(new File("doc/output.xml"));
    transformer.transform(source, file);
  }

  /**
   * creates Transformer to be used to write to XML
   *
   * @return Transformer object instance to be used in saveGrid()
   * @throws TransformerConfigurationException
   */
  private Transformer createTransformer() throws TransformerConfigurationException {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    return transformer;
  }

  /**
   * helper method called inside saveGrid() when trying to write the grid to the XML file
   *
   * @return current grid setup as String to be added under the <grid> tag
   */
  private String gridToXML() {
    Map<Coordinate, Cell> currentGrid = CURRENT_SIMULATION.getGrid().getCellMap();
    StringBuilder gridString = new StringBuilder();
    int numRows = CURRENT_SIMULATION.getGrid().getNumRows();
    int numCols = CURRENT_SIMULATION.getGrid().getNumCols();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        Cell currentCell = currentGrid.get(new Coordinate(i, j));
        Enum currentState = currentCell.getCurrentState();
        int value = Arrays.asList(STATE_VALUES).indexOf(currentState);
        gridString.append(value).append(" ");
      }
      gridString.append("\n");
    }
    return gridString.toString();
  }


  /**
   * creates the specific Simulation object corresponding to the data Map. If there are missing
   * fields, the method returns the corresponding default value.
   *
   * @param data hMap returned by parseXML with the Simulation's data
   * @return Simulation object corresponding to the data
   */

  public Simulation createSimulation(Map<String, String> data) throws XMLException {
    int numCols;
    int numRows;
    String allCells;
    EdgeType edgeType;
    List<Integer> neighborConfig;
    Direction direction;

    try {
      if (!isParsableMandatoryInt()) {
        data.put("numberOfColumns", defaultValues.get("numberOfColumns"));
        data.put("numberOfRows", defaultValues.get("numberOfRows"));
        throw new XMLException("Invalid dimensions. Using default values.");
      }
      numCols = Integer.parseInt(data.get("numberOfColumns"));
      numRows = Integer.parseInt(data.get("numberOfRows"));
      allCells = data.get("grid").replaceAll("[^0-9]", "");
      if (shapeMismatch(numRows, numCols, allCells)) {
        data.put("grid", defaultValues.get("grid"));
        throw new XMLException("Dimensions mismatch. Grid given has " + allCells.length()
            + " cells, numberOfRows*numberOfColumns = "
            + numCols * numRows + ". Using default values instead.");
      }
    } catch (XMLException XMLe) {
      //ErrorWindow window = new ErrorWindow(XMLe.getMessage());
      numCols = Integer.parseInt(
          data.getOrDefault("numberOfColumns", defaultValues.get("numberOfColumns")));
      numRows = Integer.parseInt(
          data.getOrDefault("numberOfRows", defaultValues.get("numberOfRows")));
      allCells = data.getOrDefault("grid", defaultValues.get("grid")).replaceAll("[^0-9]", "");
      System.out.println(XMLe.getMessage());
    } finally {
      edgeType = Edge.EdgeType.valueOf(
          data.getOrDefault("edgeType", defaultValues.get("edgeType")));
      direction = Neighbors.Direction.valueOf(
          data.getOrDefault("direction", defaultValues.get("direction")));
      neighborConfig = getConfigList(
          data.getOrDefault("neighborConfig", defaultValues.get("neighborConfig")));
    }

    Map<Coordinate, Integer> map = getCoordinateIntegerMap(numCols, numRows, allCells);

    String type = getSimulation(data);
    switch (type) {
      case "GameOfLife" -> createGameOfLife(numCols, numRows, map, edgeType, direction,
          neighborConfig);
      case "SpreadingFire" -> createSpreadingFire(data, numCols, numRows, map, edgeType, direction,
          neighborConfig);
      case "Segregation" -> createSegregation(data, numCols, numRows, map, edgeType, direction,
          neighborConfig);
      case "WaTor" -> createWaTor(data, numCols, numRows, map, edgeType, direction, neighborConfig);
      case "Percolation" -> createPercolation(numCols, numRows, map, edgeType, direction,
          neighborConfig);
      case "RockPaperScissors" -> createRockPaperScissors(numCols, numRows, map, edgeType,
          direction, neighborConfig);
      case "FallingSand" -> createFallingSand(numCols, numRows, map, edgeType, direction,
          neighborConfig);

    }
    return CURRENT_SIMULATION;
  }

  /**
   * @return helper method that checks if dimensions are non-string (empty/string/..)
   */
  private Boolean isParsableMandatoryInt() {
    try {
      Integer.parseInt(data.get("numberOfColumns"));
      Integer.parseInt(data.get("numberOfRows"));
      return true;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  /**
   * helper method to check if the given grid mismatches the given numCols, numRows
   *
   * @param numCols  provided numberOfColumns
   * @param numRows  provided numberOfRows
   * @param allCells provided grid turned to a non-seperated String of all the numbers in the grid
   * @return True if mismatch
   */
  private Boolean shapeMismatch(int numCols, int numRows, String allCells) {
    return numCols * numRows != allCells.length();
  }

  /**
   * helper method to check if expected Double values are parsable
   *
   * @param tag tag of which we check the value
   * @return True if parsable to Double
   */
  private Boolean isParsableDouble(String tag) {
    try {
      Double.parseDouble(tag);
      return true;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  /**
   * helper method to check if expected Integer values are parsable
   *
   * @param tag tag of which we check the value
   * @return True if parsable to Integer
   */
  private Boolean isParsableInt(String tag) {
    try {
      Integer.parseInt(tag);
      return true;
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  /**
   * checks if a simulation additional parameters are as expected
   *
   * @param param    parameter to check
   * @param isDouble True if parameter is expected to be a Double
   */
  private void checkParameters(String param, Boolean isDouble) {
    if (simulation.getElementsByTagName(param).item(0) == null) {
      throw new XMLException(
          param + " tag non-existent/empty. Using default: " + defaultValues.get(param));
    }
    if (isDouble) {
      if (!isParsableDouble(simulation.getElementsByTagName(param).item(0).getTextContent())) {
        throw new XMLException(
            "Invalid value for " + param + " passed. Using default: " + defaultValues.get(
                param));
      }
    } else {
      if (!isParsableInt(simulation.getElementsByTagName(param).item(0).getTextContent())) {
        throw new XMLException(
            "Invalid value for " + param + " passed. Using default: " + defaultValues.get(
                param));
      }
    }
    data.put(param, simulation.getElementsByTagName(param).item(0).getTextContent());
  }

  /**
   * assign default value to the parameter
   *
   * @param param parameter to which default value is assigned
   * @param e     exception thrown when parsing param from the XML file
   */
  private void useDefault(String param, XMLException e) {
    String value = defaultValues.get(param);
    data.put(param, value);
    System.out.println(e.getMessage());
  }


  /**
   * @param neighborConfig helper method that returns the neighborConfiguration
   * @return neighborConfig tag value as List<Integer> as expected by the method.
   */
  private List<Integer> getConfigList(String neighborConfig) {
    List<Integer> config = new ArrayList<>(neighborConfig.length());
    String[] splitString = neighborConfig.split(" ");
    for (String c : splitString) {
      config.add(Integer.parseInt(c));
    }
    return config;
  }

  /**
   * creates Segregation Simulation using the processed parameters
   *
   * @param data           Map with the simulation's data
   * @param numCols        numberOfColumns
   * @param numRows        numberOfRows
   * @param map            map of the grid's coordinates and states
   * @param edgeType       type of the edges
   * @param direction      direction of the neighbors
   * @param neighborConfig configuration of the neighbors
   */
  private void createSegregation(Map<String, String> data, int numCols, int numRows,
      Map<Coordinate, Integer> map, EdgeType edgeType, Direction direction,
      List<Integer> neighborConfig) {
    try {
      checkParameters("threshold", true);
    } catch (XMLException e) {
      useDefault("threshold", e);
    } finally {
      double threshold = Double.parseDouble(data.get("threshold"));
      CURRENT_SIMULATION = new Segregation(numRows, numCols, map, edgeType, direction,
          neighborConfig, threshold);
      STATE_VALUES = States.Segregation.values();
    }
  }

  private void createWaTor(Map<String, String> data, int numCols, int numRows,
      Map<Coordinate, Integer> map, EdgeType edgeType, Direction direction,
      List<Integer> neighborConfig) {
    try {
      checkParameters("fishChronon", false);
      checkParameters("sharkChronon", false);
    } catch (XMLException e) {
      useDefault("fishChronon", e);
      useDefault("sharkChronon", e);
    } finally {
      int fishChronon = Integer.parseInt(data.get("fishChronon"));
      int sharkChronon = Integer.parseInt(data.get("sharkChronon"));
      CURRENT_SIMULATION = new WaTor(numRows, numCols, map, edgeType, direction, neighborConfig,
          fishChronon, sharkChronon);
      STATE_VALUES = States.WaTor.values();
    }
  }

  private void createPercolation(int numCols, int numRows, Map<Coordinate, Integer> map,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    CURRENT_SIMULATION = new Percolation(numRows, numCols, map, edgeType, direction,
        neighborConfig);
    STATE_VALUES = States.Percolation.values();
  }

  private void createSpreadingFire(Map<String, String> data, int numCols, int numRows,
      Map<Coordinate, Integer> map, EdgeType edgeType, Direction direction,
      List<Integer> neighborConfig) {

    try {
      checkParameters("probCatch", true);
    } catch (XMLException e) {
      useDefault("probCatch", e);
    } finally {
      double probCatch = Double.parseDouble(data.get("probCatch"));
      CURRENT_SIMULATION = new SpreadingFire(numRows, numCols, map, edgeType, direction,
          neighborConfig, probCatch);
      STATE_VALUES = States.SpreadingFire.values();
    }
  }

  private void createGameOfLife(int numCols, int numRows, Map<Coordinate, Integer> map,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    CURRENT_SIMULATION = new GameOfLife(numRows, numCols, map, edgeType, direction, neighborConfig);
    STATE_VALUES = States.GameOfLife.values();
  }

  private void createFallingSand(int numCols, int numRows, Map<Coordinate, Integer> map,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    CURRENT_SIMULATION = new FallingSand(numRows, numCols, map, edgeType, direction,
        neighborConfig);
    STATE_VALUES = States.FallingSand.values();
  }

  private void createRockPaperScissors(int numCols, int numRows, Map<Coordinate, Integer> map,
      EdgeType edgeType, Direction direction, List<Integer> neighborConfig) {
    try {
      checkParameters("thresholdRPS", false);
    } catch (XMLException e) {
      useDefault("thresholdRPS", e);
    } finally {
      int thresholdRPS = Integer.parseInt(data.get("thresholdRPS"));
      CURRENT_SIMULATION = new RockPaperScissors(numRows, numCols, map, edgeType, direction,
          neighborConfig, thresholdRPS);
      STATE_VALUES = States.RockPaperScissors.values();
    }
  }

  /**
   * returns the Simulation type String from the data Map
   *
   * @param data Map with the XML file data
   * @return Simulation type String (e.g. "GameOfLife")
   */
  private String getSimulation(Map<String, String> data) {
    String type;
    try {
      type = data.get("type");
      if (!GeneralController.SIMULATIONS.contains(type)) {
        throw new XMLException(
            "Invalid simulation type provided (" + type + ") Using default SpreadingFire");
      }
    } catch (XMLException e) {
      type = defaultValues.get("type");
      data.put("type", type);
      System.out.println(e.getMessage());
    } finally {
      type = data.getOrDefault("type", defaultValues.get("type"));
    }
    return type;
  }

  /**
   * helper method to build the coordinate integer map
   *
   * @param numCols  numberOfColumns
   * @param numRows  numberOfRows
   * @param allCells non-spaced String of the grid
   * @return Coordinate, Integer map
   */
  private Map<Coordinate, Integer> getCoordinateIntegerMap(int numCols, int numRows,
      String allCells) {
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
    return map;
  }

  /**
   * stores the default values of each tag in a hashMap
   *
   * @return HashMap with the default values of all possible tags
   */
  private HashMap<String, String> storeDefaultValues() {
    defaultValues = new HashMap<>();
    defaultValues.put("grid", """
        0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        0 0 0 1 0 1 0 0 0 0
        0 0 0 1 1 1 0 0 0 0
        0 0 0 1 0 1 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        """);
    defaultValues.put("filepath", "data/SpreadingFire1.xml");
    defaultValues.put("type", "SpreadingFire");
    defaultValues.put("numberOfRows", "10");
    defaultValues.put("numberOfColumns", "10");
    defaultValues.put("probCatch", "0.5");
    defaultValues.put("threshold", "0.3");
    defaultValues.put("fishChronon", "3");
    defaultValues.put("sharkChronon", "6");
    defaultValues.put("edgeType", "FINITE");
    defaultValues.put("direction", "SQUARE");
    defaultValues.put("neighborConfig", "0 1 2 3 4 5 6 7");
    defaultValues.put("language", "English");
    defaultValues.put("gridLine", "true");
    defaultValues.put("cellState", "false");
    defaultValues.put("thresholdRPS", "3");
    defaultValues.put("style", "LightMode");
    return defaultValues;
  }

}

