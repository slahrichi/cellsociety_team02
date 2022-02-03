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

#### Abstraction #1
* Open/Close
  * Instead of having an XML parser, we can have a Parser superclass. This would allow support for any type
    of files in the future, should we want to use .json or any other type.

* Liskov Substution
    * The parsing in general has some methods that would be used for any file type (e.g. createDocumentBuilder, createTransformer)...
    * As the specific type of parser would inherit the same methods as the superclass, the other classes would be able to interact with it without knowing  
    * the specific file type.

#### Abstraction #2
* Open/Close

* Liskov Substution



## Issues in Current Code

### Method or Class
* Design issues

* Design issue

### Method or Class
* Design issues

* Design issue



## Refactoring Plan

* What are the code's biggest issues?

* Which issues are easy to fix and which are hard?

* What are good ways to implement the changes "in place"?



## Refactoring Work

* Issue chosen: Fix and Alternatives


* Issue chosen: Fix and Alternatives