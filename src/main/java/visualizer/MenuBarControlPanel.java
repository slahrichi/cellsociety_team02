package visualizer;

import cellsociety.Main;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MenuBarControlPanel extends ControlPanel {

  public static final String DEFAULT_RESOURCE_PACKAGE = "/";


  private HBox menuBar;
  private final Main myMain;
  private final ResettableStage myStage;
  private final AnimationControlPanel myAnimationPanel;
  private Scene myScene;
  private final FileChooser fileChooser = new FileChooser();

  public MenuBarControlPanel(ResourceBundle resources, Main main,
      AnimationControlPanel animationPanel, ResettableStage stage) {
    super(resources);
    myStage = stage;
    myMain = main;
    myAnimationPanel = animationPanel;

  }

  public HBox getMenuBar() {
    return menuBar;
  }

  public void setScene(Scene scene) {
    myScene = scene;
  }

  public void arrangeMenuComponents(BorderPane myRoot, SimulationVisualizer sv) {
    menuBar = new HBox();
    menuBar.getChildren()
        .addAll(createFileMenu(), createStyleMenu(), createToggleMenu(sv), createCellTypeMenu(sv),
            createLanguageMenu(myRoot, sv));


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

  public void setStyleMode(String styleMode) {
    myScene.getStylesheets().clear();
    myScene.getStylesheets().add(
        getClass().getResource(DEFAULT_RESOURCE_PACKAGE + styleMode + ".css").toExternalForm());
    myStage.setCurrentStyle(styleMode);
  }

  private HBox createLanguageMenu(BorderPane myRoot, SimulationVisualizer sv) {
    Button enButton = makeButton("englishLanguageCommand", e -> setLanguage("English", myRoot, sv));
    Button kaButton = makeButton("georgianLanguageCommand",
        e -> setLanguage("Georgian", myRoot, sv));
    Button arButton = makeButton("arabicLanguageCommand", e -> setLanguage("Arabic", myRoot, sv));
    HBox result = new HBox();
    result.getChildren().addAll(enButton, kaButton, arButton);
    return result;
  }

  private void setLanguage(String language, BorderPane myRoot, SimulationVisualizer sv) {
    myRoot.getChildren().removeAll(menuBar, myAnimationPanel.getAnimationControls());
    setResourceBundle(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language));
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
    MenuItem rectCellButton = makeMenuItem("rectCellCommand", e -> sv.changeCellType("Rectangle"));
    MenuItem hexCellButton = makeMenuItem("hexCellCommand", e -> sv.changeCellType("Hexagon"));
    MenuItem triCellButton = makeMenuItem("triCellCommand", e -> sv.changeCellType("Triangle"));
    return new MenuButton(getResourceBundle().getString("cellTypeChangePrompt"), null, rectCellButton,
        hexCellButton, triCellButton);
  }
}
