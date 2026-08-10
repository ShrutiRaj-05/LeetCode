import java.util.HashMap;
import java.util.Map;

class LFUCache {

    // Node storing key, value, and its frequency counter
    private static class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1; // Initial frequency is always 1
        }
    }

    // Custom Doubly Linked List for each frequency bucket
    private static class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeTail() {
            if (size == 0) return null;
            Node lruNode = tail.prev;
            removeNode(lruNode);
            return lruNode;
        }
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, DoublyLinkedList> freqToList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToNode = new HashMap<>();
        this.freqToList = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key)) {
            return -1;
        }

        Node node = keyToNode.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            // Evict LFU (and LRU on tie) if at capacity
            if (keyToNode.size() == capacity) {
                DoublyLinkedList minFreqList = freqToList.get(minFreq);
                Node evictNode = minFreqList.removeTail();
                keyToNode.remove(evictNode.key);
            }

            // Insert new node
            Node newNode = new Node(key, value);
            keyToNode.put(key, newNode);
            minFreq = 1; // Reset minFreq to 1 for new item
            freqToList.computeIfAbsent(1, k -> new DoublyLinkedList()).addHead(newNode);
        }
    }

    // Helper method to increment a node's frequency and move it to the new frequency list
    private void updateFreq(Node node) {
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqToList.get(oldFreq);
        oldList.removeNode(node);

        // If minFreq list becomes empty after removal, increment minFreq
        if (oldFreq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        node.freq++;
        freqToList.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).addHead(node);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */