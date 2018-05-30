package com.wscq.aidltest.aidl;
import com.wscq.aidltest.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
