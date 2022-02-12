# Cell Society Design Final

### Matthew Giglio, Saad Lahrichi, and Luka Mdivani

## Team Roles and Responsibilities

* Team Member #1 - Matthew
    * Developed all code in Model package
    * Proposed general design architecture for project

* Team Member #2 - Luka
    * Developed all code for the Visualizer package
    * Made the Main.java

* Team Member #3

## Design goals

    - Model
        - Hide the implementation details from the Controller and View
        - Leverage inheritance to make the addition of simulations seamless
        - Keep code flexible in terms of parameters and its ability to take on new features
        - Avoid any hardcoded values

* Visualizer
    * make the view completely separate from controller and model.
    * Make necessary abstraction to make adding new grid types very easy
    * Simplify the process of adding new GUI items as much as possible.

#### What Features are Easy to Add

    - Model
        - Any additional simulation is as simple as creating a new trio of cell, grid, and 
        simulation extension classes; no existing classes need to be touched
        - Different cell shapes are as simple as creating a new option for row/col delta arrays for
        a shape's neighbor layout: all neighbor checking code is agnostic in terms of which shape 
        it's dealing with

* Visualizer
    * Any new grid type is easy to add you just extend the `GridVisualizer.java` class and implement
      all abstract methods.
    * New Control Panels are easy to make , since the needed methods like `makeButton()`
      , `makeMenuItem()`
      are all in the `ControlPanel.java` superclass, so you only need to extend that. And add the
      new subclass to the root in `SimulationVisualizer.java`.
    * new languages, css styles are very easy to add ,you just make the necessary resource files and
      add a one line command (in `MenuBarControlPanel.java`) to create the new button, and it will
      be shown on the GUI.

## High-level Design

#### Core Classes

- Main.java (launcher, connects everything)
    - `ResettableStage.java`
- Model
    - `Simulation.java`
    - `Cell.java`
    - `Grid.java`
    - `States.java`
    - `Coordinate.java`
- Visualizer
    - `SimulationVisualizer.java`
    - `GridVisualizer.java`
    - `ControlPanel.java`
        - `AnimationControlPanel.java`
        - `MenuBarControlPanel.java`
    - `DataGraph.java`
    - `ErrorWindow.java`

## Assumptions that Affect the Design

#### Features Affected by Assumptions

    - Model
        - FallingSand was designed to only check left, down, and right neighbors, regardless of
        requested neighbor configuration or cell shape
            - This was not a fault of the design but more of my choice to follow the algorithm
                prescribed in documentation
    - Visualizer
      - When the css style is changed from the inital value from the xml file, reset option reset to 
         user choice insted of whatever is in the xml, this was by design. Since I though that would be 
         the user preference in a real world.

## Significant differences from Original Plan

    - Our plan held up generally well: we stuck to the MVC structure, implemented a map as the grid
    data structure, and had to make minor changes to adjust for all requested changes in the 
    second phase

## New Features HowTo

#### Easy to Add Features

- Model
    - Any additional simulations can just be added as additional classes rather than overwriting
      existing classes
    - Cell shape additions involve adding a new row/col delta pair to `Neighbors.java`
- Visualizer
    - New grid types are simply added by creating the new extensions of `GridVisualizer.java`.
        - One should also add the identifier string to `chooseGridType()` choice option.
    - new languages, css styles are very easy to add ,you just make the necessary resource files
      and add a one line command (in `MenuBarControlPanel.java`) to create the new button, and
      it will be shown on the GUI.
    - To add a new control panel simply use methods in `ControlPanle.java` and write the new
      class, add the node of the new controlPanel to the main root
      in `SimualtionVisualizer.java`

#### Other Features not yet Done

- Model
    - Infinite grid logic was not implemented, but it would not have been 
    difficult; the program could have just added more coordinates to the map if a coordinate 
    was not in bounds of the grid but the simulation type was infinite
        - A row/col addition method would be added to initialize non-existent cells in the grid
    - Multiple neighborhoods could be considered by "scaling" the current neighborhood in 
    several directions relative to the cell
        - The logic at each multiple neighborhood would be the same as the original 
        neighborhood, so just offsetting the original neighborhood given how many requested
        multiple neighborhoods there are would achieve the feature
- Visualizer
  - Most of the new features were added already.
    - A graph of changes in cell population can easily be added by making a new class for the graph which returns
    the final Node of the graph Group, and add that to the main root in `SimulationVisualizer.java`
    - Different features for allowing further live interaction with the grid are simple to make because the code is very understandable and organized, but they won't follow the open-closed principle well. Also need a lot of supporting mechanic to exist in Model.
      - Objects like new control panels (for example tabs and buttons enabling to change WaTor configurations) are easy to add because necessary superclasses already exist.
    - select colors or images used to display the cell states is easy to add, one would need a listener for each cell, and a new control panel object enabled when a cell is selected. But also not open-closed.
