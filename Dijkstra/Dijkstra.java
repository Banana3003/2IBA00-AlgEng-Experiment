import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;

public class Dijkstra {

    Graph graph;
    int src;
    int nodeCount;
    int edgeCount;
    int[] dist;
    HashSet<Integer> visited; 
    PriorityQueue<ArrayList<Integer>> pqueue;
    // Pi can be added to reconstruct the shortest path to a node
    // However, for strictly dist, pi is not necessary here

    public Dijkstra (Graph graph, int src) {
        this.graph = graph;
        this.src = src;
        this.dist = new int[graph.nodeCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        this.dist[src] = 0;
        this.visited = new HashSet<>();
        pqueue = new PriorityQueue<>(Comparator.comparingInt(a -> a.get(0)));
    }

    public void run() {
        // Put all verticies in min-priority queue with d(v) as key
        for (int i = 0; i < graph.nodeCount; i++) {
            pqueue.add(new ArrayList<>(Arrays.asList(dist[i], i)));
        }
        // While priority queue is not empty
        while (!pqueue.isEmpty()) {
            // Extract min of priority queue
            ArrayList<Integer> minElement = pqueue.poll();
            int minNode = minElement.get(1);
            // visited.add(minNode);
            // For each outgoing edge (u,v), relax(u,v)
            for (Edge edge : graph.getNeighbors(minNode)) {
                relax(minNode, edge);
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
        for (ArrayList<Integer> element : pqueue) {
            // Needs to be improved, since this is not O(1)
            if (element.get(0) == oldDist && element.get(1) == to) {
                pqueue.remove(element);
                break;
            }
        }
        pqueue.add(new ArrayList<>(Arrays.asList(dist[to], to)));
    }

    public static void main(String[] args) {
        Graph graph = new Graph("flevoland.txt");
        Dijkstra dijkstra = new Dijkstra(graph, 0);
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
