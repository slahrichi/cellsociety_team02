package Util;


import Util.menuController;
import cellsociety.Main;

public class menuControllerPlay extends menuController {

  public menuControllerPlay(String buttonText, double buttonPosX, double buttonPosY) {
    super(buttonText, buttonPosX, buttonPosY);
  }
@Override
  public void handlePress(){Main.animationEnabled=true ; System.out.println("TESTPLAYCLASS");}

}
