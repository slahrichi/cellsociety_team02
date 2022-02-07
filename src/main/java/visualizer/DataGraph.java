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
  private final XYChart.Series<String, Number> series = new XYChart.Series<>();

  private int stepCount;

  /**
   * Main constructor for the class assigns instance variables.
   *
   * @param resources languages property file
   */
  public DataGraph(ResourceBundle resources) {
    ResourceBundle myResources = resources;
    xAxis.setId("graphText");
    yAxis.setId("graphText");
    try {
      xAxis.setLabel(myResources.getString("cellTypePrompt"));
      yAxis.setLabel(myResources.getString("cellCountPrompt"));
      series.setName(myResources.getString("stepCountPrompt") + stepCount);
    } catch (MissingResourceException e) {
      ErrorWindow newErr = new ErrorWindow(e.getMessage() + ".\nGUI set to English by default.");
      myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
      xAxis.setLabel(myResources.getString("cellTypePrompt"));
      yAxis.setLabel(myResources.getString("cellCountPrompt"));
      series.setName(myResources.getString("stepCountPrompt") + stepCount);
    }

    stepCount = 0;

  }


  /**
   * @param data cell population data used to draw graph
   * @return returns the Group object with the Graph to be added to main root.
   */
  public Group createGraph(Map<Enum, Integer> data) {

    Group result = new Group();

    result.getChildren().add(buildGraph(data));
    return result;
  }

  private BarChart<String, Number> buildGraph(Map<Enum, Integer> myData) {
    series.getData().clear();
    for (Enum entry : myData.keySet()) {
      series.getData().add(new XYChart.Data<>(entry.name(), myData.get(entry)));
    }
    stepCount++;
    String name = series.getName();
    series.setName(
        name.substring(0, name.length() - (String.valueOf(stepCount).length())) + stepCount);
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    barChart.getData().add(series);
    barChart.setMaxWidth(50);
    barChart.setMaxHeight(200);
    return barChart;
  }

}
