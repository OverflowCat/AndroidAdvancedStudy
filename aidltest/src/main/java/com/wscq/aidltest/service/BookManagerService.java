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
 * @describe
 */
public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    /**
     * 使用这个list的原因是他可以进行自动的线程同步，类似的还有ConcurrentHashMap
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private AtomicBoolean isServiceDestoryed = new AtomicBoolean(false);

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service_onCreate()");
        //添加两本图书信息
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
//        new Thread(new ServiceWorker()).start();
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
//
//    private class ServiceWorker implements Runnable {
//        @Override
//        public void run() {
//            while (!isServiceDestoryed.get()) {
//                try {
//                    // 线程睡眠，模拟耗时操作
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int bookId = mBookList.size() + 1;
//                Book newBook = new Book(bookId, "new book#" + bookId);
//                try {
//                    // 通过AIDL发送消息到客户端
//                    onNewBookArrived(newBook);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "service_onCreate()");
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "service_onCreate()");
//            mBookList.add(book);
            onNewBookArrived(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.register(listener);
        }

        ;

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListenerList.unregister(listener);
        }

        ;
    };
}
