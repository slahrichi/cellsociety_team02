package visualizer;

import Model.Grid;
import Model.Simulation;
import cellsociety.Main;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * This class creates the GUI to visualize the simulation process.It arranges the scene with all the
 * buttons and their functionalities, as well as the grid.
 * <p>
 * <p>
 * Dependencies-  The class depends on the relevant <class> Simulation.java</class> class, which is
 * fed to the relevant<class> GridVisualizer</class> subclass to build the graphical interpretation
 * of the grid. It depends on the <class>Main</class> to receive the necessary data.
 *
 * @author Luka Mdivani
 */
public class SimulationVisualizer {

  public static final String TITLE = "CellSociety";
  private final int FRAMES_PER_SECOND = 3;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  public static final String DEFAULT_RESOURCE_PACKAGE = "/";
  public static final String DEFAULT_LANGUAGE = "English";
  private final int GRID_WIDTH = 600;
  private final int GRID_HEIGHT = 500;
  private final int SCENE_WIDTH;
  private final int SCENE_HEIGHT;


  private Timeline animation;
  private ResettableStage myStage;
  private Grid myGrid;
  private Simulation mySimulation;
  private BorderPane root;
  private Group gridGroup;
  private GridVisualizer gv;
  private Scene scene;
  private int numColumns;
  private int numRows;
  private Main myMain;
  private ResourceBundle myResources;
  private String myStyle;
  private animationControlPanel myAnimationPanel;
  private menuBarControlPanel myMenuBarPanel;

  /**
   * Constructor for the visualizer assigns the passed in data to instance variables.
   *
   * @param stage      main stage for the GUI
   * @param simulation the simulation object initialized from the data in selected xml file.
   * @param width      width of the GUI scene
   * @param height     height of the GUI scene
   * @param rows       number of rows in the model grid
   * @param columns    number of columns in the model grid
   * @param main       the instance of <class> Main.java </class>, used to call the file change or
   *                   reset methods.
   */
  public SimulationVisualizer(ResettableStage stage, Simulation simulation, int width, int height,
      int rows, int columns, Main main) {
    myStage = stage;
    mySimulation = simulation;
    myGrid = simulation.getGrid();
    SCENE_WIDTH = width;
    SCENE_HEIGHT = height;
    numColumns = columns;
    numRows = rows;
    myMain = main;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
    myStyle = stage.getCurrentStyle();
  }

  /**
   * Draws the <code> Scene </code> when called. Starts the animation loop.
   * <p>
   * Assumptions- For now the class assumes that the grid is rectangular, but since the abstract *
   * <code>GridVisualizer</code> superclass exists, this is easily extendable when necessary. * <p>
   */
  public void setUpScene() {
    animation = new Timeline();

    gv = new HexagonalGridVisualizer(GRID_WIDTH, GRID_HEIGHT, numRows, numColumns, myGrid);
    root = new BorderPane();
    myAnimationPanel = new animationControlPanel(myResources, animation, this);
    myMenuBarPanel = new menuBarControlPanel(myResources, myMain, myAnimationPanel, myStage);

    createUIControls();

    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

    myMenuBarPanel.setStyleMode(myStyle, scene);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      if (myAnimationPanel.getAnimationStatus()) {
        myAnimationPanel.step();
      }
    });

    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();


  }

  public void createUIControls() {
    myAnimationPanel.createAllAnimationControls();
    root.setBottom(myAnimationPanel.getAnimationControls());
    myMenuBarPanel.arrangeMenuComponents(root, this, scene);
    root.setTop(myMenuBarPanel.getMenuBar());
  }


  public void updateGrid() {
    mySimulation.update();
    myGrid = mySimulation.getGrid();

    reRenderGrid();
  }

  private void reRenderGrid() {
    root.getChildren().remove(gridGroup);
    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene.setRoot(root);
    myStage.setScene(scene);
  }


}