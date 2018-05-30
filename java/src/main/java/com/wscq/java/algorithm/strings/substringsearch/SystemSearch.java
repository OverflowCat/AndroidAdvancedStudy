package com.wscq.java.algorithm.strings.substringsearch;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/14
 * @describe 系统搜索??
 */
public class SystemSearch {


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        String text = "a";
        String query = "a";
        for (int i = 0; i < n; i++) {
            text = text + text;
            query = query + query;
        }
        text = text + text;
        query = query + "b";
        StdOut.println(text.indexOf(query));
    }
}
