To run the A* algorithm on the flevoland data, you must run the Test.java file. By default it runs the AStar 
algorithm on the "flevoland.txt" file using the manhatten distance heuristic. 

To modify the input file, and the output file, you can replace "flevoland.txt" to the input file and
"flevoland_distances.txt" to the output file.

To modify the heuristics, the "manhattenDistance" in the AStar class must be replaced with the wanted heuristic.
The other implemented heuristics are "euclideanDistance" and "noDistance"(returns 0)

Required input data:
m
node1 node2 dist
... ← m-1 more lines representing the edges of the graph
n
node1 X Y 
... ← n-1 more lines representing the node and the XY position of it