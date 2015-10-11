package com.whatever;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

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
}
