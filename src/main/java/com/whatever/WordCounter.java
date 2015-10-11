package com.whatever;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WordCounter implements Runnable {

    Logger log = LoggerFactory.getLogger(WordCounter.class);

    private InputStream inputStream;
    private WordContainer wordContainer;

    public WordCounter(InputStream inputStream, WordContainer wordContainer) {
        this.inputStream = inputStream;
        this.wordContainer = wordContainer;
    }

    public void run() {
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            while ((line = bufferedReader.readLine()) != null){
                List<String> wordList = Splitter.onPattern("([^\\w']|\\s)")
                        .omitEmptyStrings()
                        .splitToList(line);
                for(String word : wordList) {
                    wordContainer.put(word);
                }
            }
        } catch (IOException e) {
            log.error("", e);
            // TODO
        }

    }
}
