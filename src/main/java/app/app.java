package app;

import app.service.Parser;
import app.service.WordsBuffer;
import app.service.WordsCountService;
import app.service.implementations.NewsServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static app.utils.MyConstants.*;

public class app {



    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> categories = new ArrayList<>();
        categories.add(POLITICS_CATEGORY);
        categories.add(WAR_CATEGORY);
        categories.add(SOCIETY_CATEGORY);
        categories.add(WORLD_CATEGORY);
        categories.add(HEALTH_CATEGORY);

        WordsBuffer wordsBuffer = new WordsBuffer(5);

        Thread producer = new Thread(() -> {
            new Parser(new NewsServiceImpl(), wordsBuffer).parseAndSave(categories.get(0));
        });

        Thread consumer = new Thread(() -> {
            System.out.println(new WordsCountService(wordsBuffer).count("Флоран"));
        });


        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

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



