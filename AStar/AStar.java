import java.util.*;

public class AStar {
    private final Graph graph;
    private Node goalNode; // Stores goal coordinates for distance calculations

    public AStar(Graph graph) {
        this.graph = graph;
    }

    public double findMinDistance(long start, long goal) {
        // Retrieve goal node coordinates once [1]
        this.goalNode = graph.nodeMap.get(goal);
        if (goalNode == null) return -1;

        Map<Long, Double> gScore = new HashMap<>(); // Best known cost from start
        PriorityQueue<AStarNode> openSet = new PriorityQueue<>(
            Comparator.comparingDouble(n -> n.fScore)
        );

        gScore.put(start, 0.0);
        double startFScore;
        startFScore= manhattenDistance(graph.nodeMap.get(start), goalNode);
        openSet.add(new AStarNode(start, 0.0, startFScore));

        while (!openSet.isEmpty()) {
            AStarNode current = openSet.poll();
            
            if (current.id == goal) {
                return current.gScore; // Path found
            }
            
            if (current.gScore > gScore.getOrDefault(current.id, Double.MAX_VALUE)) {
                continue; // Skip outdated nodes [3]
            }
            
            for (Edge edge : graph.adjList.getOrDefault(current.id, Collections.emptyList())) {
                long neighborId = edge.to;
                double tentativeG = current.gScore + edge.weight;
                
                if (tentativeG < gScore.getOrDefault(neighborId, Double.MAX_VALUE)) {
                    gScore.put(neighborId, tentativeG);
                    Node neighborNode = graph.nodeMap.get(neighborId);
                    double fScore = tentativeG + manhattenDistance(neighborNode, goalNode);
                    openSet.add(new AStarNode(neighborId, tentativeG, fScore));
                }
            }
        }
        return -1; // Unreachable
    }

    // Euclidean distance calculation embedded directly
    private double euclideanDistance(Node a, Node b) {
        double dx = a.X - b.X;
        double dy = a.Y - b.Y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double manhattenDistance(Node a, Node b) {
        double dx = a.X - b.X;
        double dy = a.Y - b.Y;
        return Math.abs(dx) + Math.abs(dy);
    }

    private double noDistance(Node a, Node b) {
        return 0;
    }


    private static class AStarNode {
        long id;
        double gScore; // Actual cost from start
        double fScore; // Estimated total cost (gScore + heuristic)
        
        AStarNode(long id, double gScore, double fScore) {
            this.id = id;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }
}