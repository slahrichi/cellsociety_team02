# Breakout Abstractions Lab Discussion
#### Matthew Giglio, Saad Lahrichi, Luka Mdivani


## Princple Slogans

 * Single Responsibility
 
 * Open Closed
 


### Block

This superclass's purpose as an abstraction:
```java
 public class Block {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```
Having a general Block class is useful when extending it to other classes (such as when making 
different kinds of Blocks).

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Block {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```
This subclass offers to X all the methods that were already defined in the Block class. We can therefore
make a new specific type of Block and use this class to add its peculiarities.
#### Affect on Game/Level class (the Closed part)

If the Game class uses the Abstract Block object, then it would support every other subclass of it (including X)
This is useful when adding new types of Blocks and minimizes changes to the Main code. 
As the Level class can handle the abstract Block class, it would also be able to handle the Block subclasses.
So would the subclasses of Level.


### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```

The purpose of this superclass is to build a general PowerUp object.

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends PowerUp {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```
The difference between the subclass and the superclass is that the former can use everything originally 
defined in the superclass, and add new features that are specific to the PowerUp X. 

#### Affect on Game/Level class (the Closed part)
Same effect as the Block class. Different levels can call different speicifc powerUps, yet the 
sueprclass will always be referenced. The game class would also not have to worry about the specific powerups of each
level.


### Level

This superclass's purpose as an abstraction:
```java
 public class Level {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```

The Level class can hold the generic attributes of any Level.

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Level {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```
The subclass X will hold specific attributes of Level X and build upon the existing generic attributes shared
among all levels.

#### Affect on Game class (the Closed part)
The Game class would only deal with the superclass Level and would still support any Level subclass
we might want to add later without having to change our code too much.


### Others?
We could also make a subclass for Paddles that are different variations of the Paddle superclass.

