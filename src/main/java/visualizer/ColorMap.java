package visualizer;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

/**
 * Class which sets up the state-color dependencies between all available cell state types. can
 * easily be extended if a new cell type(thus new cell states) are introduced.
 * <p>
 * The <code>GridVisualizer</code> cub-classes depend on it to assign a color to a cell.
 *
 * @author Luka Mdivani
 */
public class ColorMap {

  private static Map<String, Color> COLOR_MAP = new HashMap<>();


  /**
   * initializes the ColorMap ;
   */
  public ColorMap() {
    COLOR_MAP.put("EMPTY", Color.LIGHTGRAY);

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

    COLOR_MAP.put("METAL", Color.ALICEBLUE);
    COLOR_MAP.put("SAND", Color.LIGHTYELLOW);
    COLOR_MAP.put("WATER", Color.SKYBLUE);

    COLOR_MAP.put("ROCK", Color.DARKSLATEGRAY);
    COLOR_MAP.put("PAPER", Color.WHITESMOKE);
    COLOR_MAP.put("SCISSORS", Color.RED);
  }

  /**
   * @param state the string state of the cell, used as a key to get the Color.
   * @return the corresponding color from the <code>COLOR_MAP</code>
   */
  public Color getStateMatch(String state) {
    return COLOR_MAP.get(state);
  }


}
