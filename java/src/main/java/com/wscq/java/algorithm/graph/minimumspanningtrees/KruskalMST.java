package com.wscq.java.algorithm.graph.minimumspanningtrees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/3
 * @describe 最小生成树的Kruskal算法
 */
public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;                        // 最小生成树权重
    private Queue<Edge> mst = new Queue<Edge>();  // 最小生成树变得队列

    /**
     * 通过kruskal算法计算图G的最小生成树
     *
     * @param G 要计算的图
     */
    public KruskalMST(EdgeWeightedGraph G) {
        // 构造一个升序排列的最小边队列
        MinPQ<Edge> pq = new MinPQ();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        // 运行kruskal算法
        UF uf = new UF(G.V());
        //队列为空或者边的数量为V-1时候停止运行
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            //如果v和w不成环
            if (!uf.connected(v, w)) {
                //v和w设置为连通
                uf.union(v, w);
                //这条边加入最小生成树
                mst.enqueue(e);
                //权重累加
                weight += e.weight();
            }
        }

        // 验证结果
        assert check(G);
    }

    /**
     * 返回所有边的迭代器
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * 获取生成树的权重
     */
    public double weight() {
        return weight;
    }

    // 验证结果
    private boolean check(EdgeWeightedGraph G) {

        // check total weight
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
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
     * Unit tests the {@code KruskalMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}