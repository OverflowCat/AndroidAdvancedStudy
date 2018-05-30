package com.wscq.java.algorithm.graph.directedgraphs;

import com.wscq.java.algorithm.util.FileUtil;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/28
 * @describe 计算有向图的传递闭包(为啥翻译成这个鬼样子)
 * 主要用来计算可达性,一个数组来记录,如果w->v可以连通, 说明是可达的
 *  % 文件tinyDG.txt中的可达性示意,T表示可达(规定每个顶点都是可达自身的)
 *         0  1  2  3  4  5  6  7  8  9 10 11 12
 *  --------------------------------------------
 *    0:   T  T  T  T  T  T
 *    1:      T
 *    2:   T  T  T  T  T  T
 *    3:   T  T  T  T  T  T
 *    4:   T  T  T  T  T  T
 *    5:   T  T  T  T  T  T
 *    6:   T  T  T  T  T  T  T        T  T  T  T
 *    7:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    8:   T  T  T  T  T  T  T  T  T  T  T  T  T
 *    9:   T  T  T  T  T  T           T  T  T  T
 *   10:   T  T  T  T  T  T           T  T  T  T
 *   11:   T  T  T  T  T  T           T  T  T  T
 *   12:   T  T  T  T  T  T           T  T  T  T
 *
 ******************************************************************************/

public class TransitiveClosure {
    private DirectedDFS[] tc;  // tc[v] = 记录每个顶点的深度遍历

    /**
     * Computes the transitive closure of the digraph {@code G}.
     *
     * @param G the digraph
     */
    public TransitiveClosure(Digraph G) {
        tc = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            tc[v] = new DirectedDFS(G, v);
    }

    /**
     * Is there a directed path from vertex {@code v} to vertex {@code w} in the digraph?
     *
     * @param v the source vertex
     * @param w the target vertex
     * @return {@code true} if there is a directed path from {@code v} to {@code w},
     * {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @throws IllegalArgumentException unless {@code 0 <= w < V}
     */
    public boolean reachable(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return tc[v].marked(w);
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = tc.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * Unit tests the {@code TransitiveClosure} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(FileUtil.getFilePath("tinyDG.txt"));
        Digraph G = new Digraph(in);

        TransitiveClosure tc = new TransitiveClosure(G);

        // print header
        StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            StdOut.printf("%3d", v);
        StdOut.println();
        StdOut.println("--------------------------------------------");

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.reachable(v, w)) StdOut.printf("  T");
                else StdOut.printf("   ");
            }
            StdOut.println();
        }
    }

}
