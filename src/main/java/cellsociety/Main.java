package cellsociety;

import Model.Coordinate;
import Model.Playground;
import Model.Segregation;
import Model.Simulation;
import Model.SpreadingFire;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {

  // useful names for constant values used

  public static final int SIZE_HORIZONTAL = 725;
  public static final int SIZE_VERTICAL = 575;

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
    int[][] grid = {{2, 2, 1, 2, 1}, {0, 1, 1, 1, 1}, {2, 2, 0, 0, 0},
        {2, 1, 2, 2, 2}, {2, 1, 1, 0, 1}};
    HashMap<Coordinate, Integer> setup = setupSegregation(grid);
    //Simulation seg = new Segregation(5, 5, setup, .3);
    SpreadingFire s = new SpreadingFire(5, 5, 0.7);
    SimulationVisualizer visualizer = new SimulationVisualizer(stage, s, SIZE_HORIZONTAL,
        SIZE_VERTICAL);


  }

}



