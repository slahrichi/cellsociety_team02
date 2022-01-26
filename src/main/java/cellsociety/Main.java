package cellsociety;

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
  public static final String TITLE = "CellSociety";
  public static final int SIZE_HORIZONTAL = 700;
  public static final int SIZE_VERTICAL = 500;


  /**
   * Initialize what will be displayed.
   */
  @Override
  public void start(Stage stage) {

    SimulationVisualizer visualizer = new SimulationVisualizer(stage);

    stage.setScene(visualizer.setUpScene(SIZE_HORIZONTAL, SIZE_VERTICAL));
    stage.setTitle(TITLE);
    stage.show();


  }

}



