package com.wscq.java.algorithm.strings.stringsort;

import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/4
 * @describe 键索引计数法
 */
public class KeySort {
    private Student[] a;
    private int R;

    public KeySort(Student[] a) {
        this.a = a;
        this.R = a.length;
    }

    public void sort() {
        int N = a.length;
        Student[] aux = new Student[N];
        int[] count = new int[R + 1];

        //计算出现频率
        for (int i = 0; i < N; i++) {
            count[a[i].key() + 1]++;
        }
        StdOut.println(Arrays.toString(count));

        //将频率转化为索引,count[i]为组号小于i的数量
        for (int r = 0; r < R; r++) {
            count[r + 1] = count[r] + count[r + 1];
        }
        StdOut.println(Arrays.toString(count));

        //将元素分类,此处的aux已经是排序好的了
        //知道了a[i].key()对应的前面有几个元素,
        // 所以count[a[i].key()]]就是当前元素要放到de位置
        //因为已经放了当前元素,所以,下标加一,然后当下一个key相同的元素时,
        //会放到后一位.直到所有都放完,此时count已经无用
        //所以不用关心count的排列顺序了
        for (int i = 0; i < N; i++) {
//            int index = count[a[i].key()];
//            StdOut.print(index + ",");
            aux[count[a[i].key()]++] = a[i];
        }
        StdOut.println();
        StdOut.println(Arrays.toString(count));

        //回写
        for (int i = 0; i < N; i++) {
            a[i] = aux[i];
        }
        StdOut.println(Arrays.toString(count));
    }

    static class Student {
        int key;
        String name;

        public Student(String name, int key) {
            this.key = key;
            this.name = name;
        }

        public int key() {
            return key;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "key=" + key +
                    ", name='" + name + '\'' +
                    '}' + "\n";
        }
    }

    public static void main(String[] args) {
        Student[] s = new Student[]{
                new Student("anderson", 2),
                new Student("brown", 3),
                new Student("davis", 3),
                new Student("garcia", 4),
                new Student("harrias", 1),
                new Student("jackson", 3),
                new Student("johnson", 4),
                new Student("jones", 3),
                new Student("martin", 1),
                new Student("martinez", 2),
                new Student("miller", 2),
                new Student("moore", 1),
                new Student("robinson", 2),
                new Student("smith", 4),
                new Student("taylor", 3),
                new Student("thomas", 4),
                new Student("thompson", 4),
                new Student("white", 2),
                new Student("williams", 3),
                new Student("wilson", 4),
        };
        KeySort keySort = new KeySort(s);
        keySort.sort();
        StdOut.print(Arrays.toString(keySort.a));
    }
}
