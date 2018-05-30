package com.wscq.java.algorithm.util;

import java.io.File;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/4/28
 * @describe
 */
public class FileUtil {
    public static File getFile(String fileName) {
        String path = getFilePath(fileName);
        return new File(path);
    }

    public static String getFilePath(String fileName) {
        return "java/src/main/java/file/" + fileName;
    }
}
