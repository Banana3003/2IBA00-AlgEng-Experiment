import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PHeap {

    private PHeapNode root;
    private Map<Integer, PHeapNode> posMap;

    public PHeap() {
        root = null;
        posMap = new HashMap<>();
    }

    // Returns if PHeap is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Get the values of root, for debuggin
    public HeapNode top() {
        return new HeapNode(root.dv, root.v);
    }

    // Insert a new node
    public void insert(int distance, int vertex) {
        PHeapNode newNode = new PHeapNode(distance, vertex, null, null);
        root = merge(root, newNode);
        posMap.put(vertex, newNode);
    }

    // Delete the root node
    public void deleteMin() {
        root = delete(root);
    }

    // Merge two PHeaps
    public void join(PHeap other) {
        root = merge(root, other.root);
    }

    // Extract the root node, and fix the heap
    public HeapNode extractMin() {
        if (root == null) return null;
        HeapNode min = new HeapNode(root.dv, root.v);
        posMap.remove(root.v);
        deleteMin();
        return min;
    }

    // Decrease key
    public void decreaseKey(int vertex, int distance) {
        PHeapNode node = posMap.get(vertex);
        if (node == null || node.dv <= distance) return;
        node.dv = distance;
        if (node == root) return;

        PHeapNode parent = node.parent;
        if (parent == null) return;

        if (parent.leftChild == node) {
            parent.leftChild = node.nextSibling;
        } else {
            PHeapNode prev = parent.leftChild;
            while (prev != null && prev.nextSibling != node) {
                prev = prev.nextSibling;
            }
            if (prev != null) {
                prev.nextSibling = node.nextSibling;
            }
        }

        node.nextSibling = null;
        node.parent = null;

        root = merge(root, node);
    }

    // Delete a node, and fix the heap
    private PHeapNode delete(PHeapNode node) {
        return twoPassMerge(node.leftChild);
    }

    // Merge two nodes / heaps
    private PHeapNode merge(PHeapNode a, PHeapNode b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.dv < b.dv) {
            b.nextSibling = null;
            a.addChild(b);
            return a;
        } else {
            a.nextSibling = null;
            b.addChild(a);
            return b;
        }
    }

    // Two passmerge to fix a heap
    private PHeapNode twoPassMerge(PHeapNode node) {

        if (node == null || node.nextSibling == null) return node;

        List<PHeapNode> pairs = new ArrayList<>();
        while (node != null && node.nextSibling != null) {
            PHeapNode a = node;
            PHeapNode b = node.nextSibling;
            node = b.nextSibling;

            a.nextSibling = null;
            b.nextSibling = null;

            pairs.add(merge(a, b));
        }

        // If odd number of nodes, add last one
        if (node != null) {
            pairs.add(node);
        }

        int i = pairs.size() - 1;
        PHeapNode result = pairs.get(i);

        for (i = i - 1; i >= 0; i--) {
            result = merge(pairs.get(i), result);
        }

        return result;
    }
}
