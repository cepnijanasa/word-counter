package com.whatever;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class WordCounterTest {

    @Test
    public void countWords() {
        String input = "Don't put all your eggs in one basket";
        String[] splitInput = input.split("\\s");
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        WordContainer container = new WordContainer();
        WordCounter wordCounter = new WordCounter(inputStream, container);
        wordCounter.run();
        Map<String, Integer> resultMap = container.getWordMap();
        assertFalse(resultMap.isEmpty());
        assertTrue(resultMap.size() == splitInput.length);
        for(int i=0; i<resultMap.size(); i++) {
            assertTrue(Integer.valueOf(1).equals(resultMap.get(splitInput[i])));
        }
    }

    @Test
    public void countWordsWithDuplicatesAndSeparators() {
        String input = "Don't put, all your eggs, in one basket. Don't put";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        WordContainer container = new WordContainer();
        WordCounter wordCounter = new WordCounter(inputStream, container);
        wordCounter.run();
        Map<String, Integer> resultMap = container.getWordMap();
        assertFalse(resultMap.isEmpty());
        assertTrue(resultMap.size() == 8);

        assertTrue(Integer.valueOf(2).equals(resultMap.get("Don't")));
        assertTrue(Integer.valueOf(2).equals(resultMap.get("put")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("all")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("your")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("eggs")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("in")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("one")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("basket")));
    }
}
