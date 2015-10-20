package com.whatever;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordCounterTest {

    @Test
    public void countWordsWithDuplicatesAndSeparators() throws IOException {
        String input = "Don't put, all your eggs, in one basket. DON't put";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(inputStream);

        WordCollection container = new WordCollection();
        WordCounter wordCounter = new WordCounter(file, container);
        wordCounter.run();
        Map<String, Integer> resultMap = container.getWords();
        assertFalse(resultMap.isEmpty());
        assertTrue(resultMap.size() == 8);

        assertTrue(Integer.valueOf(2).equals(resultMap.get("don't")));
        assertTrue(Integer.valueOf(2).equals(resultMap.get("put")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("all")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("your")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("eggs")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("in")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("one")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("basket")));
    }

    @Test
    public void countWordsWithNumbers() throws IOException {
        String input = "Don't put 13 eggs in 1 basket. DON't put 1000eur into_ £549";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(inputStream);

        WordCollection container = new WordCollection();
        WordCounter wordCounter = new WordCounter(file, container);
        wordCounter.run();
        Map<String, Integer> resultMap = container.getWords();
        assertFalse(resultMap.isEmpty());
        assertTrue(resultMap.size() == 7);
        assertTrue(Integer.valueOf(2).equals(resultMap.get("don't")));
        assertTrue(Integer.valueOf(2).equals(resultMap.get("put")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("eggs")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("in")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("basket")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("eur")));
        assertTrue(Integer.valueOf(1).equals(resultMap.get("into")));
    }
}
