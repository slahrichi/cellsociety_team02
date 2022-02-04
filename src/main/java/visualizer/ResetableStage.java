package visualizer;

import javafx.stage.Stage;

public class ResetableStage extends Stage {
  private String currentFile;
  public ResetableStage(String filePath){
    currentFile = filePath;
  }
  public void setCurrentFile(String s){currentFile=s;}
  public String getCurrentFile() {return currentFile;}


}
