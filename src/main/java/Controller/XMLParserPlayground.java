package Controller;

import Model.*;
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
  private final String DEFAULT_NCOLS = "10";
  private final String DEFAULT_NROWS = "10";
  private final String DEFAULT_PROB_CATCH = "0";
  private final String DEFAULT_THRESHOLD = "0.3";
  private final String DEFAULT_FISH_CHRONON = "3";
  private final String DEFAULT_SHARK_CHRONON = "6";
  private final DocumentBuilder DOCUMENT_BUILDER;
  private static Simulation CURRENT_SIMULATION;
  private static Enum[] STATE_VALUES;
  private static HashMap<String, String> data;

  public XMLParserPlayground() throws ParserConfigurationException {
    DOCUMENT_BUILDER = createDocumentBuilder();
  }

  public HashMap<String, String> parseXML(String filePath) throws Exception {
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

  public Simulation createSimulation(HashMap<String, String> data) {

    // returns the String simulation type or SpreadingFire by default
    String simulation = data.getOrDefault("type", "SpreadingFire");
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

      switch (simulation) {
        case "GameOfLife" -> {
          CURRENT_SIMULATION = new GameOfLife(numCols, numRows, map);
          STATE_VALUES = States.GameOfLife.values();
        }
        case "SpreadingFire" -> {
          CURRENT_SIMULATION = new SpreadingFire(numRows, numCols, map, probCatch);
          STATE_VALUES = States.SpreadingFire.values();
        }
        case "Segregation" -> {
          CURRENT_SIMULATION = new Segregation(numRows, numCols, map,
              threshold);
          STATE_VALUES = States.Segregation.values();
        }
        case "WaTor" -> {
          CURRENT_SIMULATION = new WaTor(numRows, numCols, map, fishChronon,
              sharkChronon);
          STATE_VALUES = States.WaTor.values();
        }
        case "Percolation" -> {
          CURRENT_SIMULATION = new Percolation(numRows, numCols, map);
          STATE_VALUES = States.Percolation.values();
        }
      }

      return CURRENT_SIMULATION;
    }

      private DocumentBuilder createDocumentBuilder () throws ParserConfigurationException {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private Transformer createTransformer () throws TransformerConfigurationException {
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      return transformer;
    }

    public static void main (String[]args)
      throws Exception {
      XMLParserPlayground parser = new XMLParserPlayground();
      //Hard coded for now, in the future, this would be passed by the View after the user selects a file.
      String filePath = "doc/SpreadingFire.xml";
      // String filePath = Main.getFileName();
      HashMap<String, String> data = parser.parseXML(filePath);
      String grid = data.get("grid");
      String allNumbers = grid.replaceAll("[^0-9]", "");
      // source: https://stackoverflow.com/questions/42546052/take-a-string-and-turn-it-into-a-2d-array-java
      int[][] numbers = Arrays.stream(allNumbers.split("(?<=\\G.{10})"))
          .map(s -> (Arrays.stream(s.split("(?<=\\G.{1})")).mapToInt(Integer::parseInt).toArray()))
          .toArray(int[][]::new);
      Simulation test = parser.createSimulation(data);

      parser.saveGrid();
    }

  }