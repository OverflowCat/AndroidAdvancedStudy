package com.wscq.java.algorithm.graph.undirectedgraph;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/27
 * @describe 间隔度数算法, 用来寻找一个图中的最短路径
 */
public class DegreesOfSeparation {
    // this class cannot be instantiated
    private DegreesOfSeparation() {
    }

    public static void main(String[] args) {
        //从输入读取一个文件名,分隔符和源文件,已改为直接写死
        String filename = "java/src/main/java/file/movies.txt";
        String delimiter = "/";
        String source = "Ron";

        //新建一个图文符号表
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        //获取图文符号表中的图
        Graph G = sg.graph();
        //如果不包含要查找的字符串,输出
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }

        //获取要查找的字符串索引
        int s = sg.indexOf(source);
        //新建一个广度优先路径算法类
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        //不断从输入流读取字符串,并计算最短路径
//        while (!StdIn.isEmpty()) {
        String sink = "Fernando";
        if (sg.contains(sink)) {
            int t = sg.indexOf(sink);
            if (bfs.hasPathTo(t)) {
                for (int v : bfs.pathTo(t)) {
                    StdOut.println("   " + sg.nameOf(v));
                }
            } else {
                StdOut.println("Not connected");
            }
        } else {
            StdOut.println("   Not in database.");
        }
//        }
    }
}