PathFinders
==============

A basic implementation of common path finding (graph) algoritms.
<li> This includes:</li>
<ul>
<li>A-Star (A*) shortest path finding algorithm. </li>
<li>Bredth First Search algorithm (BFS)</li>
<li>Depth First Search algorithm (DFS)</li>

<ul>
You have actors (people) who are trying to get from point A-B. They need a path (a queue of points) to traverse. 
Have static methods: Model.getDeterminedPath() will give you a path to move to. 

--------------
How to run
<ul>
<li>1. Launch model.java</li>
<li>2. Your only person (in red) is at grid coordinate (5,5)</li>
<li>3. Person can only move in cardinal direction (NESW)</li>
<li>4. Left click to set the destination</li>
<li>5. Right click to set a building (in blue) which appears as your obstacle</li>
<li>6. Upon reaching location, program will terminate (as of V1.0)</li>
</ul>
-------------
Algorithm basics
<ul>
<li>1. Check if at point</li>
  <ul><li>1.1 If so return the destination</li></ul>
</ul>
<ul>
<li>2. If adjacent to block</li>
    <ul><li>2.1 Return a path with starting and ending point</li></ul>
</ul>
<ul>
<li>3. If not adjacent or at desination</li>
    <ul>
    <li>3.1 Set up openlist & closed list</li>
      <ul>
      <li> 3.1.1 Open list is the set of all outer adjacent blocks </li>
      <li> 3.1.2 Closed list if the set of all the blocks you have visited and set adjacent into the open list</li>
      </ul>
    </ul>
    <li>3.2 Put initial location into closed list</li>
    <li>3.3 While not at desination determine adjacent clear blocks (no buildings)</li>
    <li>3.4 For all blocks, assigns g, h, f values for that block</li>
        <ul>
        <li>3.4.1 G value is the distance from start to current block (1 more than the g value of parent)</li>
        <li>3.4.2 H value is a heuristic value (guess at distance <= actual distance) - Used manhatten distance</li>
        <li>3.4.3 F Value is the sum of the g & h values</li>
        </ul>
    <li>3.5 Add any adjacent blocks that aren't in open list into it</li>
        <ul>
        <li>3.5.1 Select the closest block (smallest f value) as the your current block</li>
        </ul>
    <li>3.6 Continue steps 3.3 until case 2</li>
      <ul>
        <li>3.6.1 At this point, you are adjacent, so add that adjacent point to path</li>
        <li>3.6.2 Traverse from final point to no parent , adding to path at all points</li>
      </ul>
</ul>

Notable help:
<a href="http://www.raywenderlich.com/4946/introduction-to-a-pathfinding">Pathfinding Tutorial</a>
