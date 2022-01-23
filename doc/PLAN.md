# Cell Society Design Plan
### Team 2
### Matthew Giglio, Saad Lahrichi, and Luka Mdivani


## Design Overview
The overall design will consist of three general areas: the model, the view, and the controller. The model will handle the backend portion of updating states and values throughout a simulation, and the view will render the graphics displaying the progress of the simulation. As the parameter values for each simulation must be determined by an XML file, the controller will handle file parsing and the logic of determining which specific simulation should be displayed.

To enable efficient implementation and to allow for flexibility in terms of features or extensions, the design will leverage inheritance - particularly in the model - to take advantage of the similarities between the various simulations. All simulations involve a grid and cells, so abstract `Grid` and `Cell` classes will serve as superclasses to extensions of different grid and cell shapes for each specific simulation. Each simulation will need its own class because the logic by which each simulation progresses and updates is different, so a general `Simulation` class will also act as a superclass. If the simulation, grid, and cell logic are all managed abstractly, then the viewer can be designed generically and work with the superclasses, allowing the graphical rendering process to be the same for all simulations. 

While the view will not need to implement its own superclasses since it will utilize the abstractions created by the model, the way in which different simulations will be chosen will be a fundamental part of the controller. The controller will have to handle a user input, and given that user input, it will have to create a particular `Simulation` implementation and build it from the values prescribed in the XML file. 


## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).

### Model

The model will manage 3 abstract classes - `Simulation`, `Grid`, and `Cell` - and extensions of each of them for the 5 different simulations. The `Simulation` class will have instance variables including a `Grid`, the `numberOfRows`, the `numberOfColumns`, and the `numberOfCells`. To initialize the simulation, the class will need to have calls to `createGrid()` and `populateCells()` in its constructor. The purpose of `Simulation.java` is to manage the backend resources of a given simulation, so although it will have `step(double elapsedTime)` and `updateCells()` methods to update the state of the simulation elements over time, none of the processes will directly alter the graphics. For the abstract class, many of these methods will be also be abstract and instead be implemented in each specific variation of the simulation: different simulations update their cells with different logic, so they cannot share the same methods.

The `Grid` class will take in the number of cells, rows, and columns from the simulation class and create a `Cell[][]` to represent the grid itself. Although the simulation will iterate over the grid to determine cells which need to be updated, the `Grid` class will have internal method to update its own values as opposed to being handled in `Simulation.java`. Additionally, the `Cell` class will store attributes such as a `Shape`, size, color, and a dead-or-alive value, and it will also be able to actively update its own values. 


## Use Cases

 * Team generated Use Case
 ```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
 ```


## Design Considerations

#### Design Issue #1

 * Alernative #1

 * Alernative #2

 * Trade-offs


#### Design Issue #2

 * Alernative #1

 * Alernative #2

 * Trade-offs



## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Team Responsibilities

 * Team Member #1 - Matthew
    - Handling the model aspect of the project
    - Keep teammates in the loop about model updates so view and controller can be implemented accordingly 

 * Team Member #2

 * Team Member #3


#### Proposed Schedule
