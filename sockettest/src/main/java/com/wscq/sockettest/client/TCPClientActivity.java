package com.wscq.sockettest.client;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wscq.sockettest.R;
import com.wscq.sockettest.service.TCPServerService;
import com.wscq.sockettest.util.MyUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/6/4
 * @describe 客户端启动一个Socket, 通过端口号连接服务端Socket服务.这里采用TCP协议进行连接.
 */
public class TCPClientActivity extends Activity implements View.OnClickListener {
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    private static final String TAG = "client_log";

    private Button sendButton;
    private TextView messageTextView;
    private EditText messageEditText;

    private PrintWriter printWriter;
    private Socket clientSocket;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    messageTextView.setText(messageTextView.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    sendButton.setEnabled(true);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);
        findViewAndSetListener();
        startService();
        new Thread() {
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    /**
     * 启动服务端
     */
    private void startService() {
        Intent service = new Intent(this, TCPServerService.class);
        startService(service);
    }

    /**
     * 连接服务端
     * 此处是网络相关服务,所以需要在子线程中进行
     * 通过Handler更新主线程的UI
     */
    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                //连接Socket
                String host = "localhost";
                int port = 8688;
                socket = new Socket(host, port);
                //从Socket中获取输出流
                printWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                //发送连接成功消息,使button可点击
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.e(TAG, "connect tcp server failed,retry ... ");

            }
        }
        getReceiveMsg(socket);
    }

    private void getReceiveMsg(Socket socket) {
        try {
            //获取服务端的回复数据流
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()) {
                //读取回复的消息
                String msg = br.readLine();
                Log.d(TAG, "receive:" + msg);
                //回复的消息拼接格式后,输出到客户端
                if (msg != null) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg = "server " + time + ":" + msg + "\n";
                    handler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                }
            }
            Log.d("TAG", "quit...");
            MyUtils.close(printWriter);
            MyUtils.close(br);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findViewAndSetListener() {
        sendButton = findViewById(R.id.sendButton);
        messageTextView = findViewById(R.id.messageTextView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == sendButton) {
            final String msg = messageEditText.getText().toString();
            //发送消息时候,拼接发送的消息到窗口
            if (!TextUtils.isEmpty(msg) && printWriter != null) {
                printWriter.println(msg);
                messageEditText.setText("");
                String time = formatDateTime(System.currentTimeMillis());
                final String showedMsg = "self " + time + ":" + msg + "" + "\n";
                messageTextView.setText(messageTextView.getText() + showedMsg);
            }
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    protected void onDestroy() {
        //退出时候销毁对应流
        if (clientSocket != null) {
            try {
                clientSocket.shutdownInput();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
