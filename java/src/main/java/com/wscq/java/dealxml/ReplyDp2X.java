package com.wscq.java.dealxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/3/5
 * @describe 把layout中的dp转换为X开头
 */
public class ReplyDp2X {
    public static void main(String[] args) {
        File f = new File("java/src/main/java/com/wscq/java/dealxml/layout");
        print(f, 0);
    }

    /**
     * 遍历文件夹
     *
     * @param f
     * @param len
     */
    public static void print(File f, int len) {
        File[] file = f.listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()) { //判断是否文件夹
                print(file[i], len + 1);
            }

            // 为防止输出文件覆盖源文件，所以更改输出盘路径 也可自行设置其他路径
            File outPath = new File(file[i].getParent().replace("layout", "layout_copy"));
            File readfile = new File(file[i].getAbsolutePath());

            if (!readfile.isDirectory()) {
                String filename = readfile.getName(); // 读到的文件名
                String absolutepath = readfile.getAbsolutePath(); // 文件的绝对路径
                readFile(absolutepath, filename, i, outPath); // 调用 readFile
            }
        }
        outputFile();
    }

    /**
     * 读取文件夹下的文件
     *
     * @return
     */
    public static void readFile(String absolutepath, String filename,
                                int index, File outPath) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(absolutepath), "UTF-8")); // 数据流读取文件

            StringBuffer strBuffer = new StringBuffer();

            for (String temp = null; (temp = bufReader.readLine()) != null;
                 temp = null) {
                if (!temp.contains("normal")) {
                    Pattern pattern = Pattern.compile("\"([-0-9\\.]*)dp\""); //去掉空格符合换行符
                    Matcher matcher = pattern.matcher(temp);
                    if (matcher.find()) {
                        String xmlValue = matcher.group();
                        outputSpecialTemp(temp, xmlValue);
                        temp = replyTemp(temp, matcher);
//                    temp = replyNormal(temp);
                    }
                }
                strBuffer.append(temp);
                strBuffer.append(System.getProperty("line.separator")); // 换行符
            }

            bufReader.close();

            if (outPath.exists() == false) { // 检查输出文件夹是否存在，若不存在先创建
                outPath.mkdirs();
                System.out.println("已成功创建输出文件夹：" + outPath);
            }

            PrintWriter printWriter = new PrintWriter(outPath + "/" +
                    filename, "UTF-8"); // 替换后输出文件路径
            printWriter.write(strBuffer.toString().toCharArray()); //重新写入
            printWriter.flush();
            printWriter.close();
//            System.out.println("第 " + (index + 1) + " 个文件   " +
//                    "  已成功输出到    " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void outputSpecialTemp(String temp, String xmlValue) {
        String realNum = xmlValue.replaceAll("\"", "");
        String dimenName = getDimenName(temp, xmlValue);
        outDimen("<dimen name=\"" + dimenName + "\">" + realNum + "</dimen>");
    }

    /**
     * 把dp替换为对应的值
     */
    private static String replyTemp(String temp, Matcher matcher) {
        String xmlValue = matcher.group();
        //去掉引号
        xmlValue = getDimenName(temp, xmlValue);
        xmlValue = "\"" + "@dimen/" + xmlValue + "\"";
        temp = temp.substring(0, matcher.start()) + xmlValue + temp.substring(matcher.end());
        return temp;
    }

    private static String replyNormal(String temp) {
        if (temp.contains("normal_") && !temp.contains("sp")) {
            System.out.println("old --> " + temp);
            if (isHeight(temp)) {
                temp = temp.replaceAll("normal_", "y").replaceAll("dp", "");
            } else {
                temp = temp.replaceAll("normal_", "x").replaceAll("dp", "");
            }
            System.out.println("new --> " + temp);
        }
        return temp;
    }

    /**
     * 获取对应的值的名称
     */
    private static String getDimenName(String temp, String xmlValue) {
        String dimenName = xmlValue.replaceAll("\"", "")
                .replaceAll("-", "_sub_")
                .replaceAll("\\.", "_")
                .replaceAll("dp", "");
        if (isHeight(temp)) {
            dimenName = "y" + dimenName;
        } else {
            dimenName = "x" + dimenName;
        }
        return dimenName;
    }

    public static boolean isHeight(String temp) {
        return temp.contains("height") || temp.contains("top") || temp.contains("bottom")
                || temp.contains("Top") || temp.contains("Bottom") || temp.contains("Height");
    }


    //文件输出相关

    static File file;
    static StringBuffer sb;

    static void outputFile() {
        try {
            if (file.exists()) {
                file.delete();
            }
            /* 写入Txt文件 */
            file.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            System.out.println(sb.toString());
            out.write(sb.toString()); // \r\n即为换行
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
            sb = null;
            file = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void outDimen(String name) {
        if (sb == null) {
            file = new File("java/src/main/java/com/wscq/java/dealxml/layout/aaa.xml");
            sb = new StringBuffer();
        }
        if (!sb.toString().contains(name)) {
            sb.append(name + "\r\n");// 在已有的基础上添加字符串
        }
    }
}