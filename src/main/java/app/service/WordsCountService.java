package app.service;

public class WordsCountService {

    private Long counter = 0L;
    private WordsBuffer wordsBuffer;

    public WordsCountService(WordsBuffer wordsBuffer) {
        this.wordsBuffer = wordsBuffer;
    }

    public Long count(String wordMarker) {

        while (Parser.isActive) {

            String word = wordsBuffer.getWord();
            System.out.println("Consumer get word: " + word);
            if (word.equalsIgnoreCase(wordMarker)) {
                counter++;
            }
        }
        return counter;
    }
}

