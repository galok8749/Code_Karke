package leetcode.lrucache;

public class ListNode {
    private int val;
    private int key;

    public ListNode(int val, int key) {
        this.val = val;
        this.key = key;
    }

    public int getVal() {
        return val;
    }

    public int getKey() {
        return key;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", key=" + key +
                '}';
    }
}
