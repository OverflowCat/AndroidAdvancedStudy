package com.wscq.java.algorithm.graph.undirectedgraph;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/26
 * @describe 搜索某两个点是否连通
 * 这段Graph 的用例使得它的用例可以独立处理一幅图中的每个连通分量。这里的实现是基于一个由顶点
 * 索引的数组id[]。如果v属于第i个 连通分量,则id[v] 的值为i。构造函数会找出一个未被标记的顶点
 * 并调用递归函数dfs() 来标记并区分出所有和它连通的顶点，如此重复直到所有的顶点都被标记并区分。
 * connectedO)、count() 和id()方法的实现非常简单
 */
public class CC {
    private boolean[] marked;   // marked[v] = 顶点v是否被标记
    private int[] id;           // 顶点V所属连通分量的ID,id从0开始编号,对应在size中
    private int[] size;         // 每个id对应连通分量包含的顶点数
    private int count;          // 连通分量数量

    /**
     * 计算无向图{@code G}的连通分量
     *
     * @param G 无向图
     */
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {//如果是全连通,那么第一次就会标记完,count=1,size = [G.V() ,0...],id =[0,0...]
                dfs(G, v);
                count++;//多一条连通分量,会往后移动,最多不会超过G.V()
            }
        }
    }

    /**
     * Computes the connected components of the edge-weighted graph {@code G}.
     *
     * @param G the edge-weighted graph
     */
    public CC(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    // 使用深度优先遍历,找出和顶点V连通的顶点
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;//记录和这个顶点连通的顶点数
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    // depth-first search for an EdgeWeightedGraph
    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }


    /**
     * 返回顶点v所属于的联通分量id
     *
     * @param v the vertex
     * @return 包含顶点v的联通分量id
     * @throws IllegalArgumentException 有效性验证 {@code 0 <= v < V}
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     * 包含该顶点的连通分量数
     *
     * @param v the vertex
     * @return the number of vertices in the connected component containing vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * Returns the number of connected components in the graph {@code G}.
     *
     * @return the number of connected components in the graph {@code G}
     */
    public int count() {
        return count;
    }

    /**
     * 如果v和w属于同一条连通分量,返回true
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    /**
     * w和v是否在同一个连通分量中
     *
     * @param v one vertex
     * @param w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     * connected component; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     * @deprecated Replaced by {@link #connected(int, int)}.
     */
    @Deprecated
    public boolean areConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    // 有效性验证
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * Unit tests the {@code CC} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Graph G = Graph.getGraph(13, new Integer[]{
                0, 6,
                0, 2,
                0, 1,
                0, 5,
                3, 5,
                3, 4,
                4, 5,
                4, 6,
                7, 8,
                9, 11,
                9, 10,
                9, 12,
                11, 12
        });
        CC cc = new CC(G);

        // 连通分量的数量
        int m = cc.count();
        StdOut.println(m + " components");

        // 计算每个连通分量的顶点列表
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        // 结果输出
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
//        StdOut.println(Arrays.toString(cc.marked));
//        StdOut.println(Arrays.toString(cc.id));
//        StdOut.println(Arrays.toString(cc.size));
//
//        StdOut.println(G.toString());
    }
}
