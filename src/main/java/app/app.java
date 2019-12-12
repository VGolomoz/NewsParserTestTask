package app;

import app.service.Parser;
import app.service.implementations.NewsServiceImpl;
import app.service.interfaces.NewsService;

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
        new Parser(new NewsServiceImpl()).start(categories);

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

    }
}


