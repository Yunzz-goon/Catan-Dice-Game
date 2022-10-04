## Code Review

Reviewed by: <Yunzhong Zhang>, <u7543704>

Reviewing code written by: <Jingru Lin> <u7503889>



Component: 
a. https://gitlab.cecs.anu.edu.au/u7501226/comp1110-ass2/-/blob/main/src/comp1110/ass2/Player/Dice.java#L7-12
b. https://gitlab.cecs.anu.edu.au/u7501226/comp1110-ass2/-/blob/main/src/comp1110/ass2/Player/Player.java#L35-49
c. https://gitlab.cecs.anu.edu.au/u7501226/comp1110-ass2/-/blob/main/src/comp1110/ass2/Player/Player.java#L62-64

### Comments
The codes are well implemented with no bugs and are well performed on naming for the variable and methods. However, there exists problems like bad-documented method, some potential errors, inconvenience for using variable, and so on. To getting improved, I list the improvement out below respectively.

a. As for the resource type, instead of using string, it may be a better choice to use the data type "enum" to enumerate the limited resource types.
b. As for this part, it may be better if the coder use a object-oriented implement since if coder using the global variable, it may meet some trouble when operating method on the object. And maybe only one object could use the method without any trouble. Also, the methods are not well-documented. It is advised to provide some description for the method.
c. Speaking of this part, the method seems to kill the game after 15 turns, there lacks some condition so I derive it is an incomplete version.  

