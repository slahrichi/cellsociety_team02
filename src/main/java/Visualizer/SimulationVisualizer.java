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
  public static final String DEFAULT_STYLE = "darkMode";
  private final int GRID_WIDTH = 600;
  private final int GRID_HEIGHT = 500;
  private final int SCENE_WIDTH;
  private final int SCENE_HEIGHT;

  private boolean animationEnabled = false;
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
  private int numColumns;
  private int numRows;
  private Main myMain;
  private ResourceBundle myResources;


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

    root.setBottom(createAllAnimationControls());
    root.setTop(createVerticalMenuControls());

    MenuItem darkModeButton = makeMenuItem("darkModeCommand", e -> setStyleMode("darkMode"));
    MenuItem lightModeButton = makeMenuItem("lightModeCommand", e -> setStyleMode("lightMode"));
    MenuButton langMenu= new MenuButton(myResources.getString("stylePrompt"),null,darkModeButton,lightModeButton);
    langMenu.setAlignment(Pos.TOP_RIGHT);
    root.setTop(langMenu);

    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    setStyleMode(DEFAULT_STYLE);

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
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

  private void setStyleMode(String styleMode) {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE +styleMode +".css").toExternalForm());
  }


  private HBox createAllAnimationControls() {
    playButton = makeButton("playCommand", e -> play());
    pauseButton = makeButton("pauseCommand", e -> pause());
    stepButton = makeButton("stepCommand", e -> step());

    Slider slider = setUpSlider();
    Text text = new Text();
    text.setFont(new Font(14));
    text.setText(myResources.getString("animationSpeedPrompt"));
    text.setId("test");

    HBox result = new HBox();
    result.getChildren().addAll(pauseButton, playButton, stepButton, text, slider);
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
    slider.valueProperty()
        .addListener((observable, oldValue, newValue) -> setAnimationSpeed(newValue));
    return slider;
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

  private Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(myResources.getString(buttonName));
    button.setOnAction(handler);

    return button;
  }

  private void play() {
    animationEnabled = true;
    animation.play();
  }

  private void pause() {
    animation.pause();
  }

  private void step() {
    updateGrid();
  }

  private void chooseFile() throws Exception {
    pause();
    File XMLFile = new File("doc/");
    fileChooser.setInitialDirectory(XMLFile);
    File selectedFile = fileChooser.showOpenDialog(myStage);
    String fileName = "";

    if (selectedFile != null) {
      fileName = selectedFile.getName();
      myMain.changeGUI(myStage, "doc/" + fileName);
    }


  }

  private void resetGrid() throws Exception {
    pause();
    myMain.resetModel(myStage);
    myStage.show();
  }

  private void setAnimationSpeed(Number factor) {
    animation.setRate(factor.doubleValue());
  }

  private void exportGridToFile() throws ParserConfigurationException, TransformerException {
    pause();
    myMain.export();
  }

  private void updateGrid() {
    mySimulation.update();
    myGrid = mySimulation.getGrid();

    root.getChildren().remove(gridGroup);
    gridGroup = gv.makeRoot();
    root.setRight(gridGroup);
    scene.setRoot(root);
    myStage.setScene(scene);
  }

}
