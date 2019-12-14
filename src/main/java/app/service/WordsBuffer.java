package app.service;

import java.util.LinkedList;

public class WordsBuffer {

    private LinkedList<String> wordsList;
    private int listCapacity;
    public volatile boolean isEmpty = true;

    public WordsBuffer(int capacity) {
        this.wordsList = new LinkedList<>();
        this.listCapacity = capacity;
    }

    public void addWord(String word) throws InterruptedException {

        synchronized (this) {

            while (wordsList.size() >= listCapacity) {
                wait();
            }
            wordsList.addLast(word);
            if (isEmpty) isEmpty = false;
            notify();
        }
    }

    public String getWord() throws InterruptedException {

        synchronized (this) {

            while (wordsList.size() == 0) {
                    wait();
            }

            String word = wordsList.poll();
            if (wordsList.size() == 0) isEmpty = true;
            notify();

            return word;
        }
    }
}
