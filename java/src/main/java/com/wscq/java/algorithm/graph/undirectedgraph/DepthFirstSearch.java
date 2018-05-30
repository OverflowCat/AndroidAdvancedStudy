package com.wscq.java.algorithm.graph.undirectedgraph;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/26
 * @describe
 */
public class DepthFirstSearch {
    private boolean[] marked;    // 用来记录起点连通的所有顶点,下标v代表顶点,值代表是否连通
    private int count;           // number of vertices connected to s

    /**
     * Computes the vertices in graph {@code G} that are
     * connected to the source vertex {@code s}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearch(Graph G, int s) {
        //建立一个和顶点数量相同的记录数组
        marked = new boolean[G.V()];
        //有效性验证
        validateVertex(s);
        //深度优先遍历顶点
        dfs(G, s);
    }

    //从顶点v开始深度遍历图
    //在访问一个顶时,将它标记为已访问
    //递归的访问它所有没有被访问的邻居顶点
    private void dfs(Graph G, int v) {
        //连通数加一
        count++;
        marked[v] = true;
        //遍历v下面所有连通顶点
        for (int w : G.adj(v)) {
            //如果对应的下标没有显示连通
            if (!marked[w]) {
                //递归,把w置为连通,然后在设置和W连通的子节点为连通
                //层层调用,直到和v直接或间接相连的顶点都标记完全
                dfs(G, w);
            }
        }
    }

    /**
     * 源顶点{@code s}和目标顶点 {@code v}是否连通
     *
     * @param v 目标顶点
     * @return {@code true} 如果是连通的 {@code false}不是连通的
     * @throws IllegalArgumentException 如果v是无效的 {@code 0 <= v < V}
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 返回和顶点 {@code s}连通的所有顶点数量
     *
     * @return 和顶点{@code s}连通的所有顶点数量
     */
    public int count() {
        return count;
    }

    // 验证顶点是否越界 {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        String[] s = new String[]{"1", "2", "2", "3", "3", "5", "5", "2", "2", "6", "6", "1", "5", "1", "1", "4", "1", "0"};
        Graph G = new Graph(7);
        for (int i = 0; i < s.length; i += 2) {
            G.addEdge(Integer.valueOf(s[i]), Integer.valueOf(s[i + 1]));
        }

        int index = 6;
        DepthFirstSearch search = new DepthFirstSearch(G, index);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }

        StdOut.println();
        if (search.count() != G.V()) {//是否是连通图
            StdOut.println("NOT connected");
        } else {
            StdOut.println("connected");
        }
        StdOut.print("count:" + search.count);
    }

}
