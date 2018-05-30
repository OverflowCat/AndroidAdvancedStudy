package com.wscq.java.algorithm.select;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/23
 * @describe 顺序查找符号表
 */
public class SequentialSearchST<Key, Value> {
    private int n;           // 键值对数量
    private Node first;      // 第一个主句

    // a helper linked list data type
    private class Node {
        private Key key;//键
        private Value val;//值
        private Node next;//下个键值对

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 空构造
     */
    public SequentialSearchST() {
    }

    /**
     * 返回符号表的长度
     *
     * @return 符号表的长度
     */
    public int size() {
        return n;
    }

    /**
     * 符号表为空时候返回true.
     *
     * @return {@code true} 符号表为空
     * {@code false} 符号表不为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 如果符号表包含当前key,返回true
     *
     * @param key 要查找的key
     * @return {@code true} 如果包含 {@code key};
     * {@code false} 不包含{@code key}
     * @throws IllegalArgumentException 如果 {@code key} 是 {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * 顺序查找对应的值
     *
     * @param key 当前key
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    /**
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        //遍历,如果有,覆盖
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        //在首位插入
        first = new Node(key, val, first);
        n++;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        //依次递归直到找到当前key,数量减一,返回下一个,达到删除目的
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }


    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }


    /**
     * Unit tests the {@code SequentialSearchST} data type.
     *
     * @param args the command-line arguments
     */
    public static void
    main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}