package com.whatever;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class WordContainerTest {

    WordContainer container;

    @Before
    public void init() {
        container = new WordContainer();
        container.put("abstract");
        container.put("graceful");
        container.put("hedgehog");
        container.put("monster");
        container.put("unique");
        container.put("zebra");
    }

    @Test
    public void getWordsAGTest() {
        Map<String, Integer> wordsAG = container.getWordsAG();
        assertTrue(wordsAG.size() == 2);
        assertTrue(wordsAG.containsKey("abstract"));
        assertTrue(wordsAG.containsKey("graceful"));
    }

    @Test
    public void getWordsHNTest() {
        Map<String, Integer> wordsHN = container.getWordsHN();
        assertTrue(wordsHN.size() == 2);
        assertTrue(wordsHN.containsKey("hedgehog"));
        assertTrue(wordsHN.containsKey("monster"));
    }

    @Test
    public void getWordsOUTest() {
        Map<String, Integer> wordsOU = container.getWordsOU();
        assertTrue(wordsOU.size() == 1);
        assertTrue(wordsOU.containsKey("unique"));
    }

    @Test
    public void getWordsVZTest() {
        Map<String, Integer> wordsVZ = container.getWordsVZ();
        assertTrue(wordsVZ.size() == 1);
        assertTrue(wordsVZ.containsKey("zebra"));
    }
}
