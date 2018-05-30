package com.wscq.java.algorithm.graph.shortestpaths;

import com.wscq.java.algorithm.util.FileUtil;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/3
 * @describe 使用Dijkstra算法来计算图的最短路径
 */
public class DijkstraSP {
    // distTo[v] = 从顶点s->v路径所需的权重
    private double[] distTo;
    // edgeTo[v] =  s->v 路径上的,到达此顶点的一条边,比如0-2-6.那么edgeTo[2] = 0-2,edgeTo[6]=2-6
    private DirectedEdge[] edgeTo;
    // 索引优先队列,用来保存需要放松的顶点并确认下一个要放松的顶点
    private IndexMinPQ<Double> pq;

    /**
     * 计算从起点s到有向图G中的各个顶点的最短路径算法
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        //异常检测
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }

        //初始化俩数组
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        //有效性验证
        validateVertex(s);

        //disTo除了第一个以外,每个都设置一个极大值
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // 新建深度优先队列,(顶点,顶点对应的值)
        pq = new IndexMinPQ(G.V());
        //添加起点进入队列
        pq.insert(s, distTo[s]);
        StdOut.println("每次循环后优先队列中的数:");
        //队列不为空,一直循环
        while (!pq.isEmpty()) {
            //获取队列中最小值
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                //遍历v的邻接点,并放松
                relax(e);
            }
            for (Integer integer : pq) {
                StdOut.print(integer + ",");
            }
            StdOut.println();
        }

        // check optimality conditions
        assert check(G, s);
    }

    //如果发生改变,放松边以及更新队列pq的信息
    private void relax(DirectedEdge e) {
        //边的起点
        int v = e.from();
        //边的终点
        int w = e.to();
        //如果以前到达w的权重,大于这条边到w的权重,更新为这条边
        if (distTo[w] > distTo[v] + e.weight()) {
            //更新到达w所需要的额权重
            distTo[w] = distTo[v] + e.weight();
            //更新到达w所用到的边
            edgeTo[w] = e;
            //如果包含了当前点,更新当前点的权重信息
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            } else {
                //未包含当前点,加入当前点
                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
     * {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     * {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     * as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * Unit tests the {@code DijkstraSP} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(FileUtil.getFilePath("tinyEWD.txt"));
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        int s = 0;

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }

}