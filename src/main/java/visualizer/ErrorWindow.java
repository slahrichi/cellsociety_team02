package visualizer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Simple Class to display pop-up exception message when needed. Depends on the JavaFX Alert Class.
 * @author Luka Mdivani
 */
public class ErrorWindow extends Alert {

  /**
   * Constructor for the class.
   * @param s string message to be displayed on the pop-up window
   * @param buttonTypes add custom buttons to the error window if needed.
   */
  public ErrorWindow(String s,
      ButtonType... buttonTypes) {
    super(AlertType.ERROR, s, buttonTypes);
    this.show();
  }


}
