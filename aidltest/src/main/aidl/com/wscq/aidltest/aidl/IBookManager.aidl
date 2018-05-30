package com.wscq.aidltest.aidl;
import com.wscq.aidltest.aidl.Book;
import com.wscq.aidltest.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
