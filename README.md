# 2IBA00-AlgEng-Experiment

### Dijkstra
Under folder Dijkstra, we have implementation of basic dijkstra, dijkstra with min heap, dijkstra with pairing heap, dijkstra without priority queue, dial's algorithm, and their auxiliary heap classes. We also provide input file "flevoland.txt" as example data. We do not upload data for map of utrecht or noord brabant due to github only allowing 25MB files. 

To run Dijkstra on a specific map, fill in the map input text's file name (e.g. flevoland.txt) when initilizing a Graph instance in the main function of the Dijkstra implementation. Note that Dial's algorithm requires changing a part of the "Graph" class as indicated by the comments in that file. 

#### Astar
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
