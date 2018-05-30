package com.wscq.java.algorithm.graph.minimumspanningtrees;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/2
 * @describe 用来表示加权无向图中的一条表
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    /**
     * 通过顶点 {@code v} 和 {@code w} 生成
     * 一条权重为{@code weight}的边
     *
     * @param v      一个顶点
     * @param w      另一个顶点
     * @param weight 这条边的权重
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *                                  is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int v, int w, double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * 找到和顶点{@code vertex}相连的顶点,不存在时候抛出异常
     *
     * @param vertex 边的一个端点
     * @return 边的另一个端点
     * @throws IllegalArgumentException 如果顶点不是边中的任意一个端点
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    /**
     * 比较两条边的权重
     *
     * @param that 比较的边
     * @return 返回一个正整数/零或负整数,返回结果取决于两条边的权重
     * 相等返回0,传入的参数小返回负数,传入的参数大返回正数
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    /**
     * 以String形式输出一条边
     *
     * @return 一条边的String形式
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    /**
     * Unit tests the {@code Edge} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Edge e = new Edge(12, 34, 5.67);
        StdOut.println(e);
    }
}
