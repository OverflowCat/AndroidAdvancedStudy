package com.wscq.sockettest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wscq.sockettest.util.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/6/4
 * @describe 在服务端创建了一个Socket服务, 客户单连接后, 会和客户端发生交互.
 * 每收到一次客户端的消息,就随机回复一句话到客户端.
 */
public class TCPServerService extends Service {
    private static final String TAG = "service_log";
    private boolean isServiceDestoryed = false;
    private String[] definedMessages = new String[]{
            "你好啊",
            "你叫啥",
            "你愁啥",
            "抽你呀",
            "你弄啥嘞",
            "好像是个智障"
    };

    @Override
    public void onCreate() {
        new Thread(new TCPServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isServiceDestoryed = true;
        super.onDestroy();
    }

    public class TCPServer implements Runnable {

        @Override
        public void run() {
ServerSocket serverSocket = null;
try {
    //建立一个端口为8688的服务
    serverSocket = new ServerSocket(8688);
} catch (IOException e) {
    Log.e(TAG, "establish tcp server failed,port:8688");
    e.printStackTrace();
    return;
}

while (!isServiceDestoryed) {
    try {
        //连接客户端Socket
        final Socket client = serverSocket.accept();
        Log.d(TAG, "accept");
        new Thread() {
            public void run() {
                try {
                    //回复客户端
                    responseClient(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
    }

    private void responseClient(Socket client) throws IOException {
//获取客户端输入流
BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//连接客户端输出流
PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
out.print("欢迎来聊天室");
//如果没有断开连接
while (!isServiceDestoryed) {
    //读取下一行
    String str = in.readLine();
    Log.d(TAG, "msg from client:" + str);
    if (str == null) {
        break;
    }
    //随机获取一个回复的短语
    int i = new Random().nextInt(definedMessages.length);
    String msg = definedMessages[i];
    //在客户端输出
    out.println(msg);
    Log.d(TAG, "send:" + msg);
}
Log.d(TAG, "client quit");
MyUtils.close(out);
MyUtils.close(in);
client.close();
    }
}
