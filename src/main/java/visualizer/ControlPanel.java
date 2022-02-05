package visualizer;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class ControlPanel {

  private ResourceBundle myResources;

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