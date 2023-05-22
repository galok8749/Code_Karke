package leetcode.lrucache;

import java.util.*;

/** most recent ----------------- least recent
 * */
public class LRUCache {
    private HashMap<Integer, ListNode> nodeHashMap;
    private Deque<ListNode> list;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.nodeHashMap = new HashMap<>();
        this.list = new ArrayDeque<>();
    }

    public HashMap<Integer, ListNode> getNodeHashMap() {
        return nodeHashMap;
    }

    public void setNodeHashMap(HashMap<Integer, ListNode> nodeHashMap) {
        this.nodeHashMap = nodeHashMap;
    }

    public Deque<ListNode> getList() {
        return list;
    }

    public void setList(Deque<ListNode> list) {
        this.list = list;
    }

    public int get(int key) {
        if (!getNodeHashMap().containsKey(key))
            return -1;

        ListNode curNode = getNodeHashMap().get(key);
        getList().remove(curNode);
        getList().addFirst(curNode);

        return curNode.getVal();
    }
    public void put(int key, int value) {
        if (getNodeHashMap().containsKey(key)) {
            ListNode curNode = getNodeHashMap().get(key);
            getList().remove(curNode);
            curNode.setVal(value);
            getList().addFirst(curNode);
        } else {
            if (getNodeHashMap().size() == capacity) {
                ListNode removedNode = getList().pollLast();
                getNodeHashMap().remove(removedNode.getKey());
            }
            ListNode newNode = new ListNode(value, key);
            getNodeHashMap().put(key, newNode);
            getList().addFirst(newNode);
        }
    }
}
