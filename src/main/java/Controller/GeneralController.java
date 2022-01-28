package Controller;

import java.util.List;

public abstract class GeneralController {

  private static final String type = "type";
  private static final String numberOfColumns = "numberOfColumns";
  private static final String numberOfRows = "numberOfRows";
  private static final String numberOfCells = "numberOfCells";
  private static final String probCatch = "probCatch";
  private static final String grid = "grid";

  private static final String GameOfLife = "GameOfLife";
  private static final String SpreadingFire = "SpreadingFire";
  private static final String Segregation = "Segregation";
  private static final String WaTor = "WaTor";
  private static final String Percolation = "Percolation";

  // can I change this List<String> to List<Simulation>?

  public static final List<String> SIMULATIONS = List.of(
      GameOfLife,
      SpreadingFire,
      Segregation,
      WaTor,
      Percolation
  );

  public static final List<String> TAGS = List.of(
      type,
      numberOfColumns,
      numberOfRows,
      numberOfCells,
      probCatch,
      grid
  );

//  public static void step (double elapsedTime){
//    Simulation.update();
//    View.update();
//    }
//  }

/*
  //List of all possible simulations. Set the type to SimulationXml to avoid confusion with the
  //Model.Simulation objects as SimulationXml only returns the XML file of each Simulation.

  private static final List<SimulationXml> list = POSSIBLE_SIMULATIONS = List.of(
      new GameOfLifeXml(),
      new SpreadingOfFireXml(),
      new SegregationXml(),
      new PredatorPrey(),
      new Percolation()
      );


  // This is similar to the Roulette code. Here I assume that the View
  // would prompt the user to pick a Simulation type, and pass to pickSimulation
  // the corresponding number in order of apparition in the project's description.

  private SimulationXml pickSimulation(int whichSimulation){
    return POSSIBLE_SIMULATIONS.get(whichSimulation);
  }

 */
}