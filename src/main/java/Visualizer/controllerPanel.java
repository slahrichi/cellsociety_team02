package Visualizer;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class controllerPanel {
  private ResourceBundle myResources;

  public controllerPanel(ResourceBundle resources){
    this.myResources=resources;
  }

  protected Button makeButton(String buttonName, EventHandler<ActionEvent> handler) {
    Button button = new Button();
    button.setText(myResources.getString(buttonName));
    button.setOnAction(handler);

    return button;
  }
  protected ResourceBundle getResourceBundle(){
    return myResources;
  }

}
