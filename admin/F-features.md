
# Features

In addition to the features that are auto-tested, the graphical user interface
of our project implements the following features:

*(Remove those that are unimplemented)*

 - A simple placement viewer (Task 5)
 - A basic playable game (Task 10)
 - Playable against a computer opponent (Task 15) // TODO

List additional noteworthy features beyond those in the assignment
specification that you have added, if any.
- The playable game UI we developed could cover most of the game logic and rules. In other words, it's not just a board to present the elements in the game, instead, if some operations(like build, trade and swap) that violates the rules of the games, it will print out an error message in the "Run" area. 
- In the playable game UI, as for the marking function, instead of record by the users themselves, we deploy an automatic marking system, for both each round and end of the game. 
- During the development, most of our codes take the advantage of the object-oriented programming features, including using interface, extend. 
- We also supply the comments for most of the class and methods, which implies the codes are easy to read. 

## Player instructions

Use this section of the file to write any necessary instructions on
how to play your implementation of the game. You can assume the player
knows the game rules; the purpose of this section is to document how
to use any parts of your game GUI that are not intuitively obvious
(and not documented in the GUI itself).

When you open the API, you could see a game map, a text showing how much resource you have, an round-end button, a marking board, some text box and button for dice operation, building operation, trade operation and swap operation respectively. We will introduce how to use them one by one. 
- Dice: When the first time you roll the dice, you will initialize all the resource randomly. As for the second and third chance, input the number of the dice(0 for Ore, 1 for Grain, 2 for Wool, 3 for Lumber, 4 for Brick, 5 for Gold) that you want to reroll one by one in the text box and press the button"Roll it" if you want reroll the dice the resource that you don't want. For example, if you want to reroll the dices that are currently Ore, Ore and Grain respectively, you just have to input<001>.
- Build: There is three colors represented status of the buildings: yellow for the unbuilt, green for the built but not used and red for the built and used(only for the knight building). Input the code of the building that you want to build and press "Build it", the building on the map would turn green if it success, otherwise, it will tell you in the "Run" area what constraints it violates. You should build buildings one by one, which implies you are permitted to input one building code in one time. It is notice that building encoding is consistent with the style provided by lecturers. For example, if you want to build the first road, just input <R0> in the text box and press "Build it!". Notice that if you want to build the joker you have to input <J1>-<J6>" instead of input "K" related coding. After all, you cannot build a joker building if it has been used(K represented built and used).
- trade: Input the resource <Amount-type> that we want to swap in the text box and press "Trade it!" button. (e.g. 2-Grain means: use 4 gold to replace two grain). 
- Swap: Input the resource we would like to pay, the joker number that we want to use, and resource want if in joker 6 <amount-type-joker number-(resource we want)>   (e.g. 2-Grain-1 means we want to use 2 grains to replace 2 Ore; 2-Grain-6-Ore means we want to use 2 grains to replace 2 Ore;).
- End button: Once you finish one round, you have to click the "End round" button, then your resource will be cleaned and the marks in this round would be counted in the marking board automatically. 
- Marking board: Once you finish 15 rounds, the board would calculate your final mark automatically in the final box. 

