package com.wscq.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.wscq.aidltest.aidl.Book;
import com.wscq.aidltest.aidl.IBookManager;
import com.wscq.aidltest.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2018/5/25
 * @describe 服务端代码
 */
public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    // 使用这个list的原因是他可以进行自动的线程同步，类似的还有ConcurrentHashMap
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    //系统专门提供的，用于删除跨进程listener的接口。支持任意的AIDL接口。
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean isServiceDestoryed = new AtomicBoolean(false);

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service_onCreate()");
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "service_onCreate()");
        return mBinder;
    }

    /**
     * 当有新书添加的时候，服务端通过aidl调用客户端，然后显示添加新书成功，如果此处是耗时操作，需要通过handler来执行
     */
    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = mListenerList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        // 这个要和beginBroadcast配对使用，就算仅仅是获取个数
        mListenerList.finishBroadcast();
    }

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "service_onCreate()");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "service_onCreate()");
            onNewBookArrived(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }
    };
}
