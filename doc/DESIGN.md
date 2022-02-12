# Cell Society Design Final
### Matthew Giglio, Saad Lahrichi, and Luka Mdivani

## Team Roles and Responsibilities

 * Team Member #1 - Matthew
   * Developed all code in Model package
   * Proposed general design architecture for project 

 * Team Member #2

 * Team Member #3



## Design goals
    - Model
        - Hide the implementation details from the Controller and View
        - Leverage inheritance to make the addition of simulations seamless
        - Keep code flexible in terms of parameters and its ability to take on new features
        - Avoid any hardcoded values

#### What Features are Easy to Add
    - Model
        - Any additional simulation is as simple as creating a new trio of cell, grid, and 
        simulation extension classes; no existing classes need to be touched
        - Different cell shapes are as simple as creating a new option for row/col delta arrays for
        a shape's neighbor layout: all neighbor checking code is agnostic in terms of which shape 
        it's dealing with


## High-level Design

#### Core Classes
    - Model
        - `Simulation.java`
        - `Cell.java`
        - `Grid.java`
        - `States.java`
        - `Coordinate.java`



## Assumptions that Affect the Design


#### Features Affected by Assumptions
    - Model
        - FallingSand was designed to only check left, down, and right neighbors, regardless of
        requested neighbor configuration or cell shape
            - This was not a fault of the design but more of my choice to follow the algorithm
                prescribed in documentation


## Significant differences from Original Plan
    - Our plan held up generally well: we stuck to the MVC structure, implemented a map as the grid
    data structure, and had to make minor changes to adjust for all requested changes in the 
    second phase


## New Features HowTo

#### Easy to Add Features
    - Model
        - Any additional simulations can just be added as additional classes rather than 
        overwriting existing classes
        - Cell shape additions involve adding a new row/col delta pair to `Neighbors.java`


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

