package Model;

import java.util.HashMap;

public class Playground {

  public HashMap<Coordinate, Integer> setupPercolation(int rows, int cols) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
      for(int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          Coordinate c = new Coordinate(i, j);
          int state = 0;
          if (i == 0 && j == 2) state = 1;
          else if (i == 1 && j == 2) state = 2;
          map.put(c, state);
      }
    }
      return map;
  }

  public HashMap<Coordinate, Integer> setupGame(int rows, int cols) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
    for(int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Coordinate c = new Coordinate(i, j);
        int state = 0;
        if ((i == 2 && j == 2) || (i == 2 && j == 1) || (i == 2 && j == 3)) state = 1;
        map.put(c, state);
      }
    }
    return map;
  }

  private HashMap<Coordinate, Integer> setupSpreadingFire(int rows, int cols) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Coordinate coord = new Coordinate(i, j);
        int state = 0;
        if (i == rows / 2 && j == cols / 2)
          state = 1;
        map.put(coord, state);
      }
    }
    return map;
  }

  public void testSpreadingFire(Playground p) {
    HashMap<Coordinate, Integer> setup = p.setupSpreadingFire(5, 5);
    SpreadingFire s = new SpreadingFire(5, 5, setup, 0.7);
    System.out.println(s.getGrid().getCellMap());
    s.update();
    System.out.println(s.getGrid().getCellMap());
    s.update();
    System.out.println(s.getGrid().getCellMap());
  }

  private void testGameOfLife(Playground p) {
    Simulation game = new GameOfLife(5, 5,
        p.setupGame(5, 5));
    System.out.println(game.getGrid().getCellMap());
    game.update();
    System.out.println(game.getGrid().getCellMap());
    game.update();
    System.out.println(game.getGrid().getCellMap());
  }

  private void testPercolation(Playground p) {
    HashMap<Coordinate, Integer> setup = p.setupPercolation(5, 5);
    System.out.println(setup.size());
    Simulation perc = new Percolation(5, 5, setup);
    System.out.println(perc.getGrid().getCellMap());
    for (int i = 0; i < 10; i++) {
      perc.update();
    }
    System.out.println(perc.getGrid().getCellMap());
  }

  private HashMap<Coordinate, Integer> setupSegregation(int[][] grid) {
    HashMap<Coordinate, Integer> map = new HashMap<>();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        Coordinate p = new Coordinate(i, j);
        map.put(p, grid[i][j]);
      }
    }
    return map;
  }

  private void testSegregation(Playground p){
    int[][] grid = {{2, 2, 1, 2, 1}, {0, 1, 1, 1, 1}, {2, 2, 0, 0, 0},
        {2, 1, 2, 2, 2}, {2, 1, 1, 0, 1}};
    HashMap<Coordinate, Integer> setup = setupSegregation(grid);
    Simulation seg = new Segregation(5, 5, setup, .3);
    System.out.println(seg.getGrid().getCellMap());
    seg.update();
    System.out.println(seg.getGrid().getCellMap());
    for (int i = 0; i < 10; i++) {
      seg.update();
    }
  }

  public static void main(String[] args) {
    Playground p = new Playground();
    //p.testSpreadingFire(p);
    //p.testGameOfLife(p);
    //p.testPercolation(p);
    //p.testSegregation(p);
  }




}
