package app.service;

public class WordsCountService {

    private Long counter = 0L;
    private boolean countIsActive;
    private WordsBuffer wordsBuffer;

    public WordsCountService(WordsBuffer wordsBuffer) {
        this.wordsBuffer = wordsBuffer;
    }

    private void readAndCompare(String wordMarker) {

        new Thread(() -> {

            int amountOfWritingThreads = 5;

            while (Parser.writeThreadsFinished.intValue() < amountOfWritingThreads || !wordsBuffer.isEmpty) {

                String word = null;

                try {
                    word = wordsBuffer.getWord();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (word.equalsIgnoreCase(wordMarker)) {
                    counter++;
                }
            }
            countIsActive = false;
        }).start();
    }

    public Long count(String wordMarker) throws InterruptedException {
        countIsActive = true;
        readAndCompare(wordMarker);
        while (countIsActive) {
            Thread.sleep(1000);
        }

        return counter;
    }
}

