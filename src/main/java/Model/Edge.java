package Model;

/**
 * class for holding enums for the various edge types a simulation grid can have
 *
 * @author Matthew Giglio
 */
public class Edge {

  /**
   * the possible edge types for the simulation
   */
  public enum EdgeType {
    FINITE,
    TOROIDAL,
    INFINITE;
  }

}
