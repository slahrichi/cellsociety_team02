package visualizer;

import javafx.stage.Stage;

/**
 * The class was created with the purpose of making it easier to reset grids in multiple different
 * windows, since we need to save the use files opened in each window, to be able to reset each one
 * independently. It also saves the user choice of stylesheet to be used, and keeps in during
 * reset.
 * <p>
 * The class extends and depends on JavaFX Stage class, thus has all the same
 * functionalities.<class>Main.java</class> depends on this.
 *
 * @author Luka Mdivani
 */
public class ResettableStage extends Stage {

  private String currentFile;
  private String currentStyle;


  /**
   * Constructor for the class, initializes the instance variables with initial values.
   *
   * @param filePath the filePath associated with the stage, used to reset the class.
   * @param style    the user choice of stylesheet associated with the stage, used to not forget the
   *                 choice on model reset.
   */
  public ResettableStage(String filePath, String style) {
    super();
    currentFile = filePath;
    currentStyle = style;
  }

  /**
   * Used to keep track of the simulation file displayed on the stage, so the stage can reset to the
   * correct file.
   *
   * @param newFilePath the new model filepath chosen by the user.
   */
  public void setCurrentFile(String newFilePath) {
    currentFile = newFilePath;
  }

  /**
   * Used to keep track of the simulation file displayed on the stage.
   *
   * @return the current filepath associated with the stage.
   */
  public String getCurrentFile() {
    return currentFile;
  }

  /**
   * Used to keep track of the stylesheet file displayed on the stage, so the stage can reset to the
   * correct file.
   *
   * @param newStyle the new css filepath chosen by the user.
   */
  public void setCurrentStyle(String newStyle) {
    currentStyle = newStyle;
  }

  /**
   * Used to keep track of the styleSheet file displayed on the stage.
   *
   * @return the current css filepath associated with the stage.
   */
  public String getCurrentStyle() {
    return currentStyle;
  }


}
