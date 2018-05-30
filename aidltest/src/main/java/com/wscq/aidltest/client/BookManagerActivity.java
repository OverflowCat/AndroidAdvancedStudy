package com.wscq.aidltest.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wscq.aidltest.R;
import com.wscq.aidltest.aidl.Book;
import com.wscq.aidltest.aidl.IBookManager;
import com.wscq.aidltest.aidl.IOnNewBookArrivedListener;
import com.wscq.aidltest.service.BookManagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/25
 * @describe 客户端的类, 用来获取服务端的的书名等
 */
public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = "BookManagerActivity";
    public static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private TextView bookListContent;
    private EditText inputText;
    private Button addButton;

    private IBookManager mRemoteBookManager;
    private List<Book> latestList = new ArrayList<>();

    private ServiceConnection mConnection = new ServiceConnection() {
        // 服务连接后
        @Override
        /**
         * 服务连接以后执行此方法
         */
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 通过aidl中的方法，把Binder转化为AIDL本身
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            Log.i(TAG, "binder died");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "receive new book:" + msg.obj);
                    try {
                        updateContent(mRemoteBookManager.getBookList());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setListener();
        //通过intent来和服务端实现绑定
        bindService();

    }

    private void findViewById() {
        bookListContent = (TextView) findViewById(R.id.bookList);
        inputText = (EditText) findViewById(R.id.inputText);
        addButton = (Button) findViewById(R.id.addBtn);
    }

    private void setListener() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = inputText.getText().toString();
                if (TextUtils.isEmpty(bookName)) {
                    Toast.makeText(BookManagerActivity.this, "书名不能为空", Toast.LENGTH_SHORT);
                } else {
                    Random random = new Random(10000);
                    int bookId = random.nextInt();
                    try {
                        mRemoteBookManager.addBook(new Book(bookId, bookName));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务端
        unbindService(mConnection);
    }

    private void updateContent(List<Book> list) {
        if (list.size() == latestList.size()) {
            return;
        } else {
            latestList = list;
        }
        String content = "";
        for (Book book : list) {
            content = content + book.toString() + "\n";
        }
        bookListContent.setText(content);
    }
}
