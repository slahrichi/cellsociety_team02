package Util;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import cellsociety.Main;
import javafx.scene.control.Button;

public abstract class menuController {
      private Button button;
      public menuController(String buttonText, double buttonPosX, double buttonPosY) {
            button = new Button();
            button.setText(buttonText);
            button.setTranslateX(buttonPosX);
            button.setTranslateY(buttonPosY);
            button.setOnAction(e->handlePress());

      }
      public Button getButtonNode(){return button;}

      public abstract void handlePress();

}
