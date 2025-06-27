import java.util.Map;
import java.util.HashMap;

public class MinHeap {

    // A HeapNode list to keep track of each element
    private HeapNode[] Heap;
    // A variable to keep track of the current heap size
    private int size;
    // A map to keep track of vertex and heap key
    private Map<Integer, Integer> posMap;

    // Constructor of MinHeap
    public MinHeap(int maxsize) {
        this.size = 0;
        posMap = new HashMap<>();
        // Heap[0] is not used, Heap is 1 based
        Heap = new HeapNode[maxsize + 1];
    }

    // Calculations
    private int parent(int pos) { return pos / 2; }
    private int leftChild(int pos) { return (2 * pos); }
    private int rightChild(int pos) { return (2 * pos) + 1; }
    private boolean isLeaf(int pos) { return (pos > (size / 2)); }

    // Swap two nodes of the heap
    private void swap(int fpos, int spos) {
        HeapNode tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
        posMap.put(Heap[fpos].v, fpos);
        posMap.put(Heap[spos].v, spos);
    }

    // Insert a node
    public void insert(HeapNode node) {
        size++;
        Heap[size] = node;
        posMap.put(node.v, size);
        minHeapifyUp(size);
    }

    // Heapify for inserting nodes at the end, moves elements up
    public void minHeapifyUp(int pos) {
        while (pos > 1 && Heap[pos].dv < Heap[parent(pos)].dv) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    // Heapify for constructing a heap, moves elements down
    public void minHeapify(int pos) {
        int smallest = pos;
        int left = leftChild(pos);
        int right = rightChild(pos);

        if (left <= size && Heap[left].dv < Heap[smallest].dv) {
            smallest = left;
        }
        if (right <= size && Heap[right].dv < Heap[smallest].dv) {
            smallest = right;
        }
        if (smallest != pos) {
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    public HeapNode extractMin() {
        HeapNode minNode = Heap[1];
        posMap.remove(minNode.v);
        Heap[1] = Heap[size];
        posMap.put(Heap[1].v, 1);
        size--;
        minHeapify(1);
        return minNode;
    }

    // Decrease the key of a node, and move it up the heap
    public void decreaseKey(int vertex, int distance) {
        int v = posMap.get(vertex);
        Heap[v].dv = distance;
        minHeapifyUp(v);
    }

    // Verify that an element exists in priority queue
    public boolean contains(int vertex) {
        return posMap.containsKey(vertex);
    }

    // For detection when priority queue is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
