package com.wscq.messengertest.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wscq.messengertest.R;
import com.wscq.messengertest.service.MessengerService;
import com.wscq.messengertest.util.MyContaints;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/25
 * @describe
 */
public class MessengerActivity extends Activity {
    private final static String TAG = "messenger_activity";

    //给服务端发消息使用
    private Messenger messenger;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //初始化Messenger对象
            messenger = new Messenger(service);
            //构造Message并给必要的参数赋值
            Message message = getMessage();
            try {
                //发送消息
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @NonNull
        //构造要发送的消息,服务端会对这条消息进行处理
        private Message getMessage() {
            Message message = Message.obtain();
            message.what = MyContaints.MSG_FORM_CLIENT;
            Bundle bundle = new Bundle();
            bundle.putString(MyContaints.CLIENT_MESSAGE_KEY, "这里来自客户端的一条消息");
            message.setData(bundle);
            //设置接收返回的Messenger
            message.replyTo = getReplyMessenger;
            return message;
        }

        // 当里链接断开时候回回调这个方法
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //接受服务端消息使用
    private Messenger getReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyContaints.MSG_FORM_SERVICE:
                    Log.d(TAG, "客户端收到服务端消息:" + msg.getData().getString(MyContaints.SERVICE_MESSAGE_KEY));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
