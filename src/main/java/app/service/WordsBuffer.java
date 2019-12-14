package app.service;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class WordsBuffer {

    public LinkedList<String> wordsList;
    private int listCapacity;
    public volatile boolean isEmpty = true;
    public static volatile AtomicInteger addCount = new AtomicInteger(0);
    public static volatile AtomicInteger getCount = new AtomicInteger(0);

    public WordsBuffer(int capacity) {
        this.wordsList = new LinkedList<>();
        this.listCapacity = capacity;
    }

    public void addWord(String word) throws InterruptedException {

        synchronized (this) {

            while (wordsList.size() >= listCapacity) {
                wait();
            }
            addCount.incrementAndGet();
            wordsList.addLast(word);
            if (isEmpty) isEmpty = false;
            notify();
        }
    }

    public String getWord() {

        synchronized (this) {

            while (wordsList.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String word = wordsList.poll();
            if (wordsList.size() == 0) isEmpty = true;
            getCount.incrementAndGet();
            notify();

            return word;
        }
    }
}
