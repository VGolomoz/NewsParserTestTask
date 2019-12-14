package app.service;

public class WordsCountService {

    private Long counter = 0L;
    private WordsBuffer wordsBuffer;

    public WordsCountService(WordsBuffer wordsBuffer) {
        this.wordsBuffer = wordsBuffer;
    }

    public Long count(String wordMarker) {

        int amountOfWritingThreads = 5;

        while (Parser.writeThreadsFinished.intValue() < amountOfWritingThreads || !wordsBuffer.isEmpty) {

            String word = wordsBuffer.getWord();
            if (word.equalsIgnoreCase(wordMarker)) {
                counter++;
            }
        }

        for (String s: wordsBuffer.wordsList) {
            System.out.println(s);
        }
        System.out.println("Add words: " + wordsBuffer.addCount);
        System.out.println("Get words: " + wordsBuffer.getCount);
        return counter;
    }
}

