import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Graph class to represent a directed, weighted graph. 
 */
public class Graph {
    public Map<Integer, List<Edge>> adjList; // The adjacency list
    public int nodeCount = 0; // Total number of nodes / vertices
    public int edgeCount = 0; // Total number of edges

    /**
     * Constructor to build a graph from an input file 
     * 
     * @param filepath the path to the input file
     */ 
    Graph(String filepath) {

        this.adjList = new HashMap<>();
        Set<Integer> uniqueNodes = new HashSet<>();

        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {

                // The input file has format per line: "fromNode toNode weight"
                // We parse the line and add and such an edge
                String[] input = sc.nextLine().strip().split(" ");
                int fromNode = Integer.valueOf(input[0]);
                int toNode = Integer.valueOf(input[1]);
                int weight = Integer.valueOf(input[2]);
                // The commented out code below is rescaling for dial
                // weight = Math.round((float) weight / 100f);
                // weight = (weight == 0) ? 1 : weight;
                this.addEdge(fromNode, toNode, weight);

                this.edgeCount++; // Count the number of edges
                // Use a set to count the number of nodes
                uniqueNodes.add(fromNode);
                uniqueNodes.add(toNode);
            }
            this.nodeCount = uniqueNodes.size();
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // Add an directed, weighted edge to the graph
    public void addEdge(int fromNode, int toNode, int weight) {
        adjList.putIfAbsent(fromNode, new ArrayList<>());
        adjList.putIfAbsent(toNode, new ArrayList<>());
        adjList.get(fromNode).add(new Edge(toNode, weight));
    }

    // Get the neighbors of a node
    public List<Edge> getNeighbors(int node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    // Get the adjacency list of the grpah 
    public Map<Integer, List<Edge>> getAdjList() {
        return adjList;
    }
}
