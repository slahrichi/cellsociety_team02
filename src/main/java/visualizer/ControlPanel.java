package visualizer;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * This is a super class which has methods needed by all other control panel objects, mainly used
 * for button/other interactive item creation.
 * <p>
 * This class depends on javaFX, as well as the SimulationVisualizer class, it is not used outside
 * its subclasses.
 * <p>
 * The classes like <class>MenuBarControlPanel</class> and <class>AnimationControlPanel</class>
 * extend and depend on this class
 *
 * @author Luka Mdivani
 */
public class ControlPanel {

  private ResourceBundle myResources;

  /**
   * Constructor for the Class. Initializes the resourceBundle to be used.Not called outside of
   * subclasses
   *
   * @param resources the ResourceBundle to be used in all subclasses. Used to display text on UI.
   */
  public ControlPanel(ResourceBundle resources) {
    this.myResources = resources;
  }

  protected Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(myResources.getString(buttonName));
    button.setOnAction(handler);

    return button;
  }

  protected MenuItem makeMenuItem(String itemName, EventHandler<ActionEvent> handler) {
    MenuItem item = new MenuItem();

    item.setText(myResources.getString(itemName));
    item.setOnAction(handler);

    return item;
  }

  protected ResourceBundle getResourceBundle() {
    return myResources;
  }

  protected void setResourceBundle(ResourceBundle newResourceBundle) {
    myResources = newResourceBundle;
  }

}