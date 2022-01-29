package cellsociety;

import Model.Coordinate;
import Model.Segregation;
import Model.Simulation;
import Model.SpreadingFire;
import java.util.HashMap;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used

  public static final int SIZE_HORIZONTAL = 725;
  public static final int SIZE_VERTICAL = 575;
  private SimulationVisualizer visualizer;

  private HashMap<Coordinate, Integer> setupSegregation(int[][] grid) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        Coordinate p = new Coordinate(i, j);
        map.put(p, grid[i][j]);
      }
    }
    return map;
  }


  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {

    startGUI(stage);


  }
  public  void startGUI(Stage stage){
    int[][] grid = {{2, 2, 1, 2, 1}, {0, 1, 1, 1, 1}, {2, 2, 0, 0, 0},
        {2, 1, 2, 2, 2}, {2, 1, 1, 0, 1}};
    HashMap<Coordinate, Integer> setup = setupSegregation(grid);
    Simulation seg = new Segregation(5, 5, setup, .3);

    visualizer = new SimulationVisualizer(stage, seg, SIZE_HORIZONTAL,
        SIZE_VERTICAL,5,5,this,"English");
  }

  public void changeGUI(Stage stage){
    SpreadingFire s = new SpreadingFire(5, 5, 0.7);
    visualizer = new SimulationVisualizer(stage, s, SIZE_HORIZONTAL,
        SIZE_VERTICAL,5,5,this,"English");

  }

}



