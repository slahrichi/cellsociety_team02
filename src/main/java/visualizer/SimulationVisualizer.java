package visualizer;

import Model.Grid;
import Model.Simulation;
import cellsociety.Main;
import java.util.MissingResourceException;
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
 * of the grid. It depends on the <class>Main</class> to receive the necessary data. As well as the
 * <class>AnimationControlPanel</class>,<class>MenuBarControlPanel</class>,and
 * <class>GridVisualizer</class> to build the objects and functionalities necessary for the UI.
 *
 * @author Luka Mdivani
 */
public class SimulationVisualizer {

  public final String TITLE = "CellSociety";
  public final int FRAMES_PER_SECOND = 3;
  public final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  public final String DEFAULT_RESOURCE_PACKAGE = "/";
  public final String DEFAULT_LANGUAGE = "English";
  public final int GRID_WIDTH = 600;
  public final int GRID_HEIGHT = 500;
  private final int SCENE_WIDTH;
  private final int SCENE_HEIGHT;


  private final ResettableStage myStage;
  private Grid myGrid;
  private final Simulation mySimulation;
  private BorderPane root;
  private Group gridGroup;
  private GridVisualizer gv;
  private Scene scene;
  private int numColumns;
  private int numRows;
  private final Main myMain;
  private final ResourceBundle myResources;
  private final String myStyle;
  private AnimationControlPanel myAnimationPanel;
  private MenuBarControlPanel myMenuBarPanel;
  private final boolean defaultGridLineRule = true;
  private final boolean defaultCellStateDisplay = false;
  private String cellType;

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
    cellType = "Hexagon";
  }

  /**
   * Draws the <code> Scene </code> when called. Starts the animation loop.
   * <p>
   * Assumptions- For now the class assumes that the grid is rectangular, but since the abstract *
   * <code>GridVisualizer</code> superclass exists, this is easily extendable when necessary. * <p>
   */
  public void setUpScene() {
    Timeline animation = new Timeline();

    chooseGridType(cellType);
    root = new BorderPane();
    myAnimationPanel = new AnimationControlPanel(myResources, animation);
    myMenuBarPanel = new MenuBarControlPanel(myResources, myMain, myAnimationPanel, myStage);

    createUIControls();

    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myMenuBarPanel.setScene(scene);
    myMenuBarPanel.setStyleMode(myStyle);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      if (myAnimationPanel.getAnimationStatus()) {
        myAnimationPanel.step(this);
      }
    });

    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();


  }

  /**
   * method for creating all necessary UI control panels, and adding them to stage root
   */
  private void createUIControls() {
    myAnimationPanel.createAllAnimationControls(this);
    root.setBottom(myAnimationPanel.getAnimationControls());
    myMenuBarPanel.arrangeMenuComponents(this);
    root.setTop(myMenuBarPanel.getMenuBar());
  }


  /**
   * Updates the simulation object, as well the graphical grid.
   */
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

  /**
   * Toggles the gridLine display condition according to user input.
   */
  public void toggleGridLineRule() {
    gv.toggleGridRule();
    reRenderGrid();
  }

  /**
   * toggles the cell State display condition according to user input
   */
  public void toggleCellStateDisplay() {
    gv.toggleCellStateDisplay();
    reRenderGrid();
  }

  /**
   * visualizes the correct type of grid, depending on XML specifications.
   *
   * @param gridType the string representing which grid type of grid to visualize.
   */
  public void chooseGridType(String gridType) {
    switch (gridType) {
      default -> gv = new RectangleGridVisualizer(GRID_WIDTH, GRID_HEIGHT, numRows, numColumns,
          myGrid, defaultGridLineRule, defaultCellStateDisplay);
      case "Triangle" -> gv = new TriangleGridVisualizer(GRID_WIDTH, GRID_HEIGHT, numRows,
          numColumns, myGrid, defaultGridLineRule, defaultCellStateDisplay);
      case "Hexagon" -> gv = new HexagonalGridVisualizer(GRID_WIDTH, GRID_HEIGHT, numRows,
          numColumns, myGrid, defaultGridLineRule, defaultCellStateDisplay);
    }
  }

  /**
   * changes the grid type according to user input.
   *
   * @param newGridType the new type of grid user selected
   */
  public void changeGridType(String newGridType) {
    chooseGridType(newGridType);
    reRenderGrid();
  }

  /**
   * updates the resourceBundle used for GUI text.
   *
   * @param newResourceBundle new resource bundle selected by user.
   */
  public void updateLanguageForAllControlPanel(ResourceBundle newResourceBundle) {
    root.getChildren()
        .removeAll(myMenuBarPanel.getMenuBar(), myAnimationPanel.getAnimationControls());
    try {
      myAnimationPanel.setResourceBundle(newResourceBundle);
      myMenuBarPanel.setResourceBundle(newResourceBundle);
      createUIControls();
    } catch (MissingResourceException e) {
      ErrorWindow newErr = new ErrorWindow(e.getMessage() + ".\nGUI set to English by default.");
      myAnimationPanel.setResourceBundle(
          ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE));
      myMenuBarPanel.setResourceBundle(
          ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE));
      createUIControls();
    }

  }


}