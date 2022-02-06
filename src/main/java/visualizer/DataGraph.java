package visualizer;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class DataGraph {
  private final CategoryAxis xAxis = new CategoryAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private final Map<Enum, Integer> myData;

  private int stepCount;

  public DataGraph(Map<Enum, Integer> data,ResourceBundle resources) {
    myData = data;

    xAxis.setLabel(resources.getString("cellTypePrompt"));

    yAxis.setLabel(resources.getString("cellCountPrompt"));

    stepCount = 0;

  }
  public Group createGraph(){
    Group result= new Group();

    result.getChildren().add(buildGraph());
    return result;
  }
  private BarChart<String,Number> buildGraph() {
    XYChart.Series<String,Number> dataSeries1 = new XYChart.Series<>();
    for (Enum entry : myData.keySet()) {
      dataSeries1.getData().add(new XYChart.Data<>(entry.name(), myData.get(entry)));
    }
    dataSeries1.setName(String.valueOf(stepCount));
    stepCount++;
    BarChart<String, Number>  barChart = new BarChart<>(xAxis, yAxis);
    barChart.getData().add(dataSeries1);
    barChart.setMaxWidth(50);
    barChart.setMaxHeight(200);
    return barChart;
  }

}
