package com.whatever;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class WordCollectionTest {

    WordCollection collection;

    @Before
    public void init() {
        collection = new WordCollection();
        collection.put("abstract");
        collection.put("graceful");
        collection.put("hedgehog");
        collection.put("monster");
        collection.put("unique");
        collection.put("zebra");
    }

    @Test
    public void getWordsAGTest() {
        Map<String, Integer> wordsAG = collection.getWordsAG();
        assertTrue(wordsAG.size() == 2);
        assertTrue(wordsAG.containsKey("abstract"));
        assertTrue(wordsAG.containsKey("graceful"));
    }

    @Test
    public void getWordsHNTest() {
        Map<String, Integer> wordsHN = collection.getWordsHN();
        assertTrue(wordsHN.size() == 2);
        assertTrue(wordsHN.containsKey("hedgehog"));
        assertTrue(wordsHN.containsKey("monster"));
    }

    @Test
    public void getWordsOUTest() {
        Map<String, Integer> wordsOU = collection.getWordsOU();
        assertTrue(wordsOU.size() == 1);
        assertTrue(wordsOU.containsKey("unique"));
    }

    @Test
    public void getWordsVZTest() {
        Map<String, Integer> wordsVZ = collection.getWordsVZ();
        assertTrue(wordsVZ.size() == 1);
        assertTrue(wordsVZ.containsKey("zebra"));
    }
}
