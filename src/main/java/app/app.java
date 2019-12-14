package app;

import app.service.Parser;
import app.service.WordsBuffer;
import app.service.WordsCountService;
import app.service.implementations.NewsServiceImpl;

import java.io.IOException;

public class app {

    public static void main(String[] args) throws IOException, InterruptedException {

        WordsBuffer wordsBuffer = new WordsBuffer(100);

        new Parser(new NewsServiceImpl(), wordsBuffer).start();

        new Thread(() -> System.out.println(new WordsCountService(wordsBuffer).count("церкви"))).start();

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



