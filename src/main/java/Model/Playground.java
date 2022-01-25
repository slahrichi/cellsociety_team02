package Model;

public class Playground {

  public static void main(String[] args) {
    SpreadingFire s = new SpreadingFire(5, 5, 0.7);
    System.out.println(s.getGrid().getCellMap());
    s.update();
    System.out.println(s.getGrid().getCellMap());
    s.update();
    System.out.println(s.getGrid().getCellMap());
  }

}
