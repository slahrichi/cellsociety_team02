package Visualizer;

import Model.Grid;
import Model.Simulation;
import cellsociety.Main;
import java.io.File;
import java.util.ResourceBundle;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
  private final int GRID_WIDTH = 600;
  private final int GRID_HEIGHT = 500;
  private final int SCENE_WIDTH;
  private final int SCENE_HEIGHT;



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
  private int numColumns;
  private int numRows;
  private Main myMain;
  private ResourceBundle myResources;
  private animationController ac;


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
   * @param language   the selected language by the user.
   */
  public SimulationVisualizer(Stage stage, Simulation simulation, int width, int height, int rows,
      int columns, Main main, String language) {
    myStage = stage;
    mySimulation = simulation;
    myGrid = simulation.getGrid();
    SCENE_WIDTH = width;
    SCENE_HEIGHT = height;
    numColumns = columns;
    numRows = rows;
    myMain = main;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }

  /**
   * Draws the <code> Scene </code> when called. Starts the animation loop.
   * <p>
   * Assumptions- For now the class assumes that the grid is rectangular, but since the abstract *
   * <code>GridVisualizer</code> superclass exists, this is easily extendable when necessary. * <p>
   */
  public void setUpScene() {

    gv = new RectangleGridVisualizer(GRID_WIDTH, GRID_HEIGHT, numRows, numColumns, myGrid);
    root = new BorderPane();
    animation = new Timeline();
    ac= new animationController(myResources,animation,this);
    root.setBottom(ac.getAnimationController());
    root.setTop(createVerticalMenuControls());

    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      if (ac.getAnimationStatus()) {
        ac.step();
      }
    });

    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    myStage.setScene(scene);
    myStage.setTitle(TITLE);
    myStage.show();


  }





  private MenuButton createVerticalMenuControls() {
    loadButton = makeMenuItem("loadCommand", e -> {
      try {
        chooseFile();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    resetButton = makeMenuItem("resetCommand", e -> {
      try {
        resetGrid();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    exportButton = makeMenuItem("exportCommand", e -> {
      try {
        exportGridToFile();
      } catch (ParserConfigurationException ex) {
        ex.printStackTrace();
      } catch (TransformerException ex) {
        ex.printStackTrace();
      }
    });

    return new MenuButton(myResources.getString("settingsPrompt"), null, loadButton, resetButton,
        exportButton);
  }

  private MenuItem makeMenuItem(String itemName, EventHandler<ActionEvent> handler) {
    MenuItem item = new MenuItem();

    item.setText(myResources.getString(itemName));
    item.setOnAction(handler);

    return item;
  }





  private void chooseFile() throws Exception {
    ac.pause();
    File XMLFile = new File("doc/");
    fileChooser.setInitialDirectory(XMLFile);
    File selectedFile = fileChooser.showOpenDialog(myStage);
    String fileName = "";

    if (selectedFile != null) {
      fileName = selectedFile.getName();
    }

    myMain.changeGUI(myStage, "doc/" + fileName);
  }

  private void resetGrid() throws Exception {
    ac.pause();
    myMain.resetModel(myStage);
    myStage.show();
  }



  private void exportGridToFile() throws ParserConfigurationException, TransformerException {
    ac.pause();
    myMain.export();
  }

  public void updateGrid() {
    mySimulation.update();
    myGrid = mySimulation.getGrid();

    root.getChildren().remove(gridGroup);
    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene.setRoot(root);
    myStage.setScene(scene);


  }

}
