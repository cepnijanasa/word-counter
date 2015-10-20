package com.whatever;

import com.google.common.base.Splitter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WordCounter implements Runnable {

    private MultipartFile file;
    private WordCollection wordCollection;

    public WordCounter(MultipartFile file, WordCollection wordCollection) {
        this.file = file;
        this.wordCollection = wordCollection;
    }

    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> wordList = Splitter.onPattern("([^a-zA-Z']|\\s)")
                        .omitEmptyStrings()
                        .splitToList(line);
                for (String word : wordList) {
                    wordCollection.put(word.toLowerCase());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from file '" + file.getOriginalFilename() + "'", e);
        }

    }
}
