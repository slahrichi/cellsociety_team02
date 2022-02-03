# Refactoring Lab Discussion
#### NAMES
#### TEAM


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

* Liskov Substution

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