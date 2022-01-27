package Controller;

import Model.States.GameOfLife;
import Model.States.Segregation;
import java.util.List;

public abstract class GeneralController{

  private static String type="type";
  private static String numberOfColumns="numberOfColumns";
  private static String numberOfRows="numberOfRows";
  private static String numberOfCells="numberOfCells";
  private static String currentState="currentState";
  private static String probCatch="probCatch";

  private static String GameOfLife="GameOfLife";
  private static String SpreadingFire="SpreadingFire";
  private static String Segregation="Segregation";
  private static String WaTor="WaTor";
  private static String Percolation="Percolation";


  public static final List<String> GAMES = List.of(
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
      currentState,
      probCatch
  );

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