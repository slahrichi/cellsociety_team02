Cell Society
====

This project implements a cellular automata simulator.

Names:

### Timeline

Start Date:

Finish Date: 02/06/2022

Hours Spent:

* Luka- 30-35 hours.

### Primary Roles

* **Luka**-
    * Worked on the GUI. Wrote all the files in the visualizer package. The includes the main
      simulation visualizer with logic, grid drawing with logic, implementing all the required
      buttons/functionalities.
    * Worked on the Main.java which connects every package together, and launches the program. Also
      takes part handling functionalities like load new file, reset current file etc.
    * Wrote the css and .properties files.

### Resources Used

* Heavily used JavaFX documentation.
* StackOverflow
* Questions with Mentor
*

### Running the Program

Main class: Main.java

Data files needed: Loads to a default XML file, can change simulation to any other file later.

Features implemented:

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
  this a lot, but I think it was unavoidable. Noteworthy Features:

### Impressions

* Luka - I liked working on the project. It was an interesting project, and I was doing a part I had
  never interacted with(GUI design). I learned a lot and enjoyed the process. I liked working and
  coordinating with my team, and I though overall we were organized. I think in summary we did
  relatively well and followed class guidelines, delivering a good,easily extendable end product.