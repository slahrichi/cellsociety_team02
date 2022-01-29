package Model;

public class States {

  public enum SpreadingFire {
    TREE, //GREEN
    BURNING, //RED
    EMPTY;

    public int stateToInt(Enum state) {
      if (state.equals(TREE)) return 0;
      else if (state.equals(BURNING)) return 1;
      else if (state.equals(EMPTY)) return 2;
      else throw new IllegalArgumentException("Not a valid state");
      }
    }


  public enum Percolation {
    OPEN, //WHITE
    PERCOLATED, //BLUE
    BLOCKED; //BLACK
    public int stateToInt(Enum state) {
      if (state.equals(OPEN)) return 0;
      else if (state.equals(PERCOLATED)) return 1;
      else if (state.equals(BLOCKED)) return 2;
      else throw new IllegalArgumentException("Not a valid state");
    }
  }

  public enum GameOfLife {
    DEAD, //
    ALIVE;
    public int stateToInt(Enum state) {
      if (state.equals(DEAD)) return 0;
      else if (state.equals(ALIVE)) return 1;
      else throw new IllegalArgumentException("Not a valid state");
    }
  }

  public enum Segregation {
    EMPTY,
    REP,
    DEM;
    public int stateToInt(Enum state) {
      if (state.equals(EMPTY)) return 0;
      else if (state.equals(REP)) return 1;
      else if (state.equals(DEM)) return 2;
      else throw new IllegalArgumentException("Not a valid state");
    }
  }

  public enum WaTor {
    EMPTY,
    FISH,
    SHARK;
    public int stateToInt(Enum state) {
      if (state.equals(EMPTY)) return 0;
      else if (state.equals(FISH)) return 1;
      else if (state.equals(SHARK)) return 2;
      else throw new IllegalArgumentException("Not a valid state");
    }
  }

}
