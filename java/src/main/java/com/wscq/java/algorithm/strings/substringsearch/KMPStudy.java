package com.wscq.java.algorithm.strings.substringsearch;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/15
 * @describe
 */
public class KMPStudy {
    private int R = 3;
    private int[][] dfa;       // the KMP automoton

    private char[] pattern;    // 模式(pattern)的字符数组
    private String pat;        // 要搜索的字符串

    public void KMP(String pat) {
        this.pat = pat;
        int m = pat.length();
        dfa = new int[R][m];
        dfa[getIndex(pat.charAt(0))][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            StdOut.println("x = " + x + ",j = " + j + ",循环会把dfa[c][x]赋值到dfa[c][j]");
            StdOut.println("====================================================================================");
            printDfa();
            StdOut.println("\n-------复制匹配失败下的值-------");

            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][x];
                StdOut.println("dfa[" + c + "][" + j + "] = dfa[" + c + "][" + x + "]" + " = " + dfa[c][x]);
            }
            StdOut.println();
            printDfa();
            StdOut.println("\n-----设置匹配成功情况下的值-----");

            dfa[getIndex(pat.charAt(j))][j] = j + 1;

            StdOut.println("dfa[getIndex(" + pat.charAt(j) + ")][" + j + "] = j + 1 = " + (j + 1) + "\n");

            printDfa();
            StdOut.println("\n---------更新重启状态---------");
            x = dfa[getIndex(pat.charAt(j))][x];
            StdOut.println("x = dfa[getIndex(" + pat.charAt(j) + ")][" + x + "]" + " = " + x);
            StdOut.println("====================================================================================");
            StdOut.println();
            StdOut.println();
        }
    }


    private void printDfa() {
        char[] arr = {'a', 'b', 'c'};
        StdOut.println("     " + "[0, 1, 2, 3, 4, 5]");
        for (int i = 0; i < dfa.length; i++) {
            StdOut.println(arr[i] + "(" + i + ")" + ":" + Arrays.toString(dfa[i]));
        }
    }

    private int getIndex(char c) {
        char[] arr = {'a', 'b', 'c'};
        for (int i = 0; i < arr.length; i++) {
            if (c == arr[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        KMPStudy study = new KMPStudy();
        study.KMP("ababac");
    }
}
