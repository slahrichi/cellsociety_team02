package Model;

public class States {

  public enum SpreadingFire {
    TREE, //GREEN
    BURNING, //RED
    EMPTY;
    }


  public enum Percolation {
    OPEN, //WHITE
    PERCOLATED, //BLUE
    BLOCKED; //BLACK
  }

  public enum GameOfLife {
    DEAD, //
    ALIVE;
  }

  public enum Segregation {
    EMPTY,
    REP,
    DEM;
  }

  public enum WaTor {
    EMPTY,
    FISH,
    SHARK;
  }

  public enum RockPaperScissors {
    ROCK,
    PAPER,
    SCISSORS;
  }

  public enum FallingSand {
    EMPTY,
    METAL,
    SAND,
    WATER;
  }

}
