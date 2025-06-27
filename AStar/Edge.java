/**
* Edge class to represent a directed, weighted edge. 
*/
public class Edge {
    long to;
    double weight;
    Edge (long to, double weight) {
        this.to =  to; // the id of the node / vertex which this edge points to
        this.weight = weight; // the weight / distance of this edge
    }
}
