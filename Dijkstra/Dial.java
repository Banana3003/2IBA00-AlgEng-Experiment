import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;

public class Dial {

    Graph graph;
    int src;
    int nodeCount;
    int edgeCount;
    int maxEdge; 
    int maxDist;
    int[] dist;
    List<Integer>[] buckets;

    public Dial (Graph graph, int src, int maxEdge) {
        this.graph = graph;
        this.src = src;
        this.maxEdge = maxEdge;
        this.dist = new int[graph.nodeCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        this.dist[src] = 0;

        // maxDist = w * (V - 1)
        this.maxDist = maxEdge * (graph.nodeCount - 1);
        // Generate buckets based on estimate
        buckets = new ArrayList[maxDist + 1];
        for (int i = 0; i <= maxDist; i++) {
            buckets[i] = new ArrayList<>();
        }
        buckets[0].add(src);
    }

    public void run() {

        int currentDist = 0;

        // Scan through the buckets
        while (currentDist <= maxDist) {
            if (buckets[currentDist].isEmpty()) {
                currentDist++;
                continue;
            }

            // Extract a vertex from the bucket
            int u = buckets[currentDist].remove(buckets[currentDist].size() - 1);

            // Skip stale nodes (already processed with shorter distance)
            if (dist[u] < currentDist) continue;

            // Relax each edge
            for (Edge edge : graph.getNeighbors(u)) {
                int v = edge.to;
                int weight = edge.weight;

                // Add relaxed edges back into the buckets
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    buckets[dist[v]].add(v);
                }
            }
        }
    }

    public static void main(String[] args) {
        String file = "noord_brabant.txt";
        CheckWeight cw = new CheckWeight(file);
        Graph graph = new Graph(file);
        // Rescale the input data
        int maxWeighted = Math.round((float) cw.maxWeight / 100f);
        Dial dial = new Dial(graph, 0, maxWeighted);
        long startTime = System.nanoTime();

        dial.run();

        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;
        double durationInMilliseconds = durationInNanoseconds / 1_000_000.0;

        int[] dist = dial.dist;
        // for (int i = 0; i < graph.nodeCount; i++) {
        //     String result = dist[i] != Integer.MAX_VALUE ? Integer.toString(dist[i]) : "Unreachable";
        //     System.out.println(String.format("To %d: %s", i, result));
        // }
        System.out.println("Duration in MilliSeconds: " + durationInMilliseconds);
    } 
}
