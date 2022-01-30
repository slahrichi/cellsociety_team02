package Visualizer;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

public class ColorMap {

  private static Map<String, Color> COLOR_MAP = new HashMap<>();


  public ColorMap() {
    COLOR_MAP.put("EMPTY", Color.MINTCREAM);

    COLOR_MAP.put("TREE", Color.GREEN);
    COLOR_MAP.put("BURNING", Color.CRIMSON);

    COLOR_MAP.put("PERCOLATED", Color.YELLOW);
    COLOR_MAP.put("OPEN", Color.ROSYBROWN);
    COLOR_MAP.put("BLOCKED", Color.DARKORANGE);

    COLOR_MAP.put("ALIVE", Color.PURPLE);
    COLOR_MAP.put("DEAD", Color.GRAY);

    COLOR_MAP.put("REP", Color.RED);
    COLOR_MAP.put("DEM", Color.BLUE);

    COLOR_MAP.put("FISH", Color.CYAN);
    COLOR_MAP.put("SHARK", Color.NAVY);

  }

  public Color getStateMatch(String state) {
    return COLOR_MAP.get(state);
  }


}
