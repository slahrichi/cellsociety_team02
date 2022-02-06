package visualizer;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


/**
 * Class for visualizing the bar chart of cell population.
 * <p>
 * Depends on javafx, and <code>SimulationVisualizer</code>
 *
 * @author Luka Mdivani
 */
public class DataGraph {

  public final String DEFAULT_RESOURCE_PACKAGE = "/";
  public final String DEFAULT_LANGUAGE = "English";
  private final CategoryAxis xAxis = new CategoryAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private final Map<Enum, Integer> myData;

  private int stepCount;

  /**
   * Main constructor for the class assigns instance variables.
   *
   * @param data      data of cell population
   * @param resources languages property file
   */
  public DataGraph(Map<Enum, Integer> data, ResourceBundle resources) {
    myData = data;
    ResourceBundle myResources = resources;
    try {
      xAxis.setLabel(myResources.getString("cellTypePrompt"));
      yAxis.setLabel(myResources.getString("cellCountPrompt"));
    } catch (MissingResourceException e) {
      ErrorWindow newErr = new ErrorWindow(e.getMessage() + ".\nGUI set to English by default.");
      myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
      xAxis.setLabel(myResources.getString("cellTypePrompt"));
      yAxis.setLabel(myResources.getString("cellCountPrompt"));
    }

    stepCount = 0;

  }

  /**
   * @return returns the Group object with the Graph to be added to main root.
   */
  public Group createGraph() {
    Group result = new Group();

    result.getChildren().add(buildGraph());
    return result;
  }

  private BarChart<String, Number> buildGraph() {
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    for (Enum entry : myData.keySet()) {
      series.getData().add(new XYChart.Data<>(entry.name(), myData.get(entry)));
    }
    series.setName(String.valueOf(stepCount));
    stepCount++;
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    barChart.getData().add(series);
    barChart.setMaxWidth(50);
    barChart.setMaxHeight(200);
    return barChart;
  }

}
