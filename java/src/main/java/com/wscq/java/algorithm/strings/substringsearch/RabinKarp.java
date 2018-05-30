package com.wscq.java.algorithm.strings.substringsearch;

import java.math.BigInteger;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
/******************************************************************************
 *  Compilation:  javac RabinKarp.java
 *  Execution:    java RabinKarp pat txt
 *  Dependencies: StdOut.java
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  Las Vegas version of the Rabin-Karp algorithm.
 *
 *  % java RabinKarp abracadabra abacadabrabracabracadabrabrabracad
 *  pattern: abracadabra
 *  text:    abacadabrabracabracadabrabrabracad
 *  match:                 abracadabra
 *
 *  % java RabinKarp rab abacadabrabracabracadabrabrabracad
 *  pattern: rab
 *  text:    abacadabrabracabracadabrabrabracad
 *  match:           rab
 *
 *  % java RabinKarp bcara abacadabrabracabracadabrabrabracad
 *  pattern: bcara
 *  text:         abacadabrabracabracadabrabrabracad
 *
 *  %  java RabinKarp rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java RabinKarp abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ******************************************************************************/

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/14
 * @describe 使用Rabin-Karp算法找到第一次出现在文本中的字符串
 */
public class RabinKarp {
    //模式字符串,仅拉斯维加斯算法需要
    private String pat;      // the pattern  // needed only for Las Vegas
    //模式字符串的散列值
    private long patHash;    // pattern hash value
    //模式字符串的长度
    private int m;           // pattern length
    //一个很大的素数
    private long q;          // a large prime, small enough to avoid long overflow
    //字母表大小
    private int R;           // radix
    //R^(M-1) % 0
    private long RM;         // R^(M-1) % Q

    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     * @param R       the alphabet size
     */
    public RabinKarp(char[] pattern, int R) {
        this.pat = String.valueOf(pattern);
        this.R = R;
        throw new UnsupportedOperationException("Operation not supported yet");
    }

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public RabinKarp(String pat) {
        //保存模式字符串
        this.pat = pat;      // save pattern (needed only for Las Vegas)
        R = 256;
        m = pat.length();
        q = longRandomPrime();

        // 计算 R^(m-1) % q
        RM = 1;
        for (int i = 1; i <= m - 1; i++) {
            //用于减去第一个数字时的计算
            RM = (R * RM) % q;
        }
        patHash = hash(pat, m);
    }

    // Compute hash for key[0..m-1].
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++) {
            h = (R * h + key.charAt(j)) % q;
        }
        return h;
    }

    // Las Vegas version: does pat[] match txt[i..i-m+1] ?
    private boolean check(String txt, int i) {
        for (int j = 0; j < m; j++) {
            if (pat.charAt(j) != txt.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }

    // Monte Carlo version: always return true
    // private boolean check(int i) {
    //    return true;
    //}

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string
     * in the text string; n if no such match
     */
    public int search(String txt) {
        //在文本中检查相等的散列值
        int n = txt.length();
        if (n < m) {
            //查找的字符串比文本长,返回未命中
            return n;
        }
        //计算前m个文本的hash值
        long txtHash = hash(txt, m);

        // check for match at offset 0
        //一开始就匹配成功的情况
        if ((patHash == txtHash) && check(txt, 0)) {
            return 0;
        }

        // 检查哈希匹配;如果哈希匹配,检查精确匹配
        for (int i = m; i < n; i++) {
            //减去一个数字,加上最后一个数字,再次检查匹配
            //就是通过一个神奇的数学算法,算出m个字母的hash值,并依次向后递归计算
            //比直接调用hash(tet,m)的方法要少好多计算量
            txtHash = (txtHash + q - RM * txt.charAt(i - m) % q) % q;
            txtHash = (txtHash * R + txt.charAt(i)) % q;

            int offset = i - m + 1;
            //如果hash值相等,且检查后的值相等
            if ((patHash == txtHash) && check(txt, offset)) {
                //找到匹配
                return offset;
            }
        }
        //未找到匹配
        return n;
    }


    // a random 31-bit prime
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print results
        StdOut.println("text:    " + txt);

        // from brute force search method 1
        StdOut.print("pattern: ");
        for (int i = 0; i < offset; i++)
            StdOut.print(" ");
        StdOut.println(pat);
    }
}
