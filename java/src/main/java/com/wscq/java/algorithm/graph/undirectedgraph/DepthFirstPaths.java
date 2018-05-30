package com.wscq.java.algorithm.graph.undirectedgraph;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/26
 * @describe {@code DepthFirstPaths}是一种数据模型,用来寻找和顶点<em>s</em>相连通的顶点,或者路径
 */
public class DepthFirstPaths {
    private boolean[] marked;    // 是否存在从s到v的路径
    private int[] edgeTo;        // 从s~v路径上的最后一个顶点,比如1-2-3-4-6,值是4
    private final int s;         // 开始的顶点

    /**
     * 计算从顶点{@code s}到图 {@code G}中的各个顶点的路径
     *
     * @param G 要计算的树
     * @param s 开始的顶点
     * @throws IllegalArgumentException 顶点不能超出范围 {@code 0 <= s < V}
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    // 在树G种对V进行深度遍历
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                //因为每次都是加上一个顶点,所以对两个顶点存在两条路径的情况,他只会记录下一条
                //无法选择两点间最短路径,要寻找最短路径需用到广度优先算法
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * 顶点 {@code s} 和顶点 {@code v}之间是否存在连通路径
     *
     * @param v 要连通的顶点
     * @return {@code true} 路径存在 {@code false} 其他
     * @throws IllegalArgumentException v范围验证 {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 返回起点 {@code s} 和顶点 {@code v}之间的路径,如果
     * 返回{@code null} 说明不存在连通路径
     *
     * @param v 要查找的顶点
     * @return 起点 {@code s}和顶点 {@code v}之间所有路径的迭代器
     * @throws IllegalArgumentException 有效性验证 {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        //新建一个存放路径的栈
        Stack<Integer> path = new Stack();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    // 有效性验证 {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        Graph G = Graph.getGraph();
        int s = 0;
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                //某个到某个,标题
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) {//是当前顶点,输出当前顶点
                        StdOut.print(x);
                    } else {//对栈进行输出
                        StdOut.print("-" + x);
                    }
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }

}
