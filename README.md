Cell Society
====

This project implements a cellular automata simulator.

Names: Matthew Giglio, Saad Lahrichi, Luka Mdivani

### Timeline

Start Date: 01/25/2022

Finish Date: 02/07/2022

Hours Spent:

* Luka- 30-35 hours.
* Saad ~ 30 hours

### Primary Roles

* **Luka**-
    * Worked on the GUI. Wrote all the files in the visualizer package. The includes the main
      simulation visualizer with logic, grid drawing with logic, implementing all the required
      buttons/functionalities.
    * Worked on the Main.java which connects every package together, and launches the program. Also
      takes part handling functionalities like load new file, reset current file etc.
    * Wrote the css and .properties files.

* **Saad**-
  * Worked on the Controller. Main focus was on the connection between XML files and the View, 
  and XML files and the Model. Wrote different initial configuration files for every 
  simulation type implemented. Added special files to trigger known exceptions.
  * Bulk of the work was focused on reading XML files and building corresponding Simulation 
  based on provided data, while checking for file/data integrity.
  * Added feature to save currently displayed simulation to a new XML file.
  
### Resources Used

* Heavily used JavaFX documentation.
* StackOverflow
* Questions with Mentors
* [Java's XML documentation](https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html) 

### Running the Program

Main class: Main.java

Data files needed: Loads to a default XML file, can change simulation to any other file later.
File chooser automatically opens folder containing all preset configuration files.

Features implemented:

* Controller
    * Parses XML file, check for file&data integrity. If file is wrongly formatted, loads SpreadingFire by default.
    * Creates the corresponding Simulation object depending on data found in the XML file (e.g. GameOfLife, SpreadingFire,...)
    * Replaces specific variables if missing/wrongly formatted with default values depending on the type of Simulation.
    * Exports the current Simulation with all parameters (as seen in the View) to output.xml.
  
* Visualization
    * Load new models, reset current model, Open New window(with separate and fully independent
      simulation).
        * Making simulations run independently was a design choice, since it didn't mean we had to
          reduce the size of the grid when new ones were added.Making the windows independent also
          helped keep the code as closed as possible. Also, users can control the animation
          completely independently and to their liking.
    * Change between StyleSheets.
    * Change Cell type(rect,hex,tri) dynamically.
    * Selection of 3 Languages.
    * Barchart which visualizes population size.

### Notes/Assumptions

Assumptions or Simplifications:

* Controller 
  * Expects the files to be .xml. Throws an XMLException otherwise and loads SpreadingFire by default. 
  * Will not parse any tag that is not contained in the list of TAGS ("type",
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
   "style")
  * Will not create any simulation of which the type is not contained in the list of (currently supported)
  SIMULATIONS "GameOfLife",
   "SpreadingFire",
   "Percolation",
   "Segregation",
   "WaTor",
   "FallingSand",
   "RockPaperScissors")
  * Expects the "numberOfColumns", "numberOfRows", and "numberOfCells" to be Integers. Will throw XMLException if it cannot be parsed to Int.
  * Expects the "grid" to be a String representing an Int[][] with dimensions corresponding to numberOfRows and numberOfColumns, otherwise throws XMLException. 
  Depending on the Simulation type, the "grid" can String can have 0s, 1s, 2s, and sometimes 3s, depending on the number
  of states of each Simulation (e.g: GameOfLife: 0s and 1s; Percolation: 0s, 1s, and 2s; FallingSand: 0s, 1s, 2s, and 3s).
  * Expects "edgeType" to be either FINITE or TOROIDAL.
  * Expects "direction" to be either of SQUARE, TRIANGULAR, or HEXAGONAL.
  * Expects "neighborConfigs" to be a space-seperated string of Integers from 0-7 for SQUARE, 0-11 for TRIANGULAR, and 0-5 for HEXAGONAL
  * Expects "language" to be either of English, Georgian, or Arabic.
  * Expects "gridLine" and "cellState" to be True or False depending on whether the user wants to see the grid lines and each cell's current state.
  * Expects "style" to be either of LightMode or DarkMode.
  
* Visualization -
    * Doesn't make many assumptions, since the .xml files are checked in the parser. does make sure
      that .css and .properties files are valid, this is done to make sure program is functional if
      it is extended and new resource files are not written correctly.
    * If a stylesheet for a window is changed after launch by the user, the new setting is kept when
      the model is reset(same simulation reloaded), instead of reading the value again from .xml.
      This was intended as a feature by assumption that user would like to keep the preferred style
      mode. Interesting data files:

Known Bugs:

* Turning off gridlines leaves tiny(1-2 pixel) vertical(only) gaps between files, I tried to fix
  this a lot, but I think it was unavoidable.

 
Noteworthy Features:


### Impressions

* Luka - I liked working on the project. It was an interesting project, and I was doing a part I had
  never interacted with(GUI design). I learned a lot and enjoyed the process. I liked working and
  coordinating with my team, and I thought overall we were organized. I think in summary we did
  relatively well and followed class guidelines, delivering a good,easily extendable end product.
* Saad - Although I was responsible for the Controller only, I learned a lot more besides XML parsing. 
Coordinating our efforts as a team was a major learning point. That is, writing code while keeping in mind
that it must connect with other teammates' code. Making design decisions together and committing to them was another one.
I also found it very helpful to apply what we learned in class, especially writing readable, easy-to-maintain code as abstractly as possible, while keeping
in mind that we (or someone else) may want to build upon and update it in the future.