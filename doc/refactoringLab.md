 # Refactoring Lab Discussion
#### Matthew Giglio, Luka Mdivani, Saad Lahrichi
#### Team 2


## Principles

### Current Abstractions

#### Abstraction #1 - Simulation
* Open/Close
  * Has all the essential features of any simulation, so any new simulation can extend this
  class and add its own features that are specific to its algorithm
  * Example: the superclass has numColumns, numRows, a `Grid` data structure, the `edgeType`,
  and the neighbor configuration, which occurs in any simulation, but the `SpreadingFire` 
  specifically has `probCatch`

* Liskov Substution
  * Since all of the `Simulation`s inherit the same methods, the controller and view can interact
  with any of the simulations without knowing which type of `Simulation` it is

#### Abstraction #2 - Grid
* Open/Close
  * Same reasoning as above

* Liskov Substution
  * Same reasoning as above

#### Abstraction #3 - Cell
* Open/Close
  * Same reasoning as above

* Liskov Substution
  * Same reasoning as above


### New Abstractions

#### Abstraction #1 : General Parser
* Open/Close
  * Instead of having an XML parser, we can have a Parser superclass. This would allow support for any type
    of files in the future, should we want to use .json or any other type.

* Liskov Substution
    * The parsing in general has some methods that would be used for any file type (e.g. createDocumentBuilder, createTransformer)...
    * As the specific type of parser would inherit the same methods as the superclass, the other classes would be able to interact with it without knowing  
    * the specific file type.

#### Abstraction #2 : General Graph
* Open/Close

  Since the graphs for different models will have different number of cell types etc., it might be 
smarter to create a superclass which has all the abstractions for general methods they would need.
And then add subclass with specific methods for specific simulations, given the number of celltypes,
what data needs to be visualized etc.

* Liskov Substitution

Since all the graphs will need to have shared properties like the graph view background, graph size,
etc. All specific graph objects would inherit these features, and be able to interact with it without knowing.

## Issues in Current Code

### Method or Class: XMLParser.createSimulation 
* The method createSimulation does too many things, which is why it is too long. I did that to avoid DRY code, but it resulted in a method
* that does too many things. It can be split into two or three shorter methods.


### Method or Class: SimulationVisualizer.java

* The class has too many dependencies, I will create separate classes for features like the animationController,
where I will handle all the Play/Pause/Step/Speed button creation.

* I could also move the menu settings to a separate class, although I am not sure whether I should do this.



## Refactoring Plan

* What are the code's biggest issues?
  * Some long methods in the controller
  * Some protected vs private issues with superclasses in the model
  * Lack of class separation in the view

* Which issues are easy to fix and which are hard?
  * Splitting methods in view should be relatively straightforward
  * The instance variables in the `Cell`, `Simulation`, and `Grid` superclasses were accidentally
  made protected, so while shifting them to private is not necessarily difficult from a conceptual
  standpoint, it is a bit tedious given the refactoring with new protected getter methods
  * The controller line reduction is not incredibly difficult, but dealing with the different data
  types parsed in the XML files and the numerous amount of simulations to choose from, it is not
  immediately obvious how to break down the methods

* What are good ways to implement the changes "in place"?
  * For all the issues above, making incremental changes and checking that each individual stage
  still functions as opposed to making large overhauls in a single step will allow for optimal 
  changing



## Refactoring Work

* Issue chosen: in XMLParser.createSimulation, I chained some calls instead of storing unnecessary variables and only parsed the parameters 
* inside the Simulations that require then.


* Issue chosen: fixing the instance variables in the Cell superclass to be private (done on Matthew)


* Issue chosen: splitting the classes in view to have better readability and separation