package com.whatever;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WordCollector {

    private List<InputStream> isList = new ArrayList<>();

    public WordCollection collectWords() throws InterruptedException {
        Thread thread;
        List<Thread> threadList = new ArrayList<>();
        WordCollection collection = new WordCollection();
        for (InputStream is : isList) {
            thread = new Thread(new WordCounter(is, collection));
            threadList.add(thread);
        }
        for (Thread t : threadList) {
            t.start();
            t.join();
        }

        return collection;
    }

    public void addSource(InputStream is) {
        isList.add(is);
    }
}
