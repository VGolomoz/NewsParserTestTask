package app;

import app.service.implementations.NewsServiceImpl;
import app.service.interfaces.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class app {

    private static NewsService newsService;

    public static void main(String[] args) throws IOException, InterruptedException {

        newsService = new NewsServiceImpl();

        Document doc = Jsoup.connect("https://www.unian.net/politics/").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/71.0").validateTLSCertificates(false).get();


//        System.out.printf("Title: %s%n", doc.title());
//        System.out.printf("Body: %s", doc.body().text());
//        System.out.println(doc.html());
//        String description = doc.select("meta[name=description]").first().attr("content");
//        System.out.println("Description : " + description);

        Elements htmlBlock = doc.select("section[class=\"publications-archive\"]"); //get html block with links on all last news at topic
        Elements links = htmlBlock.select("a[href]"); // get only links on all last news at topic
        String link = links.get(0).attr("href"); // get link on one last news at topic
        Document actualNewsFromTopic = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/71.0").validateTLSCertificates(false).get();
        String title = actualNewsFromTopic.title();
        String newsDate = actualNewsFromTopic.select("div[class=\"item time no-padding\"]").first().text();

        System.out.println(newsService.createNewsInDB(title, "test", newsDate, "test"));


//        Elements test = actualNewsFromTopic.select("p");
//        for (Element e: test) {
//            System.out.println(e.text());
//        }
//        System.out.println(actualNewsFromTopic);

    }
    }
//        Elements test = doc.select("p");
//        for (Element e: test) {
//            System.out.println(e.text());
//        }
//        System.out.println("Before: " + test.text());
//        String s = test.text().substring(0, test.text().lastIndexOf("Заметили ошибку? Выделите ее и нажмите Ctrl+Enter"));
//        System.out.println(s);

