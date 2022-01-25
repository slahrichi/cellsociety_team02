package Util;

import Util.menuController;
import cellsociety.Main;

public class menuControllerPause extends menuController {

  public menuControllerPause(String buttonText, double buttonPosX, double buttonPosY) {
    super(buttonText, buttonPosX, buttonPosY);
  }
  @Override
  public void handlePress(){  Main.animationEnabled=false ; System.out.println("TESTPAUSECLASS");}

}
