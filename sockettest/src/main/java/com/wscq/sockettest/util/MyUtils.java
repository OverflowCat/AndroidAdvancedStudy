package com.wscq.sockettest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/6/4
 * @describe
 */
public class MyUtils {
    public static void close(BufferedReader in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PrintWriter out) {
        out.close();
    }
}
