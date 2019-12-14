package app;

import app.service.Parser;
import app.service.WordsBuffer;
import app.service.WordsCountService;
import app.service.implementations.NewsServiceImpl;

import java.io.IOException;

public class app {

    private static final String WORD_MARKER = "";

    public static void main(String[] args) throws IOException, InterruptedException {

        // Buffer (WordsBuffer) into which 5 parallel threads send words from news
        WordsBuffer wordsBuffer = new WordsBuffer(100);

        // Service (Parser) that parses news in 5 parallel threads and sends it to Buffer (WordsBuffer)
        new Parser(new NewsServiceImpl(), wordsBuffer).start();

        // Service (WordsCountService) that read words from Buffer (WordsBuffer)
        // compare each with WORD_MARKER and return amount of repetitions
        System.out.println(new WordsCountService(wordsBuffer).count(WORD_MARKER));

    }
}


//        try(FileReader reader = new FileReader(1 + ".txt"))
//        {
//            StringBuilder result = new StringBuilder();
//            int c;
//            while((c=reader.read())!=-1){
//
//                result.append((char)c);
//            }
//            System.out.println(result.toString());
//        }
//        catch(IOException ex){
//
//            System.out.println(ex.getMessage());
//        }

//        if (newsService.createNewsInDB(newsTitle, newsPostDate, fileNameForSaveNewsText)) {
//            FileWriter writer = new FileWriter(fileNameForSaveNewsText, false);
//            writer.write(clearNewsText.toString());
//            writer.flush();
//            writer.close();
//        }



