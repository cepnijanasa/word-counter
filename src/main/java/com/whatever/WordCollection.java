package com.whatever;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordCollection {

    private SortedMap<String, Integer> wordMap = Collections.synchronizedSortedMap(
            new TreeMap<String, Integer>());

    public synchronized void put(String word) {
        if (wordMap.containsKey(word)) {
            Integer count = wordMap.get(word);
            wordMap.put(word, ++count); // replace existing value
        } else {
            wordMap.put(word, 1);
        }
    }

    Map<String, Integer> getWords() {
        return Collections.unmodifiableMap(wordMap);
    }

    public Map<String, Integer> getWordsAG() {
        return Collections.unmodifiableMap(wordMap.headMap("h"));
    }

    public Map<String, Integer> getWordsHN() {
        return Collections.unmodifiableMap(wordMap.subMap("h", "o"));
    }

    public Map<String, Integer> getWordsOU() {
        return Collections.unmodifiableMap(wordMap.subMap("o", "v"));
    }

    public Map<String, Integer> getWordsVZ() {
        return Collections.unmodifiableMap(wordMap.tailMap("v"));
    }
}
