package com.wscq.yong.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/16
 * @describe
 */
public class LogUtil {
    private static boolean isDebug = true;

    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        //TODO 代码不要删，这段是为了解决打印过长显示不全用的
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.w(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d("testTaskType-" + tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }
}
