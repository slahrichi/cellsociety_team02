package Model;

public class States {

  public enum SpreadingFire {
    TREE, //GREEN
    BURNING, //RED
    EMPTY //BEIGE/WHITE
  }

  public enum Percolation {
    PERCOLATED, //BLUE
    OPEN, //WHITE
    BLOCKED //BLACK
  }

  public enum GameOfLife {
    ALIVE, //
    DEAD
  }

  public enum Segregation {
    EMPTY,
    REP,
    DEM
  }

  public enum WaTor {
    EMPTY,
    FISH,
    SHARK
  }

}
