package Controller;

import java.util.List;

public abstract class GeneralController {

  public static final List<String> TAGS = List.of(
      "type",
      "numberOfColumns",
      "numberOfRows",
      "numberOfCells",
      "grid",
      "edgeType",
      "neighborConfig",
      "direction",
      "language",
      "gridLine",
      "cellState",
      "style"
  );

  public static final List<String> SIMULATIONS = List.of(
      "GameOfLife",
      "SpreadingFire",
      "Percolation",
      "Segregation",
      "WaTor",
      "FallingSand",
      "RockPaperScissors"
  );
}