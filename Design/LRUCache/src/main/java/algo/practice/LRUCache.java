package algo.practice;

import java.util.*;

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 *
 * Use a HashMap which maps keys to nodes
 * Nodes should contain the value and next and previous nodes in the doubly linked list
 */
public class LRUCache {
    private Node head;
    private Node tail;
    private int capacity;
    private Map<Integer, Node> map;

    public LRUCache(int capacity) {
        head = null;
        tail = null;
        map = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        if (node != head) {
            removeFromList(node);
            insertAtFront(node);
        }
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            removeKey(key);
        }
        // remove end if at capacity
        if (map.size() >= capacity && tail != null) {
            removeKey(tail.key);
        }
        Node node = new Node(key, value);
        map.put(key, node);
        insertAtFront(node);
    }

    private void insertAtFront(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
            head.prev = null;
        }
    }

    private boolean removeKey(int key) {
        Node node = map.get(key);
        removeFromList(node);
        map.remove(key);
        return true;
    }

    private void removeFromList(Node node) {
        if (node == null) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (head == node) {
            head = node.next;
        }
        if (tail == node) {
            tail = node.prev;
        }
    }

    private class Node {
        public int key;
        public int value;
        public Node prev;
        public Node next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }
}