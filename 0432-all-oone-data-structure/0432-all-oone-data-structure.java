import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class AllOne {

    // Doubly Linked List Node holding a count and all keys with this count
    private static class Node {
        int count;
        Set<String> keys;
        Node prev;
        Node next;

        Node(int count) {
            this.count = count;
            this.keys = new HashSet<>();
        }
    }

    private final Map<String, Node> keyToNode;
    private final Node head;
    private final Node tail;

    public AllOne() {
        keyToNode = new HashMap<>();
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        if (keyToNode.containsKey(key)) {
            Node currNode = keyToNode.get(key);
            int newCount = currNode.count + 1;

            // Check if a node with newCount already exists right after currNode
            Node nextNode = currNode.next;
            if (nextNode == tail || nextNode.count != newCount) {
                nextNode = insertNodeAfter(currNode, newCount);
            }

            // Move key to nextNode
            nextNode.keys.add(key);
            keyToNode.put(key, nextNode);

            // Clean up old node
            currNode.keys.remove(key);
            if (currNode.keys.isEmpty()) {
                removeNode(currNode);
            }
        } else {
            // Key doesn't exist; count will become 1
            Node firstNode = head.next;
            if (firstNode == tail || firstNode.count != 1) {
                firstNode = insertNodeAfter(head, 1);
            }

            firstNode.keys.add(key);
            keyToNode.put(key, firstNode);
        }
    }

    public void dec(String key) {
        Node currNode = keyToNode.get(key);
        int newCount = currNode.count - 1;

        // Remove key from current node
        currNode.keys.remove(key);

        if (newCount == 0) {
            keyToNode.remove(key);
        } else {
            // Check if a node with newCount already exists right before currNode
            Node prevNode = currNode.prev;
            if (prevNode == head || prevNode.count != newCount) {
                prevNode = insertNodeAfter(currNode.prev, newCount);
            }

            prevNode.keys.add(key);
            keyToNode.put(key, prevNode);
        }

        // Clean up empty node
        if (currNode.keys.isEmpty()) {
            removeNode(currNode);
        }
    }

    public String getMaxKey() {
        if (tail.prev == head) {
            return "";
        }
        return tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        if (head.next == tail) {
            return "";
        }
        return head.next.keys.iterator().next();
    }

    // Helper to insert a new node with count after prevNode
    private Node insertNodeAfter(Node prevNode, int count) {
        Node newNode = new Node(count);
        newNode.next = prevNode.next;
        newNode.prev = prevNode;
        prevNode.next.prev = newNode;
        prevNode.next = newNode;
        return newNode;
    }

    // Helper to remove a node from doubly linked list
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */