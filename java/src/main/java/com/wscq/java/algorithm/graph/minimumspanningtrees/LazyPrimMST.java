package com.wscq.java.algorithm.graph.minimumspanningtrees;

import com.wscq.java.algorithm.util.FileUtil;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/2
 * @describe 最小生成树的Prim算法
 * 找到一个顶点,把顶点连的边加入队列
 * 在队列中找到最小权重边加入生成树
 * 边两端的顶点连的边加入队列(要去掉已经加过的,就是已标记的)
 * 再从队列里面找最小的加入生成树
 * 然后重复上述两个步骤
 */
public class LazyPrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;       // 最小生成树的总权重
    private Queue<Edge> mst;     // 最小生成树边对应的队列
    private boolean[] marked;    // marked[v] = 标记当前顶点是否在生成树中
    private MinPQ<Edge> pq;      // 横切边(包括失效的边)

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     *
     * @param G the edge-weighted graph
     */
    public LazyPrimMST(EdgeWeightedGraph G) {
        mst = new Queue();
        pq = new MinPQ();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {  // run Prim from all vertices to
            if (!marked[v]) {
                // get a minimum spanning forest
                prim(G, v);
            }
        }

        // check optimality conditions
        assert check(G);
    }

    // 运行prim算法
    private void prim(EdgeWeightedGraph G, int s) {
        //先添加顶点
        scan(G, s);
        // better to stop when mst has V-1 edges
        //在mst的填充内容等于V-1时候结束更好
        while (!pq.isEmpty()) {
            //从pq中得到权重最小的边
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            // 跳过失效的边
            if (marked[v] && marked[w]) {
                continue;
            }

            //将边添加到树中,总的权重增加
            mst.enqueue(e);
            weight += e.weight();

            if (!marked[v]) {
                // 将顶点V添加到树中
                scan(G, v);
            }
            if (!marked[w]) {
                // 将顶点W添加到树中
                scan(G, w);
            }
        }
    }

    // add all edges e incident to v onto pq if the other endpoint has not yet been scanned
    //迭代G中所有包含V的边,如果另一个端点没有被标记,则添加这条边到队列
    private void scan(EdgeWeightedGraph G, int v) {
        //当前顶点标记为true
        marked[v] = true;
        //遍历所有含有顶点V的边
        for (Edge e : G.adj(v)) {
            //边的另一个端点没有标记
            if (!marked[e.other(v)]) {
                //这条边插入队列
                pq.insert(e);
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     *
     * @return the edges in a minimum spanning tree (or forest) as
     * an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     *
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    /**
     * Unit tests the {@code LazyPrimMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(FileUtil.getFilePath("tinyEWG.txt"));
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}
