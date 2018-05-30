package com.wscq.java.algorithm.graph.minimumspanningtrees;

import com.wscq.java.algorithm.util.FileUtil;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/2
 * @describe 即时的prim算法
 */
public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    // edgeTo[v] = 顶点对应的最小生成树的边,因为只有v-1条边,所以会有某个值为null
    //运算开始就是找v相连的权重最小的顶点来对应边的,所以数组edgeTo的0位置为null
    private Edge[] edgeTo;
    private double[] distTo;      // distTo[v] = edgeTo[v].weight()
    private boolean[] marked;     // marked[v] = 如果v在树中则为true
    private IndexMinPQ<Double> pq;//有效的横切边

    /**
     * 计算图G中最小的生成树.
     *
     * @param G 边加权的图
     */
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ(G.V());
        for (int v = 0; v < G.V(); v++) {
            //放一个正无穷
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        //遍历每个顶点
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) {
                //如果未被标记,运行prim算法
                prim(G, v);
            }

        // 检测最优条件
        assert check(G);
    }

    // 从图中的顶点s开始运行prim算法
    private void prim(EdgeWeightedGraph G, int s) {
        //开水遍历的顶点不放入数组,也不记录权重
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);//放入最小横切边队列
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    //扫描顶点v
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        //获取v的所有边,取他们中权重最小的一个放入
        for (Edge e : G.adj(v)) {
            //因为开始取得就是另一个顶点,v也就不会参与到放置过程,所以开始的顶点对应的边为null
            //获取另一个顶点
            int w = e.other(v);
            //如果另一个顶点被标记过,跳出
            if (marked[w]) {
                continue;
            }
            //如果这条边的权重小于disTo[w]
            if (e.weight() < distTo[w]) {
                //更新权重和顶点对应的边
                distTo[w] = e.weight();
                edgeTo[w] = e;
                //如果最小生成树中包含w,更新w对应的weight,否则加入到pq中
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
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
        Queue<Edge> mst = new Queue();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * 获取最小生成树的权重之和
     */
    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }


    //检查最优性条件(需要时间正比于E V lg *)
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
            for (Edge f : edges()) {
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
     * Unit tests the {@code PrimMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(FileUtil.getFilePath("tinyEWG.txt"));
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        StdOut.printf(Arrays.toString(mst.edgeTo));
    }
}