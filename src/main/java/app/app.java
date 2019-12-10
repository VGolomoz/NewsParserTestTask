package app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class app {

    public static void main(String[] args) throws IOException, InterruptedException {
        Document doc = Jsoup.connect("https://ain.ua/").userAgent("Mozilla").validateTLSCertificates(false).get();

//        Document doc = Jsoup.connect("https://ain.ua/2019/12/10/problemy-s-poiskom-raboty/").userAgent("Mozilla").validateTLSCertificates(false).get();


        System.out.printf("Title: %s%n", doc.title());
        System.out.printf("Body: %s", doc.body().text());
        System.out.println(doc.html());
//        String description = doc.select("meta[name=description]").first().attr("content");
//        System.out.println("Description : " + description);

//        Elements links = doc.select("a[href]");

//        Elements test = doc.select("p");
//        for (Element e: test) {
//            System.out.println(e.text());
//        }
//        System.out.println("Before: " + test.text());
//        String s = test.text().substring(0, test.text().lastIndexOf("Заметили ошибку? Выделите ее и нажмите Ctrl+Enter"));
//        System.out.println(s);

//        Elements link = doc.select("ul[class=\"block-posts block-last-news\"]");
//        for (Element e: link) {
//            System.out.println(e.text());
//        }
//
//        Elements first = link.select("a[href]");
//        for (int i = 0; i < 5 ; i++) {
//            System.out.println("Something here: " + first.get(i).attr("href"));
//        }
    }
}

