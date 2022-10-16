## Code Review

Reviewed by: Matthew Britton u7501226

Reviewing code written by: Yunzhong Zhang, u7543704



Component:
a. https://gitlab.cecs.anu.edu.au/u7501226/comp1110-ass2/-/blob/main/src/comp1110/ass2/Main/BuildBuilding

### Comments
Overall the code is well written and seems to be bug free. I can see no issues with the implementation, and the use of a 
hash map is definitely a better choice than the way I implemented this part of the game in my code (Available.java).
Whilst the code seems to lack documentation, most of the code is written such that it is obvious what it does, and 
any extra documentation would be superfluous.
However, there are a few things that could be improved:

1: The code is very repetitive, and the way he repeats the addition of each new building is not very efficient. I would instead recommend that he writes:
```
cities.put(<city points>, new City(<city points>, <adjacent road>, <previous city>));
```
over his current implementation, which is:
```
City city = new City(7, roads.get(1), null);
cities.put(city.getPoint(), city);
```
this would significantly shorten the code and make it more clear what the code is doing.

2: Additionally, the use of private variables and implementing getter and setter methods would be more in keeping with 
the java style guidelines, however given that these elements of the project are likely to be accessed very regularly 
throughout the evolution of the game I can see why he has chosen to implement them in this way.

3: Finally, there appears to be some duplicated code in the buildBuilding method, although this is not a major issue, and
the code is still very readable. I would recommend that he uses IntelliJ's auto refactoring to rectify this issue.

