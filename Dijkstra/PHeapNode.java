public class PHeapNode {

    int dv; 
    int v;
    PHeapNode parent;
    PHeapNode leftChild;
    PHeapNode nextSibling;

    // Create a new null pairing heap node
    public PHeapNode() {
        this.parent = null;
        this.leftChild = null;
        this.nextSibling = null;
    }
    
    // Create a new pairing heap node, with pointer to its left child and its next sibling
    public PHeapNode(int distance, int vertex, PHeapNode leftChild, PHeapNode nextSibling) {
        this.dv = distance; 
        this.v = vertex;
        this.parent = null;
        this.leftChild = leftChild; 
        this.nextSibling = nextSibling;
    }

    // Add a child to this pairing heap node
    public void addChild(PHeapNode node) {
        if (this.leftChild == null) {
            this.leftChild = node;
        } else {
            // If left child is not null, we update the pointers to 
            // make the inserted node as the new left child
            node.nextSibling = this.leftChild;
            this.leftChild = node;
        }
        node.parent = this;
    }
}
