package com.wscq.java.algorithm.graph.undirectedgraph;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/25
 * @describe 简单的图
 */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * 建立顶点V个,无边图
     *
     * @param V 顶点个数
     * @throws IllegalArgumentException 顶点个数不能小于0,否则抛异常
     */
    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];//创建邻接表
        for (int v = 0; v < V; v++) {//初始每个邻接表数量为空
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * 从输入流读取两个int值,分别作为键和值来使用
     *
     * @param in 输入流
     * @throws IllegalArgumentException 边的数量为负时
     * @throws IllegalArgumentException 定点数量为负时
     * @throws IllegalArgumentException 输入流的格式错误
     */
    public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            }
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            }
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }


    /**
     * 拷贝{@code G}到一个新的图
     *
     * @param G 一个图
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    /**
     * 获取图的顶点数量
     *
     * @return 图的顶点数量
     */
    public int V() {
        return V;
    }

    /**
     * 获取图中边的数量
     *
     * @return 图中边的数量
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 添加无向边到当前图
     *
     * @param v 边的一个顶点
     * @param w 边的另一个顶点
     * @throws IllegalArgumentException 与这个顶点相连的顶点需要满足如下规范{@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        //数量+1
        E++;
        //在顶点v对应的组下加入w
        adj[v].add(w);
        //在顶点w对应的组下加入v
        adj[w].add(v);
    }


    /**
     * 当前顶点相连的所有顶点的迭代
     *
     * @param v 当前顶点
     * @return 这个 {@code v}包含的所有顶点的迭代器
     * @throws IllegalArgumentException 当前顶点有效性验证 {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 和顶点 {@code v}相连顶点的数量
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }


    /**
     * 顶点格式化输出
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


    /**
     * 测试方法
     */
    public static void main(String[] args) {
        String[] s = new String[]{"1", "2", "2", "3", "3", "5", "5", "2", "2", "6", "6", "1", "5", "1"};
        Graph G = new Graph(7);
        for (int i = 0; i < s.length; i += 2) {
            G.addEdge(Integer.valueOf(s[i]), Integer.valueOf(s[i + 1]));
        }
        StdOut.println(G);
    }

    public static Graph getGraph() {
        Integer[] s = new Integer[]{0, 1,
                1, 2,
                1, 3,
                0, 3,
                2, 3,
                2, 6,
                3, 6,
                5, 6,
                6, 6};
        Graph G = new Graph(7);
        for (int i = 0; i < s.length; i += 2) {
            G.addEdge(s[i], s[i + 1]);
        }
        return G;
    }

    public static Graph getGraph(Integer[] intArrs) {
        Graph G = new Graph(7);
        for (int i = 0; i < intArrs.length; i += 2) {
            G.addEdge(intArrs[i], intArrs[i + 1]);
        }
        return G;
    }

    public static Graph getGraph(int count, Integer[] intArrs) {
        Graph G = new Graph(count);
        for (int i = 0; i < intArrs.length; i += 2) {
            G.addEdge(intArrs[i], intArrs[i + 1]);
        }
        return G;
    }

}
