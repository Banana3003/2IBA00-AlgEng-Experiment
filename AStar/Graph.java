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
    public Map<Long, List<Edge>> adjList; // The adjacency list
    public HashMap<Long, Node> nodeMap;
    public Set<Long> uniqueNodes;
    public long nodeCount = 0; // Total number of nodes / vertices
    public long edgeCount = 0; // Total number of edges

    /**
     * Constructor to build a graph from an input file 
     * 
     * @param filepath the path to the input file
     */ 
    Graph(String filepath) {

        this.adjList = new HashMap<>();
        this.uniqueNodes = new HashSet<>();
        this.nodeMap= new HashMap<>();

        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            edgeCount = sc.nextLong();
            sc.nextLine();
            for(int i = 0; i < edgeCount; i++) {
                // The input file has format per line: "fromNode toNode weight"
                // We parse the line and add and such an edge
                String[] input = sc.nextLine().strip().split(" ");
                long fromNode = Long.valueOf(input[0]);
                long toNode = Long.valueOf(input[1]);
                double weight = Double.valueOf(input[2]);
                this.addEdge(fromNode, toNode, weight);
                // Use a set to count the number of nodes
                uniqueNodes.add(fromNode);
                uniqueNodes.add(toNode);
            }

            nodeCount = sc.nextInt();
            sc.nextLine();
            for(long i = 0; i < nodeCount; i++) {
                long id;
                double x, y;
                String[] input = sc.nextLine().strip().split(" ");
                id = Long.valueOf(input[0]);
                x = Double.valueOf(input[1]);
                y = Double.valueOf(input[2]);
                nodeMap.put(id, new Node (id, Math.round(x), Math.round(y)));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // Add an directed, weighted edge to the graph
    public void addEdge(long fromNode, long toNode, double weight) {
        adjList.putIfAbsent(fromNode, new ArrayList<>());
        adjList.putIfAbsent(toNode, new ArrayList<>());
        adjList.get(fromNode).add(new Edge(toNode, weight));
    }

    // Get the neighbors of a node
    public List<Edge> getNeighbors(long node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    // Get the adjacency list of the grpah 
    public Map<Long, List<Edge>> getAdjList() {
        return adjList;
    }
}
