package com.wscq.java.algorithm.graph.undirectedgraph;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/26
 * @describe 判断图是不是无环图
 */
public class Cycle {
    private boolean[] marked; //是否标记过该顶点
    private int[] edgeTo; //结束点
    private Stack<Integer> cycle;//记录环(不固定,有就记录,不全记录)

    /**
     * Determines whether the undirected graph {@code G} has a cycle and,
     * if so, finds such a cycle.
     *
     * @param G the undirected graph
     */
    public Cycle(Graph G) {
        if (hasSelfLoop(G)) {
            return;
        }
        if (hasParallelEdges(G)) {
            return;
        }
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, -1, v);
            }
        }
    }


    // 这个图是个自环吗
    private boolean hasSelfLoop(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    // 这个图有两个平行边吗?
    private boolean hasParallelEdges(Graph G) {
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {

            // check for parallel edges incident to v
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }

            // reset so marked[v] = false for all v
            for (int w : G.adj(v)) {
                marked[w] = false;
            }
        }
        return false;
    }

    /**
     * 图中是否有环
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * 返回图中的一个环
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * 深度遍历树,寻找环
     *
     * @param G 当前树
     */
    private void dfs(Graph G, int u, int v) {
        StdOut.println("(" + u + "," + v + ")");
        marked[v] = true;
        for (int w : G.adj(v)) {//遍历所有和V相连的顶点
            // 如果已经找到环,结束
            if (cycle != null) {
                return;
            }

            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, v, w);
            } else if (w != u) {//除了自己以外,这个顶点还有别的被标记过,说明是个环(因为只会路过才回标记,找到标记即为闭环)
                // 构造一个环,方便输出
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {//按照标记路径反向入栈,就找出当前路径了
                    cycle.push(x);
                    StdOut.println(x);
                }
                cycle.push(w);//再加入路径的首位,就是一个完整的环.
                cycle.push(v);
            }
        }
    }

    /**
     * Unit tests the {@code Cycle} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Graph G = Graph.getGraph(13, new Integer[]{
                0, 2,
                9, 11,
                0, 5,
                3, 5,
                0, 1,
                3, 4,
                4, 5,
                0, 6,
                4, 6,
                7, 8,
                9, 10,
                9, 12,
                11, 12
        });
        Cycle finder = new Cycle(G);
        if (finder.hasCycle()) {
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("Graph is acyclic");
        }
        StdOut.println(finder.hasSelfLoop(G));
        StdOut.println(finder.hasParallelEdges(G));
        StdOut.println(G.toString());
    }


}
