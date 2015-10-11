package com.whatever;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WordContainer {

    private Map<String, Integer> wordMap = Collections.synchronizedMap(new HashMap<String, Integer>());

    public synchronized void put(String word) {
        if (wordMap.containsKey(word)) {
            Integer count = wordMap.get(word);
            count++;
            wordMap.put(word, count); // replace existing value
        } else {
            wordMap.put(word, 1);
        }
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }
}
