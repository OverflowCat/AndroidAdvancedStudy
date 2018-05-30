package com.wscq.messengertest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wscq.messengertest.util.MyContaints;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/25
 * @describe
 */
public class MessengerService extends Service {
    private final static String TAG = "messenger_service";
    private final Messenger messenger = new Messenger(new MessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private static class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyContaints.MSG_FORM_CLIENT:
                    //收到消息后打印出这个消息
                    Log.d(TAG, "收到来自客户端的消息:" + msg.getData().getString(MyContaints.CLIENT_MESSAGE_KEY));
                    replyClient(msg);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }

        private void replyClient(Message msg) {
            //获取客户端的Messenger
            Messenger messenger = msg.replyTo;
            //给要传递的信息赋值
            Message message = getMessage();
            try {
                //发送消息到客户端
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @NonNull
        private Message getMessage() {
            Message message = Message.obtain(null, MyContaints.MSG_FORM_SERVICE);
            Bundle bundle = new Bundle();
            bundle.putString(MyContaints.SERVICE_MESSAGE_KEY, "这里是服务端的回复");
            message.setData(bundle);
            return message;
        }
    }
}
