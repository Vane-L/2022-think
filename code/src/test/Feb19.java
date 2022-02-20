package test;

/**
 * @Author: wenhongliang
 */
public class Feb19 {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        hashTable.insert(new Node(1, 1));
        hashTable.insert(new Node(1, 2));
        hashTable.insert(new Node(17, 3));
        System.out.println(hashTable.get(1));
        System.out.println(hashTable.get(17));
    }

    /**
     * 实现hash表，支持insert和get函数.
     * //1 hash函数可以简单实现，2 hash冲突用双向链表处理,双向链表需自己实现 3 key类型可以为int、string、模板； 4写测试用例测试：
     * 4.1 insert一个key、value；然后get key获取对应的value
     * 4.2 insert相同key，不同value，然后get key获取对应的value
     * 4.3 测试hash冲突
     */
    static class HashTable {
        public int size;
        public DoubleLinkList[] tmp;

        public HashTable() {
            this(16);
        }

        public HashTable(int capacity) {
            this.size = capacity;
            tmp = new DoubleLinkList[size];
        }

        public int get(Integer key) {
            // 获取idx,再通过key获取value
            int idx = key % size;
            DoubleLinkList head = tmp[idx];
            if (head == null || head.get(key) == null) return -1;
            return head.get(key).value;
        }

        public void insert(Node node) {
            // 找到idx
            // 如果已有节点，tail=node,tail=null
            // 如果没有，head=null,tail=null=>head=node,tail=
            int idx = getIndex(node);
            if (tmp[idx] == null) {
                DoubleLinkList head = new DoubleLinkList();
                head.add(node.key, node.value);
                tmp[idx] = head;
            } else {
                tmp[idx].add(node.key, node.value);
            }
        }

        public int getIndex(Node node) {
            Integer key = node.key;
            if (key == null) throw new NullPointerException();
            return key % size;
        }
    }

    static class Node {
        public Integer key;
        public Integer value;
        public Node next;

        public Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    static class ListNode {
        // 头尾的节点
        public ListNode head;
        public ListNode tail;

        // 节点的前后指针
        public ListNode pre = null;
        public ListNode next = null;

        // 节点的对象
        public Node node;

        public ListNode() {
            head = tail = null;
        }

        public ListNode(Node node) {
            head = tail = null;
            this.node = node;
        }

        public void add(Node node) {
            // 先找有没有key相等的，有则更新
            ListNode cur = head;
            while (cur != null) {
                if (cur.node.key.equals(node.key)) {
                    cur.node.value = node.value;
                    return;
                }
                cur = cur.next;
            }
            // 没有则新增
            cur = new ListNode(node);
            cur.next = tail;
        }

        public Node get(Integer key) {
            ListNode cur = head;
            while (cur != null) {
                if (cur.node.key.equals(key)) {
                    return cur.node;
                }
                cur = cur.next;
            }
            return null;
        }

    }
}
