package visualizer;

import javafx.stage.Stage;

public class ResettableStage extends Stage {
  private String currentFile;
  private String currentStyle;


  public ResettableStage(String filePath,String style){
    super();
    currentFile = filePath;
    currentStyle= style;
  }
  public void setCurrentFile(String s){currentFile=s;}
  public String getCurrentFile() {return currentFile;}
  public void setCurrentStyle(String s){currentStyle=s;}
  public String getCurrentStyle() {return currentStyle;}


}
