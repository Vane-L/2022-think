package test;


/**
 * @Author: wenhongliang
 */
public class DoubleLinkList {
    class Node {
        public Integer key;
        public Integer value;
        public Node pre;
        public Node next;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    public Node head = null;
    public Node tail = null;

    public void add(Integer key, Integer value) {
        Node node = new Node(key, value);
        if (head == null) {
            head = tail = node;
            return;
        }
        Node cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) {
                cur.value = value;
                return;
            }
            cur = cur.next;
        }
        tail.next = node;
        node.pre = tail;
        tail = node;
    }

    public Node get(int key) {
        Node cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

}
