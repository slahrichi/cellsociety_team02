package Controller;

import java.util.List;

public abstract class GeneralController {

  // These could be changed to an enum later.

  private static final String type = "type";
  private static final String numberOfColumns = "numberOfColumns";
  private static final String numberOfRows = "numberOfRows";
  private static final String numberOfCells = "numberOfCells";
  private static final String probCatch = "probCatch";
  private static final String grid = "grid";
  private static final String threshold = "threshold";
  private static final String fishChronon = "fishChronon";
  private static final String sharkChronon = "sharkChronon";


  public static final List<String> TAGS = List.of(
      type,
      numberOfColumns,
      numberOfRows,
      numberOfCells,
      probCatch,
      threshold,
      fishChronon,
      sharkChronon,
      grid
  );
}