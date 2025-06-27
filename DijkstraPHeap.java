import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;

public class DijkstraPH {

    Graph graph;
    int src;
    int nodeCount;
    int edgeCount;
    int[] dist;
    HashSet<Integer> visited; 
    PHeap pqueue;
    // Pi can be added to reconstruct the shortest path to a node
    // However, for strictly dist, pi is not necessary here

    public DijkstraPH (Graph graph, int src) {
        this.graph = graph;
        this.src = src;
        this.dist = new int[graph.nodeCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        this.dist[src] = 0;
        this.visited = new HashSet<>();
        pqueue = new PHeap();
    }

    public void run() {
        // Put all verticies in min-priority queue with d(v) as key
        for (int i = 0; i < graph.nodeCount; i++) {
            pqueue.insert(this.dist[i], i);
        }
        // While priority queue is not empty
        while (!pqueue.isEmpty()) {
            // Extract min of priority queue
            HeapNode minNode = pqueue.extractMin();
            int minVertex = minNode.v;
            // visited.add(minNode);
            // For each outgoing edge (u,v), relax(u,v)
            for (Edge edge : graph.getNeighbors(minVertex)) {
                relax(minVertex, edge);                
            }
        }
    }

    private void relax(int from, Edge edge) {
        if (dist[from] == Integer.MAX_VALUE) return;
        if (dist[from] + edge.weight < dist[edge.to]) {
            int oldDist = dist[edge.to];
            dist[edge.to] = dist[from] + edge.weight;
            decreaseKey(oldDist, edge.to);
        }
    }

    private void decreaseKey(int oldDist, int to) {
        pqueue.decreaseKey(to, dist[to]);
    }

    public static void main(String[] args) {
        Graph graph = new Graph("noord_brabant.txt");
        DijkstraPH dijkstra = new DijkstraPH(graph, 0);
        long startTime = System.nanoTime();

        dijkstra.run();

        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;
        double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;

        int[] dist = dijkstra.dist;
        //for (int i = 0; i < graph.nodeCount; i++) {
        //    String result = dist[i] != Integer.MAX_VALUE ? Integer.toString(dist[i]) : "Unreachable";
        //    System.out.println(String.format("To %d: %s", i, result));
        //}
        System.out.println("Duration in MilliSeconds: " + durationInMilliseconds);
    } 
}
