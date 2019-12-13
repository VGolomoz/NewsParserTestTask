package app.service;

import java.util.LinkedList;

public class WordsBuffer {

    private LinkedList<String> wordsList;
    private int listCapacity;

    public WordsBuffer(int capacity) {
        this.wordsList = new LinkedList<>();
        this.listCapacity = capacity;
    }

    public void addWord(String word) throws InterruptedException {

        synchronized (this) {

            while (wordsList.size() >= listCapacity) {
                wait();
            }

            System.out.println("Produced add word: " + word);
            wordsList.addLast(word);
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
            notify();

            return word;
        }
    }
}
