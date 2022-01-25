package Util;
import cellsociety.Main;
import Util.menuController;

public class menuControllerStep extends menuController {

  public menuControllerStep(String buttonText, double buttonPosX, double buttonPosY) {
    super(buttonText, buttonPosX, buttonPosY);
  }
  @Override
  public void handlePress(){Main.move(Main.SECOND_DELAY);   System.out.println("TESTStepCLASS");}

}
