package cellsociety;

import Model.Grid;
import Model.Simulation;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimulationVisualizer {

  public static final String TITLE = "CellSociety";
  private final int FRAMES_PER_SECOND = 10;
  private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private final int GRID_WIDTH = 600;
  private final int GRID_HEIGHT = 500;
  private final int SCENE_WIDTH ;
  private final int SCENE_HEIGHT ;

  private boolean animationEnabled = false;
  private Circle ball;
  private KeyFrame frame;
  private Button playButton;
  private Button pauseButton;
  private Button stepButton;
  private MenuItem loadButton;
  private MenuItem resetButton;
  private MenuItem exportButton;
  private Timeline animation;
  private FileChooser fileChooser = new FileChooser();
  private Stage myStage;
  private Grid myGrid;
  private Simulation mySimulation;
  private BorderPane root;
  private Group gridGroup;
  private GridVisualizer gv;
  private Scene scene;

  public SimulationVisualizer(Stage stage, Simulation simulation,int width, int height) {
    myStage = stage;
    mySimulation=simulation;
    myGrid = simulation.getGrid();
    SCENE_WIDTH=width;
    SCENE_HEIGHT=height;
    setUpScene();
  }

  public void setUpScene() {
    ball = new Circle(450, 250, 20);
    ball.setFill(Color.LIGHTSTEELBLUE);

    gv = new RectangleGridVisualizer(GRID_WIDTH, GRID_HEIGHT, 5, 5, myGrid);

    root = new BorderPane();

    root.setBottom(createAllAnimationControls());
    root.setTop(createVerticalMenuControls());

    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    root.getChildren().add(ball);
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

    frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      if (animationEnabled) {
        step();
      }
    });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();


  }


  private HBox createAllAnimationControls() {
    playButton = makeButton("Play", e -> play());
    pauseButton = makeButton("Pause", e -> pause());
    stepButton = makeButton("Step", e -> step());

    Slider slider = setUpSlider();

    HBox result = new HBox();
    result.getChildren().addAll(pauseButton, playButton, stepButton, slider);
    result.setAlignment(Pos.CENTER);
    return result;
  }

  private Slider setUpSlider() {
    Slider slider = new Slider();
    slider.setMin(0.1);
    slider.setMax(2);
    slider.setValue(1);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.1);
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> setAnimationSpeed(newValue));
    return slider;
  }

  private MenuButton createVerticalMenuControls() {
    loadButton = makeMenuItem("Load File", e -> chooseFile());
    resetButton = makeMenuItem("Reset Grid", e -> resetGrid());
    exportButton = makeMenuItem("Export Grid (.XML)", e -> exportGridToFile());

    return new MenuButton("Settings", null, loadButton, resetButton, exportButton);
  }

  private MenuItem makeMenuItem(String itemName, EventHandler<ActionEvent> handler) {
    MenuItem item = new MenuItem();

    item.setText(itemName);
    item.setOnAction(handler);

    return item;
  }

  private Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button =  new Button();
    button.setText(buttonName);
    button.setOnAction(handler);

    return button;
  }

  private void play() {
    animationEnabled=true;
    animation.play();
  }

  private void pause() {
    animation.pause();
  }

  private void step() {
    updateGrid();
  }

  private void chooseFile() {
    File selectedFile = fileChooser.showOpenDialog(myStage);
  }

  private void resetGrid() {

    animation.stop();
    animation.playFromStart();
  }

  private void setAnimationSpeed(Number factor) {
    animation.setRate(factor.doubleValue());
  }

  private void exportGridToFile() {
  }

  private void updateGrid() {
    mySimulation.update();
    myGrid=mySimulation.getGrid();

    root.getChildren().remove(gridGroup);
    gridGroup= gv.makeRoot();
    root.setRight(gridGroup);
    scene.setRoot(root);
    myStage.setScene(scene);


  }

}
