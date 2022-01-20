# Rock Paper Scissors Lab Discussion
#### Matthew Giglio-mcg69, Saad Lahrichi-sl636 , Luka Mdivani - lm378


### High Level Design Goals

We are thinking of making several classes.

* `Player` Class- which would handle the input from the player, and use it to run the game.

* `Game` Class- a non-abstract class that handles managing the game resources/rules. `Game` could work with objects from the other superclasses, and because all of those objects will be specific implementations, the game manager could still work regardless of which specific rules are being implemented

* `Weapon` Class-since we can play games with weapons other than just rock, paper, scissors, having a weapons superclass would allow us to manage all weapons in the superclass as well as add new ones as needed

* `CPU` Class- an abstract class which can serve as a general superclass for AI player. It could be extended in other classes according to specific strategies.

### CRC Card Classes


|Game| |
|---|---|
|knows Player and CPU                 ||
|knows Game     |Player|
|`void playTurns()`   |CPU |
|`void initializeGame()` | |
| `void checkWeaponMatchup(String w1, String w2)`| |

|Player| |
|---|---|
|knows player lives       ||
|knows player valid moves/weapons     |Game|
|`void makeMove()`    | |


|CPU| |
|---|---|
|Move generateNextMove(OrderLine)         |Game|
|knows valid moves/weapons     |Weapon|

This class's purpose is to represent an opponent against which the user plays.

```java=
public class CPU{
    // returns the next move of the CPU
    public Move generateNextMove()
}
```
This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```
 ```java
public class CPU {
     // returns the move by the cpu
     public Move generateNextMove ()  
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```


### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
Game game = new Game(Player[] players = new Player[5]);
for (Player p : List<Players> players){
    player.score = 0
}
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
for (Player p : List<Players> players) {
    p.makeChoice();
}
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 if (hasWon(player)) {
     player.addToScore();
 }
else {
    player.lowerScore();
}
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
list<String> choices= new List<String>;
choices.add(newChoice)
checkWeaponMatchup(choices)
updateHashMap(choices)
//checkWeaponMatchup(String w1, String w2)

 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Scanner s = new Scanner(...);
 HashMap<String, List<String>> weaponRelationships = new HashMap<>();
while(s.hasNext()) {
    //parse input to create weapon relationship in map
}
 ```

