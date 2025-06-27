import java.util.*;
import java.io.*;

public class Test {
    public static void main(String[] args) {

        long totalTime = 0;
        long totalDistances = 0;
        long dNot0 = 0;
        long timeNot0 = 0;
        File distFile = new File("flevoland_distances.txt");
        try {
            distFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter newWriter = new FileWriter("flevoland_distances.txt");Graph graph;
            try {
                graph = new Graph("flevoland.txt"); 
            } catch (Exception ee) {
                System.err.println("Error loading graph: " + ee.getMessage());
                newWriter.close();
                return;
            }

            // Get all node IDs
            List<Long> nodeIds = new ArrayList<>(graph.nodeMap.keySet());
            
            // Ensure there are at least 2 nodes
            if (nodeIds.size() < 2) {
                System.err.println("Graph needs at least 2 nodes");
                newWriter.close();
                return;
            }
            Long[] nodeCopy = graph.uniqueNodes.toArray(new Long[graph.uniqueNodes.size()]);

            AStar astar = new AStar(graph);
            int timesToRun = 4000;
            for(int i = 0; i < timesToRun; i++) {
                    System.out.println("Remaining" + (timesToRun - i));
                    int random1 = (int)(Math.random() * nodeCopy.length);
                    int random2 = (int)(Math.random() * nodeCopy.length);
                    long node1 = nodeCopy[random1], node2 = nodeCopy[random2];
                    long start_time = System.currentTimeMillis();
                    double distance = astar.findMinDistance(node1, node2);
                    long end_time = System.currentTimeMillis();
                    totalTime = totalTime + (end_time - start_time);
                    
                    if(distance > 0){
                        timeNot0 = timeNot0 + (end_time - start_time);
                        dNot0++;
                        newWriter.write(node1 + " " + node2 + " " + distance + System.getProperty("line.separator"));
                    }
                    totalDistances++;
                }
        
            System.out.println("Total time " + totalTime + "ms with " + totalDistances + " distances calculated, out of which " + dNot0 + " were not 0 and " + (totalDistances - dNot0) + " were not possible");
            System.out.println("Time for not 0: " + timeNot0 + "ms");
            newWriter.close();
        
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}