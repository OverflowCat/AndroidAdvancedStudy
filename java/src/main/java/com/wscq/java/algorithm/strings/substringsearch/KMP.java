package com.wscq.java.algorithm.strings.substringsearch;
/******************************************************************************
 *  Compilation:  javac KMP.java
 *  Execution:    java KMP pattern text
 *  Dependencies: StdOut.java
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  KMP algorithm.
 *
 *  % java KMP abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:               abracadabra
 *
 *  % java KMP rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:         rab
 *
 *  % java KMP bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                                   bcara
 *
 *  % java KMP rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java KMP abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ******************************************************************************/

import com.wscq.java.algorithm.strings.stringsort.Alphabet;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

import static com.wscq.java.algorithm.strings.stringsort.Alphabet.LOWERCASE;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/14
 * @describe 使用Knuth-Morris-Pratt算法搜索匹配的字符串
 */
public class KMP {
    private int[][] dfa;       // the KMP automoton

    private char[] pattern;    // 模式(pattern)的字符数组
    private String pat;        // 要搜索的字符串

    /**
     * 预处理要搜索的字符串
     *
     * @param pat 要搜索的字符串
     */
    public KMP(String pat) {
        this.pat = pat;

        // 通过pattern新建DFA数组
        //长度和要搜索字符串等长
        int m = pat.length();
        //建立一个长度为[R][m]的二维数组
        dfa = new int[LOWERCASE.radix()][m];
        //pat第一个字符对应的数组对应的第一个值为1
        dfa[LOWERCASE.toIndex(pat.charAt(0))][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            //j从1开始直到搜索达到药检索的字符串长度
            //c是直接循环了整个字母表
            //x在第一次循环时候一直等于0
            for (int c = 0; c < LOWERCASE.radix(); c++) {
                // 复制不匹配的情况?
                dfa[c][j] = dfa[c][x];
            }

            // 设置匹配情况
            dfa[LOWERCASE.toIndex(pat.charAt(j))][j] = j + 1;
            // 更新重启状态
            x = dfa[LOWERCASE.toIndex(pat.charAt(j))][x];
        }
        printDfa();
    }

    private void printDfa() {
        for (int i = 0; i < dfa.length; i++) {
            StdOut.println(Arrays.toString(dfa[i]));
        }
    }

    /**
     * Preprocesses the pattern string.
     *
     * @param pattern the pattern string
     * @param R       the alphabet size
     */
    public KMP(char[] pattern, int R) {
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        // build DFA from pattern
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            dfa[pattern[j]][j] = j + 1;      // Set match case.
            x = dfa[pattern[j]][x];        // Update restart state.
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param txt the text string
     * @return the index of the first occurrence of the pattern string
     * in the text string; N if no such match
     */
    public int search(String txt) {
        // simulate operation of DFA on text
        int m = pat.length();
        int n = txt.length();
        int i;
        int j;
        //循环,直到txt结尾或者par结尾
        for (i = 0, j = 0; i < n && j < m; i++) {
            //这里调整j的位置
            //也就是如果在txt中查到了字母i,那么结合当前j应该可以知道要回退多少可以继续
            j = dfa[LOWERCASE.toIndex(txt.charAt(i))][j];
            StdOut.println(j);
        }
        if (j == m) {
            return i - m;    // found
        }
        return n;                    // not found
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param text the text string
     * @return the index of the first occurrence of the pattern string
     * in the text string; N if no such match
     */
    public int search(char[] text) {

        // simulate operation of DFA on text
        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }
        if (j == m) return i - m;    // found
        return n;                    // not found
    }


    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
//        String pat = args[0];
//        String txt = args[1];
//        char[] pattern = pat.toCharArray();
//        char[] text = txt.toCharArray();
//
//        KMP kmp1 = new KMP(pat);
//        int offset1 = kmp1.search(txt);
//
//        KMP kmp2 = new KMP(pattern, 256);
//        int offset2 = kmp2.search(text);
//
//        // 输出对应的结果
//        StdOut.println("text:    " + txt);
//
//        StdOut.print("pattern: ");
//        for (int i = 0; i < offset1; i++)
//            StdOut.print(" ");
//        StdOut.println(pat);
//
//        StdOut.print("pattern: ");
//        for (int i = 0; i < offset2; i++)
//            StdOut.print(" ");
//        StdOut.println(pat);

        String pat = "abcababdd";
        KMP kmp = new KMP(pat);
        StdOut.print(kmp.search("abcajfjsoiijeriowjqkabcababddfdasfewrqqrfasdfdtwhyethurtuger"));
    }
}
