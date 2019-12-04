package com.wscq.toastmanager;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019-10-10
 * @describe
 */
public class ToastUtils {
    private static Object iNotificationManagerObj;
    private static ToastUtils toastUtils = null;

    /**
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        if (toastUtils == null) {
            toastUtils = new ToastUtils();
        }
        toastUtils.show(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param message
     */
    public void show(Context context, String message, int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        //        后setText 兼容小米默认会显示app名称的问题
        //        toast = Toast.makeText(context, null, duration);
        //        toast.setText(message);
        //        if (isNotificationEnabled(context)) {
        //            toast.show();
        //        } else {
        //            showSystemToast(toast);
        //        }
        showToast(context, message, Gravity.BOTTOM);
    }

    Toast toast;

    public void showToast(Context context, String text, int gravity) {
        if (context == null) {
            return;
        }
        toast = null;
        if (toast == null) {
            toast = new Toast(context);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflater.inflate(R.layout.view_shared_toast_tipview, null);
        ((TextView) toastView.findViewById(R.id.toast_tip_text)).setText(text);
        if (gravity == Gravity.CENTER) {
            // (toastView.findViewById(R.id.toast_tip_image)).setVisibility(View.VISIBLE);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, 140);
        }

        toast.setView(toastView);
        if (isNotificationEnabled(context)) {
            toast.show();
        } else {
            showSystemToast(toast);
        }
    }

    /**
     * 显示系统Toast
     */
    private static void showSystemToast(Toast toast) {
        try {
            Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
            getServiceMethod.setAccessible(true);
            if (iNotificationManagerObj == null) {
                iNotificationManagerObj = getServiceMethod.invoke(null);

                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //强制使用系统Toast
                        if ("enqueueToast".equals(method.getName())
                                || "enqueueToastEx".equals(method.getName())) {  //华为p20 pro上为enqueueToastEx
                            args[0] = "android";
                        }
                        return method.invoke(iNotificationManagerObj, args);
                    }
                });
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(null, iNotificationManagerProxy);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息通知是否开启
     *
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        return areNotificationsEnabled;
    }
}
