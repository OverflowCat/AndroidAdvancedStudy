package com.wscq.java.algorithm.strings.stringsort;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/4
 * @describe
 */
public class Alphabet {
    /**
     * 二进制
     */
    public static final Alphabet BINARY = new Alphabet("01");
    /**
     * 八进制
     */
    public static final Alphabet OCTAL = new Alphabet("01234567");
    /**
     * 十进制
     */
    public static final Alphabet DECIMAL = new Alphabet("0123456789");
    /**
     * 十六进制
     */
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");
    /**
     * DNA碱基对
     */
    public static final Alphabet DNA = new Alphabet("ACGT");
    /**
     * 字母表
     */
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    /**
     * 大写字母表
     */
    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    /**
     * 蛋白质
     */
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
    /**
     * base64字母表
     */
    public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    /**
     * ASCII码
     */
    public static final Alphabet ASCII = new Alphabet(128);
    /**
     * EXTENDED_ASCII码
     */
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
    /**
     * UNICODE16码
     */
    public static final Alphabet UNICODE16 = new Alphabet(65536);
    //字母数组
    private char[] alphabet;
    //编号
    private int[] inverse;
    //字母表中的字符数量
    private final int R;

    /**
     * 根据传入的字符,创建一张新的字母表
     *
     * @param alpha
     */
    public Alphabet(String alpha) {
        boolean[] unicode = new boolean['\uffff'];

        //传入的字符串验证,如果字符串重复,抛异常
        int c;
        for (c = 0; c < alpha.length(); ++c) {
            char Cc = alpha.charAt(c);
            //不能存在一样的,否则抛异常
            if (unicode[Cc]) {
                throw new IllegalArgumentException("Illegal alphabet: repeated character = '" + Cc + "'");
            }

            unicode[Cc] = true;
        }

        //赋初值
        this.alphabet = alpha.toCharArray();
        this.R = alpha.length();
        //一个较长的int数组
        this.inverse = new int['\uffff'];

        //所有值都置为-1
        for (c = 0; c < this.inverse.length; ++c) {
            this.inverse[c] = -1;
        }
        //每个字母位对应的下标,会依次往后变大
        for (c = 0; c < this.R; this.inverse[this.alphabet[c]] = c++) {
            ;
        }
    }

    private Alphabet(int radix) {
        this.R = radix;
        this.alphabet = new char[this.R];
        this.inverse = new int[this.R];

        int i;
        for (i = 0; i < this.R; ++i) {
            this.alphabet[i] = (char) i;
        }

        for (i = 0; i < this.R; this.inverse[i] = i++) {
            ;
        }
    }

    public Alphabet() {
        this(256);
    }

    /**
     * c是否在字母表中
     *
     * @param c 要判断的字母
     * @return true = 在字母表中,false = 不在字母表中
     */
    public boolean contains(char c) {
        return this.inverse[c] != -1;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int R() {
        return this.R;
    }

    public int radix() {
        return this.R;
    }

    /**
     * 表示一个索引所需要的位数
     *
     * @return 索引所需要的位数
     */
    public int lgR() {
        int lgR = 0;

        for (int t = this.R - 1; t >= 1; t /= 2) {
            ++lgR;
        }

        return lgR;
    }

    /**
     * 将s转换为R进制的整数
     *
     * @param s 要转化的字母s
     * @return R进制的整数
     */
    public int toIndex(char c) {
        if (c < this.inverse.length && this.inverse[c] != -1) {
            return this.inverse[c];
        } else {
            throw new IllegalArgumentException("Character " + c + " not in alphabet");
        }
    }

    public int[] toIndices(String s) {
        char[] source = s.toCharArray();
        int[] target = new int[s.length()];

        for (int i = 0; i < source.length; ++i) {
            target[i] = this.toIndex(source[i]);
        }

        return target;
    }

    /**
     * 将R进制的整数转化为基于该字母表的字符串
     *
     * @param index 要转化的整数
     * @return 返回的字符串
     */
    public char toChar(int index) {
        if (index >= 0 && index < this.R) {
            return this.alphabet[index];
        } else {
            throw new IllegalArgumentException("index must be between 0 and " + this.R + ": " + index);
        }
    }

    public String toChars(int[] indices) {
        StringBuilder s = new StringBuilder(indices.length);

        for (int i = 0; i < indices.length; ++i) {
            s.append(this.toChar(indices[i]));
        }

        return s.toString();
    }

    public static void main(String[] args) {
        int[] encoded1 = BASE64.toIndices("NowIsTheTimeForAllGoodMen");
        String decoded1 = BASE64.toChars(encoded1);
        StdOut.println(decoded1);

        int[] encoded2 = DNA.toIndices("AACGAACGGTTTACCCCG");
        String decoded2 = DNA.toChars(encoded2);
        StdOut.println(decoded2);

        int[] encoded3 = DECIMAL.toIndices("01234567890123456789");
        String decoded3 = DECIMAL.toChars(encoded3);
        StdOut.println(decoded3);
    }
}
