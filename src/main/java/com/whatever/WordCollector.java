package com.whatever;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordCollector {

    public static WordCollection collectWords(List<MultipartFile> fileList)
            throws InterruptedException, ExecutionException, IOException {

        WordCollection collection = new WordCollection();
        if (fileList.isEmpty()) {
            return collection;
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future> futureList = new ArrayList<>(fileList.size());
        for (MultipartFile file : fileList) {
            futureList.add(
                    executor.submit(
                            new WordCounter(file, collection))
            );
        }
        for (Future future : futureList) {
            future.get(); // returns null as soon thread finishes its work.
        }
        executor.shutdown();

        return collection;
    }
}
