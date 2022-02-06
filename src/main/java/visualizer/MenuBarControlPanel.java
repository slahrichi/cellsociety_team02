package visualizer;

import cellsociety.Main;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This class extends the ControlPanel superclass, it is used to create the  menu bar controls,so
 * they can be displayed on the screen.
 * <p>
 * This class depends on javaFX, as well as <class>SimulationVisualizer</class> which calls it. It
 * also depends on<class>AnimationControlPanel</class> for the Pause() functionality.
 * <class>SimulationVisualizer</class> also  depend on this class since it needs the Pause()
 * method.
 *
 * @author Luka Mdivani
 */
public class MenuBarControlPanel extends ControlPanel {

  public final String DEFAULT_RESOURCE_PACKAGE = "/";


  private HBox menuBar;
  private final Main myMain;
  private final ResettableStage myStage;
  private final AnimationControlPanel myAnimationPanel;
  private Scene myScene;
  private final FileChooser fileChooser = new FileChooser();

  /**
   * The main constructor for the class, used to initialize instance variables.
   *
   * @param resources      the resourceBundle used to display texts on the UI.
   * @param main           the Main class instance associated with this MenuBar, used for
   *                       changing/resetting models.
   * @param animationPanel used to call the Pause() method before loading new files, reloading
   *                       models etc.
   * @param stage          the stage on which
   */
  public MenuBarControlPanel(ResourceBundle resources, Main main,
      AnimationControlPanel animationPanel, ResettableStage stage) {
    super(resources);
    myStage = stage;
    myMain = main;
    myAnimationPanel = animationPanel;

  }

  /**
   * @return returns the HBox object with all the controls, which is added to the root.
   */
  public HBox getMenuBar() {
    return menuBar;
  }

  /**
   * used to set the myScene instance variable, separate from constructor because it needs to be set
   * after the scene has been created, but the contractor is called before this happens.
   *
   * @param scene the scene on which the menu bar is displayed.
   */
  public void setScene(Scene scene) {
    myScene = scene;
  }

  /**
   * Creates all the menu bar control items when called, and arranges them into an HBox object.
   *
   * @param myRoot               the main root of the UI scene
   * @param simulationVisualizer the instance of the SimulationVisualizer class.
   */
  public void arrangeMenuComponents(Pane myRoot, SimulationVisualizer simulationVisualizer) {
    menuBar = new HBox();
    menuBar.getChildren()
        .addAll(createFileMenu(), createStyleMenu(), createToggleMenu(simulationVisualizer),
            createCellTypeMenu(simulationVisualizer),
            createLanguageMenu(myRoot, simulationVisualizer));


  }

  private MenuButton createFileMenu() {
    MenuItem loadButton = makeMenuItem("loadCommand", e -> changeModel());
    MenuItem resetButton = makeMenuItem("resetCommand", e -> resetGrid());
    MenuItem exportButton = makeMenuItem("exportCommand", e -> {
      try {
        exportGridToFile();
      } catch (ParserConfigurationException ex) {
        ex.printStackTrace();
      } catch (TransformerException ex) {
        ex.printStackTrace();
      }
    });
    MenuItem newUIButton = makeMenuItem("newWindowCommand", e -> openNewWindow());
    return new MenuButton(getResourceBundle().getString("settingsPrompt"), null, loadButton,
        resetButton, exportButton, newUIButton);
  }

  private void changeModel() {
    String fileName = chooseFile();

    if (!fileName.equals("")) {
      myMain.changeGUI(myStage, "doc/" + fileName);
    }

  }

  private void resetGrid() {
    myAnimationPanel.pause();
    myMain.resetModel(myStage);
    myStage.show();
  }

  private void openNewWindow() {
    String fileName = chooseFile();
    if (!fileName.equals("")) {
      myMain.startAdditionalGUI("doc/" + fileName);
    }
  }

  private void exportGridToFile() throws ParserConfigurationException, TransformerException {
    myAnimationPanel.pause();
    myMain.export();
  }

  private String chooseFile() {
    myAnimationPanel.pause();
    File XMLFile = new File("doc/");
    fileChooser.setInitialDirectory(XMLFile);
    File selectedFile = fileChooser.showOpenDialog(myStage);
    String fileName = "";
    if (selectedFile != null) {

      fileName = selectedFile.getName();
    }

    return fileName;
  }

  private MenuButton createStyleMenu() {
    MenuItem darkModeButton = makeMenuItem("darkModeCommand", e -> setStyleMode("darkMode"));
    MenuItem lightModeButton = makeMenuItem("lightModeCommand", e -> setStyleMode("lightMode"));

    return new MenuButton(getResourceBundle().getString("stylePrompt"), null, darkModeButton,
        lightModeButton);
  }

  /**
   * Sets the chosen styleSheet to the GUI Scene.
   *
   * @param styleMode the style sheet name which is to be used.
   */
  public void setStyleMode(String styleMode) {
    myScene.getStylesheets().clear();
    myScene.getStylesheets().add(
        getClass().getResource(DEFAULT_RESOURCE_PACKAGE + styleMode + ".css").toExternalForm());
    myStage.setCurrentStyle(styleMode);
  }

  private HBox createLanguageMenu(Pane myRoot, SimulationVisualizer sv) {
    Button enButton = makeButton("englishLanguageCommand", e -> setLanguage("English", myRoot, sv));
    Button kaButton = makeButton("georgianLanguageCommand",
        e -> setLanguage("Georgian", myRoot, sv));
    Button arButton = makeButton("arabicLanguageCommand", e -> setLanguage("Arabic", myRoot, sv));
    HBox result = new HBox();
    result.getChildren().addAll(enButton, kaButton, arButton);
    return result;
  }

  private void setLanguage(String language, Pane myRoot, SimulationVisualizer sv) {
    myRoot.getChildren().removeAll(menuBar, myAnimationPanel.getAnimationControls());
    setResourceBundle(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language));
    myAnimationPanel.setResourceBundle(
        ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language));
    sv.createUIControls();

  }

  private MenuButton createToggleMenu(SimulationVisualizer sv) {
    MenuItem gridToggleButton = makeMenuItem("gridCommand", e -> toggleGridLine(sv));
    MenuItem cellStateDisplayToggleButton = makeMenuItem("cellStateCommand",
        e -> toggleDisplayCellState(sv));
    return new MenuButton(getResourceBundle().getString("togglePrompt"), null, gridToggleButton,
        cellStateDisplayToggleButton);
  }

  private void toggleGridLine(SimulationVisualizer sv) {
    sv.toggleGridLineRule();
  }

  private void toggleDisplayCellState(SimulationVisualizer sv) {
    sv.toggleCellStateDisplay();
  }

  private MenuButton createCellTypeMenu(SimulationVisualizer sv) {
    MenuItem rectCellButton = makeMenuItem("rectCellCommand", e -> sv.changeGridType("Rectangle"));
    MenuItem hexCellButton = makeMenuItem("hexCellCommand", e -> sv.changeGridType("Hexagon"));
    MenuItem triCellButton = makeMenuItem("triCellCommand", e -> sv.changeGridType("Triangle"));
    return new MenuButton(getResourceBundle().getString("cellTypeChangePrompt"), null,
        rectCellButton, hexCellButton, triCellButton);
  }
}
